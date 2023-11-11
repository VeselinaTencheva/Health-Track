package university.medicalrecordsdemo.config;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.model.entity.RoleType;
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
                                                .requestMatchers("/appointments")
                                                .hasAnyAuthority(RoleType.ROLE_PATIENT.name(),
                                                                RoleType.ROLE_GENERAL_PRACTITIONER.name(),
                                                                RoleType.ROLE_PHYSICIAN.name(),
                                                                RoleType.ROLE_ADMIN.name())
                                                .requestMatchers("/patients")
                                                .hasAnyAuthority(RoleType.ROLE_GENERAL_PRACTITIONER.name(),
                                                                RoleType.ROLE_PHYSICIAN.name(),
                                                                RoleType.ROLE_ADMIN.name())
                                                .requestMatchers("/physicians")
                                                .hasAnyAuthority(RoleType.ROLE_GENERAL_PRACTITIONER.name(),
                                                                RoleType.ROLE_PHYSICIAN.name(),
                                                                RoleType.ROLE_ADMIN.name())
                                                .requestMatchers("/diagnosis")
                                                .hasAnyAuthority(RoleType.ROLE_GENERAL_PRACTITIONER.name(),
                                                                RoleType.ROLE_PHYSICIAN.name(),
                                                                RoleType.ROLE_ADMIN.name())
                                                .requestMatchers("/sick-leaves")
                                                .hasAnyAuthority(RoleType.ROLE_GENERAL_PRACTITIONER.name(),
                                                                RoleType.ROLE_PHYSICIAN.name(),
                                                                RoleType.ROLE_ADMIN.name())
                                                .requestMatchers("/treatments")
                                                .hasAnyAuthority(RoleType.ROLE_GENERAL_PRACTITIONER.name(),
                                                                RoleType.ROLE_PHYSICIAN.name(),
                                                                RoleType.ROLE_ADMIN.name())
                                                .requestMatchers("/js/**", "/css/**", "/images/**").permitAll()
                                                .requestMatchers("/register", "/login").anonymous().anyRequest()
                                                .authenticated())
                                .formLogin(formLogin -> formLogin
                                                .loginPage("/login")
                                                .permitAll())
                                .exceptionHandling(exceptionHandling -> exceptionHandling
                                                .accessDeniedPage("/unauthorized"))
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/login"))
                                .httpBasic(withDefaults());

                return http.build();
        }
}
