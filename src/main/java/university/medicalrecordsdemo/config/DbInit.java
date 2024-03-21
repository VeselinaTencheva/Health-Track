package university.medicalrecordsdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.DepartmentType;
import university.medicalrecordsdemo.model.entity.DiagnosisEntity;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;
import university.medicalrecordsdemo.model.entity.PrivilegeEntity;
import university.medicalrecordsdemo.model.entity.PrivilegeType;
import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.model.entity.SpecialtyType;
import university.medicalrecordsdemo.model.entity.UserEntity;
import university.medicalrecordsdemo.repository.AppointmentRepository;
import university.medicalrecordsdemo.repository.DiagnosisRepository;
import university.medicalrecordsdemo.repository.PatientRepository;
import university.medicalrecordsdemo.repository.PhysicianRepository;
import university.medicalrecordsdemo.repository.PrivilegeRepository;
import university.medicalrecordsdemo.repository.RoleRepository;
import university.medicalrecordsdemo.repository.SickLeaveRepository;
import university.medicalrecordsdemo.repository.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DbInit implements CommandLineRunner {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private DiagnosisRepository diagnosisRepository;

        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private PrivilegeRepository privilegeRepository;

        @Autowired
        private PatientRepository patientRepository;

        @Autowired
        private PhysicianRepository physicianRepository;

        @Autowired
        private AppointmentRepository appointmentRepository;

        @Autowired
        private SickLeaveRepository sickLeaveRepository;

        @Autowired
        private PasswordEncoder encoder;

        public DbInit() {
        }

        @Override
        public void run(String... args) throws Exception {
                // // Load roles
                // this.loadRoles();

                // // Load admin
                // this.loadAdmin();

                // // Load physicians
                // this.loadPhysicians();
                
                // // Load patients
                // this.loadPatients();

                // // Load diagnoses
                // this.loadDiagnoses();

                // // Load appointments
                // this.loadAppointments();
        }

        @Transactional
        public void loadAdmin() {
                // if (userRepository.findAll().size() > 0) {
                //         userRepository.deleteAll();
                // }

                final RoleEntity adminRole = roleRepository.findByAuthority(RoleType.ROLE_ADMIN);

                UserEntity user = new UserEntity();

                user.setFirstName("Veselina");
                user.setSsn("123-45-6789");
                user.setLastName("Tencheva");
                user.setGender("woman");
                user.setBirthDate(LocalDate.of(1998, 8, 9));
                user.setPassword(encoder.encode("password"));
                user.setUsername("veselina");
                user.setRoles(Arrays.asList(adminRole).stream().collect(Collectors.toSet()));
                user.setEnabled(true);
                user.setAccountNonExpired(true);
                user.setAccountNonLocked(true);
                user.setCredentialsNonExpired(true);
                userRepository.save(user);
        }

        @Transactional
        public void loadAppointments() {
                if (appointmentRepository.findAll().size() > 0) {
                        appointmentRepository.deleteAll();
                }

                List<AppointmentEntity> appointments = appointmentRepository.findAll();
                for (AppointmentEntity appointment : appointments) {
                        appointment.getPatient().getAppointments().remove(appointment);
                        appointment.getPhysician().getAppointments().remove(appointment);
                        if (appointment.getSickLeave() != null) {
                                sickLeaveRepository.delete(appointment.getSickLeave());
                                appointment.getSickLeave().setAppointment(null);
                        }
                        if (appointment.getDiagnosis() != null) {
                                appointment.getDiagnosis().getAppointments().remove(appointment);
                                diagnosisRepository.delete(appointment.getDiagnosis());
                        }
                        appointmentRepository.delete(appointment);
                }

                final List<PatientEntity> patients = patientRepository.findAll();
                final List<PhysicianEntity> physicians = physicianRepository.findAll();

                appointments = Arrays.asList(
                        new AppointmentEntity(LocalDate.of(2020, 1, 1), patients.get(0), physicians.get(0), "General checkup"),
                        new AppointmentEntity(LocalDate.of(2020, 2, 2), patients.get(1), physicians.get(1), "Follow-up appointment"),
                        new AppointmentEntity(LocalDate.of(2020, 3, 3), patients.get(2), physicians.get(2), "Annual physical"),
                        new AppointmentEntity(LocalDate.of(2020, 4, 4), patients.get(3), physicians.get(3), "Consultation"),
                        new AppointmentEntity(LocalDate.of(2020, 5, 5), patients.get(4), physicians.get(4), "General checkup"),
                        new AppointmentEntity(LocalDate.of(2020, 6, 6), patients.get(0), physicians.get(0), "Follow-up appointment"),
                        new AppointmentEntity(LocalDate.of(2020, 7, 7), patients.get(1), physicians.get(1), "Annual physical"),
                        new AppointmentEntity(LocalDate.of(2020, 8, 8), patients.get(2), physicians.get(2),  "Consultation"),
                        new AppointmentEntity(LocalDate.of(2020, 9, 9), patients.get(3), physicians.get(3), "General checkup"),
                        new AppointmentEntity(LocalDate.of(2020, 10, 10), patients.get(4), physicians.get(4), "Follow-up appointment")
                );

                appointmentRepository.saveAll(appointments);
        }

        @Transactional
        public void loadPatients() {
                if (patientRepository.findAll().size() > 0) {
                        patientRepository.deleteAll();
                }

                final RoleEntity patientRole = roleRepository.findByAuthority(RoleType.ROLE_PATIENT);
                List<PatientEntity> patients = Arrays.asList(
                        new PatientEntity("9812014225", "John", "Doe", "Male", LocalDate.of(1980, 1, 1), "john@patient.com", encoder.encode("password"), true, patientRole),
                        new PatientEntity("8505062523", "Jane", "Smith", "Female", LocalDate.of(1990, 3, 3), "jane@patient.com", encoder.encode("password"), true, patientRole),
                        new PatientEntity("8202025623", "Michael", "Johnson", "Male", LocalDate.of(1990, 3, 3), "michael@patient.com", encoder.encode("password"), true, patientRole),
                        new PatientEntity("8301052365", "Emily", "Brown", "Female", LocalDate.of(1982, 4, 4), "emily@patient.com", encoder.encode("password"), true, patientRole),
                        new PatientEntity("0006058586", "David", "Wilson", "Male",LocalDate.of(1978, 5, 5), "david@patient.com", encoder.encode("password"), true, patientRole),
                        new PatientEntity("0205047475", "Sarah", "Martinez", "Female", LocalDate.of(1995, 6, 6), "sarah@patient.com", encoder.encode("password"), true, patientRole),
                        new PatientEntity("0106125456", "Christopher", "Lopez", "Male", LocalDate.of(1978, 7, 7), "christopher@patient.com", encoder.encode("password"), true, patientRole),
                        new PatientEntity("9808095457", "Amanda", "Garcia", "Female", LocalDate.of(1992, 8, 8), "amanda@patient.com", encoder.encode("password"), true, patientRole),
                        new PatientEntity("7402084545", "James", "Perez", "Male", LocalDate.of(1983, 9, 9), "james@patient.com", encoder.encode("password"), true, patientRole),
                        new PatientEntity("7604054245", "Jessica", "Rodriguez", "Female", LocalDate.of(1998, 10, 10), "jessica@patient.com", encoder.encode("password"), true, patientRole)
                );
                // loop all patients and set their physician to the first physician in the list
                Set<SpecialtyType> specialtySet = new HashSet<>();
                specialtySet.add(SpecialtyType.GENERAL_PRACTICE);
                List<PhysicianEntity> gpPhysicians = physicianRepository.findAllBySpecialtiesIn(specialtySet);
                
                if (!gpPhysicians.isEmpty()) {
                    PhysicianEntity gpPhysician = gpPhysicians.get(0);
                    for (PatientEntity patient : patients) {
                        patient.setPhysician(gpPhysician);
                    }
                    patientRepository.saveAll(patients);
                } else {
                        System.out.println("No general practitioners found");
                    // Handle the case where no physicians with the given specialty are found
                    // For example, throw an exception, log a warning, or handle it based on your application logic
                }
        }

        @Transactional
        public void loadRoles() {
                if (roleRepository.findAll().size() > 0) {
                        roleRepository.deleteAll();
                }

                if (privilegeRepository.findAll().size() > 0) {
                        privilegeRepository.deleteAll();
                }

                final PrivilegeEntity readPatient = createPrivilegeIfNotFound(PrivilegeType.READ_PATIENT);
                final PrivilegeEntity readAllPatients = createPrivilegeIfNotFound(PrivilegeType.READ_ALL_PATIENTS);
                final PrivilegeEntity readPhysician = createPrivilegeIfNotFound(PrivilegeType.READ_PHYSICIAN);
                final PrivilegeEntity writePatient = createPrivilegeIfNotFound(PrivilegeType.WRITE_PATIENT);
                final PrivilegeEntity writePhysician = createPrivilegeIfNotFound(PrivilegeType.WRITE_PHYSICIAN);
                final PrivilegeEntity readAllPhysicians = createPrivilegeIfNotFound(PrivilegeType.READ_ALL_PHYSICIANS);
                final PrivilegeEntity readDiagnosis = createPrivilegeIfNotFound(PrivilegeType.READ_DIAGNOSIS);
                final PrivilegeEntity writeDiagnosis = createPrivilegeIfNotFound(PrivilegeType.WRITE_DIAGNOSIS);
                final PrivilegeEntity readAllDiagnoses = createPrivilegeIfNotFound(PrivilegeType.READ_ALL_DIAGNOSES);
                final PrivilegeEntity readSickLeave = createPrivilegeIfNotFound(PrivilegeType.READ_SICK_LEAVE);
                final PrivilegeEntity writeSickLeave = createPrivilegeIfNotFound(PrivilegeType.WRITE_SICK_LEAVE);
                final PrivilegeEntity readAllSickLeaves = createPrivilegeIfNotFound(PrivilegeType.READ_ALL_SICK_LEAVES);
                final PrivilegeEntity readVisitation = createPrivilegeIfNotFound(PrivilegeType.READ_VISITATION);
                final PrivilegeEntity writeVisitation = createPrivilegeIfNotFound(PrivilegeType.WRITE_VISITATION);
                final PrivilegeEntity readAllVisitations = createPrivilegeIfNotFound(PrivilegeType.READ_ALL_VISITATIONS);

                createRoleIfNotFound(RoleType.ROLE_ADMIN,
                        new HashSet<>(Arrays.asList(readAllVisitations, readAllSickLeaves, readAllDiagnoses, readAllPhysicians, readAllPatients,readPatient, readDiagnosis, readPhysician, readVisitation, readSickLeave, writePatient, writeDiagnosis, writePhysician, writeVisitation, writeSickLeave)));
                createRoleIfNotFound(RoleType.ROLE_PATIENT,
                        new HashSet<>(Arrays.asList(readAllVisitations, readAllSickLeaves, readPatient, readPhysician, writePatient, readDiagnosis, readSickLeave, readVisitation)));
                createRoleIfNotFound(RoleType.ROLE_PHYSICIAN,
                        new HashSet<>(Arrays.asList(readAllVisitations, readAllSickLeaves, readAllDiagnoses, readAllPatients, readPatient, readDiagnosis, readPhysician, readVisitation, readSickLeave, writePhysician, writeDiagnosis, writeVisitation, writeSickLeave)));
                createRoleIfNotFound(RoleType.ROLE_GENERAL_PRACTITIONER,
                        new HashSet<>(Arrays.asList(readAllVisitations,readAllSickLeaves, readAllDiagnoses, readAllPhysicians, readAllPatients, readPatient, readDiagnosis, writePatient, readPhysician, readVisitation, readSickLeave, writeDiagnosis, writeVisitation, writeSickLeave, writePhysician)));
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

        @Transactional
        public void loadPhysicians() {
                if (physicianRepository.findAll().size() > 0) {
                        physicianRepository.deleteAll();
                }
                final RoleEntity physicianRole = roleRepository.findByAuthority(RoleType.ROLE_PHYSICIAN);
                final RoleEntity generalPractitionerRole = roleRepository.findByAuthority(RoleType.ROLE_GENERAL_PRACTITIONER);
                List<PhysicianEntity> physicians = Arrays.asList(
                        new PhysicianEntity("1234567890", "John", "Doe", "Male", LocalDate.of(1980, 1, 1), "john@example.com", encoder.encode("password"), "MD123456", new HashSet<>(Arrays.asList(SpecialtyType.ALLERGY_AND_IMMUNOLOGY, SpecialtyType.GENERAL_PRACTICE)), generalPractitionerRole),
                        new PhysicianEntity("2345678901", "Jane", "Smith", "Female", LocalDate.of(1985, 2, 2), "jane@example.com", encoder.encode("password"), "MD234567", new HashSet<>(Arrays.asList(SpecialtyType.ANATOMIC_PATHOLOGY)), physicianRole),
                        new PhysicianEntity("3456789012", "Michael", "Johnson", "Male", LocalDate.of(1975, 3, 3), "michael@example.com", encoder.encode("password"), "MD345678", new HashSet<>(Arrays.asList(SpecialtyType.ANESTHESIOLOGIST)), physicianRole),
                        new PhysicianEntity("4567890123", "Emily", "Brown", "Female", LocalDate.of(1990, 4, 4), "emily@example.com", encoder.encode("password"), "MD456789", new HashSet<>(Arrays.asList(SpecialtyType.CLINICAL_IMMUNOLOGY)), physicianRole),
                        new PhysicianEntity("5678901234", "David", "Martinez", "Male", LocalDate.of(1982, 5, 5), "david@example.com", encoder.encode("password"), "MD567890", new HashSet<>(Arrays.asList(SpecialtyType.CLINICAL_PATHOLOGY, SpecialtyType.GENERAL_PRACTICE)), generalPractitionerRole),
                        new PhysicianEntity("6789012345", "Jessica", "Garcia", "Female", LocalDate.of(1978, 6, 6), "jessica@example.com", encoder.encode("password"), "MD678901", new HashSet<>(Arrays.asList(SpecialtyType.COAGULATION_DISORDERS)), physicianRole),
                        new PhysicianEntity("7890123456", "Christopher", "Lopez", "Male", LocalDate.of(1987, 7, 7), "christopher@example.com", encoder.encode("password"), "MD789012", new HashSet<>(Arrays.asList(SpecialtyType.COLORECTAL_SURGERY)), physicianRole),
                        new PhysicianEntity("8901234567", "Amanda", "Perez", "Female", LocalDate.of(1980, 8, 8), "amanda@example.com", encoder.encode("password"), "MD890123", new HashSet<>(Arrays.asList(SpecialtyType.COSMETIC_DERMATOLOGY)), physicianRole),
                        new PhysicianEntity("9012345678", "Daniel", "Gonzalez", "Male", LocalDate.of(1989, 9, 9), "daniel@example.com", encoder.encode("password"), "MD901234", new HashSet<>(Arrays.asList(SpecialtyType.DERMATOPATHOLOGY)), physicianRole),
                        new PhysicianEntity("0123456789", "Ashley", "Ramirez", "Female", LocalDate.of(1983, 10, 10), "ashley@example.com", encoder.encode("password"), "MD012345", new HashSet<>(Arrays.asList(SpecialtyType.DIABETES_AND_METABOLISM)), physicianRole),
                        new PhysicianEntity("9876543210", "Matthew", "Torres", "Male", LocalDate.of(1984, 11, 11), "matthew@example.com", encoder.encode("password"), "MD987654", new HashSet<>(Arrays.asList(SpecialtyType.ELECTROPHYSIOLOGY)), physicianRole)
                );
                physicianRepository.saveAll(physicians);
        }

        @Transactional
        public void loadDiagnoses() {
                if (diagnosisRepository.findAll().size() > 0) {
                        diagnosisRepository.deleteAll();
                }
                List<DiagnosisEntity> diagnoses = Arrays.asList(
                        // Immunology
                        new DiagnosisEntity("Allergic Rhinitis", "IMM001", DepartmentType.IMMUNOLOGY, "Allergic inflammation of the nasal airways"),
                        new DiagnosisEntity("Autoimmune Hepatitis", "IMM002", DepartmentType.IMMUNOLOGY, "Chronic liver inflammation caused by autoimmune response"),
                        new DiagnosisEntity("Systemic Lupus Erythematosus", "IMM003", DepartmentType.IMMUNOLOGY, "Autoimmune disease affecting various organs"),
                        new DiagnosisEntity("Rheumatoid Arthritis", "IMM004", DepartmentType.IMMUNOLOGY, "Chronic inflammatory disorder affecting joints"),
                        new DiagnosisEntity("Celiac Disease", "IMM005", DepartmentType.IMMUNOLOGY, "Autoimmune reaction to gluten causing damage to the small intestine"),
                        // Anesthesiology
                        new DiagnosisEntity("General Anesthesia", "ANES001", DepartmentType.ANESTHESIOLOGY, "Induced unconsciousness for surgical procedures"),
                        new DiagnosisEntity("Epidural Anesthesia", "ANES002", DepartmentType.ANESTHESIOLOGY, "Regional anesthesia for pain relief during childbirth"),
                        new DiagnosisEntity("Local Anesthesia", "ANES003", DepartmentType.ANESTHESIOLOGY, "Loss of sensation in a specific area of the body"),
                        new DiagnosisEntity("Spinal Anesthesia", "ANES004", DepartmentType.ANESTHESIOLOGY, "Regional anesthesia for lower body surgeries"),
                        new DiagnosisEntity("Conscious Sedation", "ANES005", DepartmentType.ANESTHESIOLOGY, "Reduced consciousness for minor procedures"),
                        // Cardiology
                        new DiagnosisEntity("Coronary Artery Disease", "CARD001", DepartmentType.CARDIOLOGY, "Narrowing or blockage of coronary arteries leading to reduced blood flow to the heart muscle"),
                        new DiagnosisEntity("Atrial Fibrillation", "CARD002", DepartmentType.CARDIOLOGY, "Irregular heartbeat due to abnormal electrical signals in the atria"),
                        new DiagnosisEntity("Heart Failure", "CARD003", DepartmentType.CARDIOLOGY, "Inability of the heart to pump sufficient blood to meet the body's needs"),
                        new DiagnosisEntity("Hypertension", "CARD004", DepartmentType.CARDIOLOGY, "High blood pressure"),
                        new DiagnosisEntity("Myocardial Infarction", "CARD005", DepartmentType.CARDIOLOGY, "Heart attack due to blockage of coronary arteries leading to damage of heart muscle"),
                        // Dermatology
                        new DiagnosisEntity("Acne", "DERM001", DepartmentType.DERMATOLOGY, "Chronic inflammatory skin condition characterized by pimples and spots"),
                        new DiagnosisEntity("Psoriasis", "DERM002", DepartmentType.DERMATOLOGY, "Chronic autoimmune condition causing rapid buildup of skin cells leading to red, itchy, and scaly patches"),
                        new DiagnosisEntity("Eczema", "DERM003", DepartmentType.DERMATOLOGY, "Inflammatory skin condition causing dry, itchy, and inflamed patches of skin"),
                        new DiagnosisEntity("Skin Cancer", "DERM004", DepartmentType.DERMATOLOGY, "Abnormal growth of skin cells leading to cancerous tumors"),
                        new DiagnosisEntity("Rosacea", "DERM005", DepartmentType.DERMATOLOGY, "Chronic skin condition characterized by facial redness and visible blood vessels"),
                        // Endocrinology
                        new DiagnosisEntity("Diabetes Mellitus Type 2", "ENDO001", DepartmentType.ENDOCRINOLOGY, "Chronic condition characterized by high blood sugar levels"),
                        new DiagnosisEntity("Hypothyroidism", "ENDO002", DepartmentType.ENDOCRINOLOGY, "Underactive thyroid gland leading to decreased production of thyroid hormones"),
                        new DiagnosisEntity("Hyperthyroidism", "ENDO003", DepartmentType.ENDOCRINOLOGY, "Overactive thyroid gland leading to increased production of thyroid hormones"),
                        new DiagnosisEntity("Addison's Disease", "ENDO004", DepartmentType.ENDOCRINOLOGY, "Adrenal glands do not produce enough hormones"),
                        new DiagnosisEntity("Cushing's Syndrome", "ENDO005", DepartmentType.ENDOCRINOLOGY, "Excessive cortisol hormone production"),
                        // Gastroenterology
                        new DiagnosisEntity("Gastritis", "GASTR001", DepartmentType.GASTROENTEROLOGY, "Inflammation of the stomach lining"),
                        new DiagnosisEntity("Gastroesophageal Reflux Disease (GERD)", "GASTR002", DepartmentType.GASTROENTEROLOGY, "Chronic digestive disorder causing stomach acid to back up into the esophagus"),
                        new DiagnosisEntity("Crohn's Disease", "GASTR003", DepartmentType.GASTROENTEROLOGY, "Inflammatory bowel disease affecting the gastrointestinal tract"),
                        new DiagnosisEntity("Ulcerative Colitis", "GASTR004", DepartmentType.GASTROENTEROLOGY, "Inflammatory bowel disease causing ulcers and inflammation in the colon and rectum"),
                        new DiagnosisEntity("Peptic Ulcer Disease", "GASTR005", DepartmentType.GASTROENTEROLOGY, "Open sores that develop on the inner lining of the stomach, upper small intestine, or esophagus"),
                        // Hematology
                        new DiagnosisEntity("Anemia", "HEMAT001", DepartmentType.HEMATOLOGY, "Condition characterized by a deficiency of red blood cells or hemoglobin"),
                        new DiagnosisEntity("Hemophilia", "HEMAT002", DepartmentType.HEMATOLOGY, "Genetic disorder impairing the body's ability to control blood clotting"),
                        new DiagnosisEntity("Thrombocytopenia", "HEMAT003", DepartmentType.HEMATOLOGY, "Low platelet count in the blood"),
                        // new DiagnosisEntity("Leukemia", "HEMAT004", DepartmentType.HEMATOLOGY, "Cancer of the body's blood-forming tissues"),
                        new DiagnosisEntity("Sickle Cell Disease", "HEMAT005", DepartmentType.HEMATOLOGY, "Inherited blood disorder causing abnormal hemoglobin"),
                        // Neurology
                        new DiagnosisEntity("Migraine", "NEURO001", DepartmentType.NEUROLOGY, "Recurrent throbbing headaches"),
                        new DiagnosisEntity("Epilepsy", "NEURO002", DepartmentType.NEUROLOGY, "Central nervous system disorder characterized by recurrent seizures"),
                        new DiagnosisEntity("Parkinson's Disease", "NEURO003", DepartmentType.NEUROLOGY, "Progressive nervous system disorder affecting movement"),
                        new DiagnosisEntity("Multiple Sclerosis", "NEURO004", DepartmentType.NEUROLOGY, "Chronic disease affecting the central nervous system"),
                        new DiagnosisEntity("Alzheimer's Disease", "NEURO005", DepartmentType.NEUROLOGY, "Progressive neurodegenerative disease affecting memory and cognitive function"),
                        // Oncology
                        new DiagnosisEntity("Breast Cancer", "ONCO001", DepartmentType.ONCOLOGY, "Cancer that forms in the cells of the breasts"),
                        new DiagnosisEntity("Lung Cancer", "ONCO002", DepartmentType.ONCOLOGY, "Cancer that begins in the lungs"),
                        new DiagnosisEntity("Colon Cancer", "ONCO003", DepartmentType.ONCOLOGY, "Cancer that forms in the colon or rectum"),
                        new DiagnosisEntity("Prostate Cancer", "ONCO004", DepartmentType.ONCOLOGY, "Cancer that occurs in the prostate gland in men"),
                        new DiagnosisEntity("Leukemia", "ONCO005", DepartmentType.ONCOLOGY, "Cancer of the body's blood-forming tissues"),
                        // Pathology
                        new DiagnosisEntity("Cervical Dysplasia", "PATHO001", DepartmentType.PATHOLOGY, "Abnormal changes in the cells of the cervix"),
                        new DiagnosisEntity("Liver Cirrhosis", "PATHO002", DepartmentType.PATHOLOGY, "Scarring of the liver tissue"),
                        new DiagnosisEntity("Kidney Stones", "PATHO003", DepartmentType.PATHOLOGY, "Hard deposits of minerals and salts that form in the kidneys"),
                        new DiagnosisEntity("Gallstones", "PATHO004", DepartmentType.PATHOLOGY, "Hardened deposits that form in the gallbladder"),
                        new DiagnosisEntity("Pancreatitis", "PATHO005", DepartmentType.PATHOLOGY, "Inflammation of the pancreas"),
                        // Pulmonology
                        new DiagnosisEntity("Asthma", "PULM001", DepartmentType.PULMONOLOGY, "Chronic inflammatory disease of the airways"),
                        new DiagnosisEntity("Chronic Obstructive Pulmonary Disease (COPD)", "PULM002", DepartmentType.PULMONOLOGY, "Progressive lung disease causing breathing problems"),
                        new DiagnosisEntity("Pneumonia", "PULM003", DepartmentType.PULMONOLOGY, "Infection that inflames air sacs in one or both lungs"),
                        new DiagnosisEntity("Tuberculosis", "PULM004", DepartmentType.PULMONOLOGY, "Bacterial infection that primarily affects the lungs")
                        // new DiagnosisEntity("Lung Cancer", "PULM005", DepartmentType.PULMONOLOGY, "Cancer that begins in the lungs")
                );
            
                diagnosisRepository.saveAll(diagnoses);
            }

}
