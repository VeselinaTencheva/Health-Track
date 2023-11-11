package university.medicalrecordsdemo.service.physician;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.dto.physician.UpdatePhysicianDto;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;
import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.model.entity.SpecialtyType;
import university.medicalrecordsdemo.repository.PhysicianRepository;
import university.medicalrecordsdemo.repository.RoleRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PhysicianServiceImpl implements PhysicianService {

    private final ModelMapper modelMapper;
    private final PhysicianRepository physicianRepository;
    private final RoleRepository roleRepository;

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
    public PhysicianDto update(Long id, UpdatePhysicianDto updatePhysicianDto) {
        PhysicianEntity physician = modelMapper.map(updatePhysicianDto, PhysicianEntity.class);
        physician.setId(id);
        return convertToPhysicianDTO(
                physicianRepository.save(modelMapper.map(physician, PhysicianEntity.class)));
        // GeneralPractitioner prevGP;
        // try {
        // prevGP = generalPractitionerService.findById(id);
        // } catch (Exception e) {
        // prevGP = null;
        // }

        // if (prevGP == null && physician.getPracticeCode().isEmpty() &&
        // physician.getPracticeCode().isBlank()) {
        // return
        // convertToPhysicianDTO(this.physicianRepository.save(modelMapper.map(physician,
        // Physician.class)));
        // } else if (prevGP == null && (!physician.getPracticeCode().isBlank())) {
        // physicianRepository.delete(modelMapper.map(physician, Physician.class));
        // return convertToPhysicianDTO(
        // generalPractitionerRepository
        // .save(modelMapper.map(physician, GeneralPractitioner.class)));
        // } else if (prevGP != null && physician.getPracticeCode().isBlank()) {
        // generalPractitionerRepository.deleteById(id);
        // return
        // convertToPhysicianDTO(physicianRepository.save(modelMapper.map(physician,
        // Physician.class)));
        // } else {
        // return convertToPhysicianDTO(
        // generalPractitionerRepository.save(modelMapper.map(physician,
        // GeneralPractitioner.class)));
        // }
    }

    @Override
    public void delete(Long id) {
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

        physicianEntity.setRoles(roles);
        return physicianEntity;
    }
}