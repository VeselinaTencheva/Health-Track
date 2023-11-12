package university.medicalrecordsdemo.service.physician;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;
import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.model.entity.SpecialtyType;
import university.medicalrecordsdemo.repository.PatientRepository;
import university.medicalrecordsdemo.repository.PhysicianRepository;
import university.medicalrecordsdemo.repository.RoleRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PhysicianServiceImpl implements PhysicianService {

    private final ModelMapper modelMapper;
    private final PhysicianRepository physicianRepository;
    private final PatientRepository patientRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Set<PhysicianDto> findAll() {
        return physicianRepository.findAll().stream()
                .map(this::convertToPhysicianDTO)
                .collect(Collectors.toSet());
        // return physicianRepository
        // .findAll()
        // .stream()
        // .map(physician -> {
        // PhysicianDTO physicianDTO = convertToPhysicianDTO(physician);
        // boolean isGp = true;
        // int patientsCount = 0;
        // try {
        // GeneralPractitioner generalPractitioner = this.generalPractitionerService
        // .findById(physicianDTO.getId());
        // patientsCount =
        // patientRepository.findDistinctByGeneralPractitioner(generalPractitioner).size();
        // } catch (IllegalArgumentException e) {
        // isGp = false;
        // }
        // physicianDTO.setGP(isGp);
        // physicianDTO.setPatientsCount(patientsCount);
        // return physicianDTO;
        // })
        // .collect(Collectors.toList());
    }

    @Override
    public PhysicianDto findById(Long id) {
        return convertToPhysicianDTO(this.physicianRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Physician ID: " + id)));
    }

    @Override
    public PhysicianDto create(PhysicianDto physicianDto) {
        return convertToPhysicianDTO(
                this.physicianRepository.saveAndFlush(convertToPhysicianEntity(physicianDto)));
    }

    @Override
    public PhysicianDto update(Long id, PhysicianDto physicianDto) {
        PhysicianEntity prevousEntity = modelMapper.map(this.findById(id), PhysicianEntity.class);
        physicianDto.setPassword(prevousEntity.getPassword());
        PhysicianEntity physician = convertToPhysicianEntity(physicianDto);
        physician.setId(id);
        return convertToPhysicianDTO(physicianRepository.save(physician));
    }

    @Override
    public void delete(Long id) {
        PhysicianEntity physicianEntity = modelMapper.map(this.findById(id), PhysicianEntity.class);
        // Fetch patients associated with the physician
        List<PatientEntity> patients = patientRepository.findByPhysician(physicianEntity);

        // Set physician to null for each patient and save/update them
        patients.forEach(patient -> {
            patient.setPhysician(null);
            patientRepository.save(patient); // or patientRepository.update(patient) if you have a custom update method
        });

        // Delete the physician
        this.physicianRepository.deleteById(id);
    }

    private PhysicianDto convertToPhysicianDTO(PhysicianEntity physician) {
        return modelMapper.map(physician, PhysicianDto.class);
    }

    private PhysicianEntity convertToPhysicianEntity(PhysicianDto physicianDto) {
        PhysicianEntity physicianEntity = modelMapper.map(physicianDto, PhysicianEntity.class);

        final boolean isGeneralPractitioner = physicianDto.getSpecialties().contains(SpecialtyType.GENERAL_PRACTICE);
        final boolean isMedicalSpecialist = (!isGeneralPractitioner && !physicianDto.getSpecialties().isEmpty()) ||
                (isGeneralPractitioner && physicianDto.getSpecialties().size() > 1);

        Set<RoleEntity> roles = new HashSet<>();

        if (isGeneralPractitioner) {
            RoleEntity roleEntity = roleRepository.findByAuthority(RoleType.ROLE_GENERAL_PRACTITIONER);
            roles.add(roleEntity);
        }

        if (isMedicalSpecialist) {
            RoleEntity roleEntity = roleRepository.findByAuthority(RoleType.ROLE_PHYSICIAN);
            roles.add(roleEntity);
        }

        physicianEntity.setPassword(encoder.encode(physicianDto.getPassword())); // TODO investigate why it is not
                                                                                 // encoding automatically

        physicianEntity.setRoles(roles);
        return physicianEntity;
    }
}