package university.medicalrecordsdemo.service.privilege;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.model.entity.PrivilegeEntity;
import university.medicalrecordsdemo.repository.PrivilegeRepository;

@AllArgsConstructor
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    @Override
    public Set<PrivilegeEntity> findAll() {
        return privilegeRepository.findAll().stream()
                .collect(Collectors.toSet());
    }

    @Override
    public PrivilegeEntity findById(Long id) {
        return this.privilegeRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid privilege ID: " + id));
    }

    // @Override
    // public Optional<PrivilegeEntity> findByName(String name) {
    // return this.privilegeRepository.findByName(name)
    // .orElseThrow(() -> new IllegalArgumentException("Invalid Privilege Name: " +
    // name));
    // }

    @Override
    public PrivilegeEntity create(PrivilegeEntity role) {
        return this.privilegeRepository.save(role);
    }

    @Override
    public PrivilegeEntity update(Long id, PrivilegeEntity role) {
        role.setId(id);
        return this.privilegeRepository.save(role);
    }

    @Override
    public void delete(Long id) {
        this.privilegeRepository.deleteById(id);
    }
}