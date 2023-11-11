package university.medicalrecordsdemo.service.role;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.repository.RoleRepository;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Set<RoleEntity> findAll() {
        return roleRepository.findAll().stream()
                .collect(Collectors.toSet());
    }

    @Override
    public RoleEntity findById(Long id) {
        return this.roleRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Role ID: " + id));
    }

    @Override
    public RoleEntity create(RoleEntity role) {
        return this.roleRepository.save(role);
    }

    @Override
    public RoleEntity update(Long id, RoleEntity role) {
        role.setId(id);
        return this.roleRepository.save(role);
    }

    @Override
    public void delete(Long id) {
        this.roleRepository.deleteById(id);
    }
}