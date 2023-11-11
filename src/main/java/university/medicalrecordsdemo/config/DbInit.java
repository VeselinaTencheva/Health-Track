package university.medicalrecordsdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.model.entity.PrivilegeEntity;
import university.medicalrecordsdemo.model.entity.PrivilegeType;
import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.model.entity.UserEntity;
import university.medicalrecordsdemo.repository.PatientRepository;
import university.medicalrecordsdemo.repository.PhysicianRepository;
import university.medicalrecordsdemo.repository.PrivilegeRepository;
import university.medicalrecordsdemo.repository.RoleRepository;
import university.medicalrecordsdemo.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DbInit implements CommandLineRunner {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private PrivilegeRepository privilegeRepository;

        @Autowired
        private PatientRepository patientRepository;

        @Autowired
        private PhysicianRepository physicianRepository;

        @Autowired
        private PasswordEncoder encoder;

        public DbInit() {
        }

        @Override
        public void run(String... args) throws Exception {
                if (userRepository.findAll().size() > 0) {
                        this.userRepository.deleteAll();
                }
                if (roleRepository.findAll().size() > 0)
                        this.roleRepository.deleteAll();
                if (privilegeRepository.findAll().size() > 0)
                        this.privilegeRepository.deleteAll();
                //
                PrivilegeEntity readPatient = createPrivilegeIfNotFound(PrivilegeType.READ_PATIENT);
                PrivilegeEntity readPhysician = createPrivilegeIfNotFound(PrivilegeType.READ_PHYSICIAN);
                // PrivilegeEntity readGeneralPractitioner
                // = createPrivilegeIfNotFound("READ_GENERAL_PRACTITIONER");
                PrivilegeEntity writePatient = createPrivilegeIfNotFound(PrivilegeType.WRITE_PATIENT);
                PrivilegeEntity writePhysician = createPrivilegeIfNotFound(PrivilegeType.WRITE_PHYSICIAN);
                // PrivilegeEntity writeGeneralPractitioner
                // = createPrivilegeIfNotFound("WRITE_GENERAL_PRACTITIONER");
                PrivilegeEntity readDiagnosis = createPrivilegeIfNotFound(PrivilegeType.READ_DIAGNOSIS);
                PrivilegeEntity writeDiagnosis = createPrivilegeIfNotFound(PrivilegeType.WRITE_DIAGNOSIS);
                PrivilegeEntity readSickLeave = createPrivilegeIfNotFound(PrivilegeType.READ_SICK_LEAVE);
                PrivilegeEntity writeSickLeave = createPrivilegeIfNotFound(PrivilegeType.WRITE_SICK_LEAVE);
                PrivilegeEntity readVisitation = createPrivilegeIfNotFound(PrivilegeType.READ_VISITATION);
                PrivilegeEntity writeVisitation = createPrivilegeIfNotFound(PrivilegeType.WRITE_VISITATION);

                Set<PrivilegeEntity> adminPrivileges = Arrays.asList(
                                readPatient, readDiagnosis, readPhysician,
                                readVisitation, readSickLeave, writePatient, writeDiagnosis,
                                writePhysician, writeVisitation, writeSickLeave)
                                .stream().collect(Collectors.toSet());

                Set<PrivilegeEntity> patientPrivileges = Arrays.asList(
                                readPatient, readPhysician, writePatient,
                                readDiagnosis, readSickLeave, readVisitation)
                                .stream().collect(Collectors.toSet());

                Set<PrivilegeEntity> physicianPrivileges = Arrays.asList(
                                readPatient, readDiagnosis, readPhysician,
                                readVisitation, readSickLeave, writePhysician,
                                writeDiagnosis, writeVisitation, writeSickLeave)
                                .stream().collect(Collectors.toSet());

                Set<PrivilegeEntity> generalPractitionerPrivileges = Arrays.asList(
                                readPatient, readDiagnosis, readPhysician,
                                readVisitation, readSickLeave,
                                writeDiagnosis, writeVisitation, writeSickLeave, writePhysician)
                                .stream().collect(Collectors.toSet());

                createRoleIfNotFound(RoleType.ROLE_ADMIN, adminPrivileges);
                createRoleIfNotFound(RoleType.ROLE_PATIENT, patientPrivileges);
                createRoleIfNotFound(RoleType.ROLE_PHYSICIAN, physicianPrivileges);
                createRoleIfNotFound(RoleType.ROLE_GENERAL_PRACTITIONER,
                                generalPractitionerPrivileges);

                RoleEntity adminRole = roleRepository.findByAuthority(RoleType.ROLE_ADMIN);
                UserEntity user = new UserEntity();

                user.setFirstName("Veselina");
                user.setSsn("123-45-6789");
                user.setLastName("Tencheva");
                user.setGender("woman");
                user.setBirthDate("08/09/1998");
                user.setPassword(encoder.encode("vesi"));
                user.setUsername("veselina");
                user.setRoles(Arrays.asList(adminRole).stream().collect(Collectors.toSet()));
                user.setEnabled(true);
                user.setAccountNonExpired(true);
                user.setAccountNonLocked(true);
                user.setCredentialsNonExpired(true);
                userRepository.save(user);

                // do tuk

                // Physician physician = new Physician();
                // physician.setName("phys 1");
                // physician.setDepartmentType(DepartmentType.ANESTHESIOLOGY);
                // physician.setMedicalUUID("2156346546");
                //
                // physicianRepository.save(physician);
                //
                // Physician physician2 = new Physician();
                // physician2.setName("phys 2");
                // physician2.setDepartmentType(DepartmentType.DERMATOLOGY);
                // physician2.setMedicalUUID("8778687687");
                //
                // Physician physician1 = new Physician();
                // physician1.setName("phys 3");
                // physician1.setDepartmentType(DepartmentType.ENDOCRINOLOGY);
                // physician1.setMedicalUUID("4654964968");
                //
                // physicianRepository.save(physician);
                // physicianRepository.save(physician1);
                //
                //
                // GeneralPractitioner gp = new GeneralPractitioner();
                // gp.setName("gp1");
                // gp.setPracticeCode("554545");
                // gp.setPracticeAddress("feskjftre");
                // gp.setDepartmentType(DepartmentType.ANESTHESIOLOGY);
                // gp.setMedicalUUID("45778");
                //
                // GeneralPractitioner gp1 = new GeneralPractitioner();
                // gp1.setName("gp2");
                // gp1.setPracticeCode("554544");
                // gp1.setPracticeAddress("gdrgrete");
                // gp1.setDepartmentType(DepartmentType.ANESTHESIOLOGY);
                // gp1.setMedicalUUID("56455");
                //
                // generalPractitionerRepository.save(gp);
                // generalPractitionerRepository.save(gp1);

                //
                // Patient p = new Patient();
                // p.setName("patient 1");
                // p.setSsn("1111111");
                // p.setHasInsurance(true);
                //
                // patientRepository.save(p);
                //
                // Patient p2 = new Patient();
                // p2.setName("patient 2");
                // p2.setSsn("22222222");
                // p2.setHasInsurance(true);

                //
                // patientRepository.save(p2);
                // this.userRepository.deleteAll();
                // Role adminRole = roleRepository.findByAuthority("ROLE_ADMIN");
                // User user = new User();
                // user.setName("user2");
                // user.setUsername("user2");
                // user.setPassword(encoder.encode("user"));
                // user.setRoles(Arrays.asList(adminRole).stream().collect(Collectors.toSet()));
                // user.setAccountNonExpired(true);
                // user.setAccountNonLocked(true);
                // user.setEnabled(true);
                // user.setCredentialsNonExpired(true);
                //
                // User user2 = new User();
                // user2.setName("teatcher");
                // user2.setUsername("teacher");
                // user2.setPassword(encoder.encode("teacher"));
                // user2.setAccountNonExpired(true);
                // user2.setAccountNonLocked(true);
                // user2.setEnabled(true);
                // user2.setCredentialsNonExpired(true);
                ////
                // List<User> users = Arrays.asList(user, user2);
                //
                // // Save to db
                // this.userRepository.saveAll(users);
        }

        @Transactional
        PrivilegeEntity createPrivilegeIfNotFound(PrivilegeType type) {
                PrivilegeEntity privilegeEntity = privilegeRepository.findByName(type);

                if (privilegeEntity == null) {
                        privilegeEntity = new PrivilegeEntity();
                        privilegeEntity.setName(type);
                        privilegeEntity = privilegeRepository.save(privilegeEntity);
                }

                return privilegeEntity;
        }

        @Transactional
        RoleEntity createRoleIfNotFound(
                        RoleType type, Set<PrivilegeEntity> privileges) {
                RoleEntity role = roleRepository.findByAuthority(type);
                if (role == null) {
                        role = new RoleEntity();
                        // role.setId(UUID.randomUUID());
                        role.setAuthority(type);
                        role.setPrivileges(privileges);
                        roleRepository.save(role);
                }
                return role;
        }

}
