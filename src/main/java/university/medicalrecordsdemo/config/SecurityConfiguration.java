package university.medicalrecordsdemo.config;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.model.entity.PrivilegeType;
import university.medicalrecordsdemo.service.user.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

        private final UserService userService;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public DaoAuthenticationProvider authProvider() {
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
                authProvider.setUserDetailsService(userService);
                authProvider.setPasswordEncoder(passwordEncoder());
                return authProvider;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/appointments/update/**", "/appointments/delete/**")
                        .hasAuthority(PrivilegeType.WRITE_VISITATION.name())
                        .requestMatchers("/appointments")
                        .hasAuthority(PrivilegeType.READ_ALL_VISITATIONS.name())
                        .requestMatchers("/patients/update/**", "/patients/delete/**")
                        .hasAuthority(PrivilegeType.WRITE_PATIENT.name())
                        .requestMatchers("/patients")
                        .hasAuthority(PrivilegeType.READ_ALL_PATIENTS.name())
                        .requestMatchers("/physicians/update/**", "/physicians/delete/**")
                        .hasAuthority(PrivilegeType.WRITE_PHYSICIAN.name())
                        .requestMatchers("/physicians")
                        .hasAuthority(PrivilegeType.READ_ALL_PHYSICIANS.name())
                        .requestMatchers("/diagnosis/update/**", "/diagnosis/delete/**")
                        .hasAuthority(PrivilegeType.WRITE_DIAGNOSIS.name())
                        .requestMatchers("/diagnosis")
                        .hasAuthority(PrivilegeType.READ_ALL_DIAGNOSES.name())
                        .requestMatchers("/sick-leaves/update/**", "/sick-leaves/delete/**")
                        .hasAuthority(PrivilegeType.WRITE_SICK_LEAVE.name())
                        .requestMatchers("/sick-leaves")
                        .hasAuthority(PrivilegeType.READ_ALL_SICK_LEAVES.name())
                        .requestMatchers("/js/**", "/css/**", "/images/**").permitAll()
                        .requestMatchers("/register", "/login").anonymous().anyRequest()
                        .authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/unauthorized"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .httpBasic(withDefaults());
        
            return http.build();
        }
}
