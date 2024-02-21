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
import university.medicalrecordsdemo.util.enums.PhysicianTableColumnsEnum;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

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
    public Page<PhysicianDto> findAllByPageAndSort(int page, int size, PhysicianTableColumnsEnum sortField, String sortDirection) {
        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortDirection)) {
            direction = Sort.Direction.DESC;
        }
        final String sortFieldString = sortField.getColumnName().toString();
        Sort sort = Sort.by(direction, sortFieldString);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<PhysicianEntity> physicianPage = physicianRepository.findAll(pageRequest);
        return physicianPage.map(this::convertToPhysicianDTO);
    }

    @Override
    public Page<PhysicianDto> findAllByPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PhysicianEntity> physicianPage = physicianRepository.findAll(pageRequest);
        return physicianPage.map(this::convertToPhysicianDTO);
    }

    @Override
    public Set<PhysicianDto> findAllBySpecialty(SpecialtyType specialty) {
        Set<SpecialtyType> specialtySet = new HashSet<>();
        specialtySet.add(specialty);
        return physicianRepository.findAllBySpecialtiesIn(specialtySet).stream()
                .map(this::convertToPhysicianDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public PhysicianDto findById(Long id) {
        return convertToPhysicianDTO(this.physicianRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Physician ID: " + id)));
    }

    @Override
    public PhysicianDto create(PhysicianDto physicianDto) {
        PhysicianEntity physicianEntity = convertToPhysicianEntity(physicianDto);
        physicianEntity.setPassword(encoder.encode(physicianDto.getPassword()));
        return convertToPhysicianDTO(physicianRepository.save(physicianEntity));
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