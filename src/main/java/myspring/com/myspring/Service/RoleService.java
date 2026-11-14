package myspring.com.myspring.Service;

import lombok.RequiredArgsConstructor;
import myspring.com.myspring.Model.Role;
import myspring.com.myspring.Repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    // Get list
    public List<Role> getList(){
        return roleRepository.findAll();
    }

    // Get one id
    public Optional<Role> getOneById(Long id){
        //  select * from roles where = id;
        return roleRepository.findById(id);
    }

    // Save new record
    public Role create(Role role){
        return roleRepository.save(role);
    }

    // Update
    public Role update(Long id, Role role){
        Role isExistRole = roleRepository.findById(id).orElseThrow(
          () -> new RuntimeException("Not found")
        );
        // update existing
        isExistRole.setName(role.getName());
        isExistRole.setDescription(role.getDescription());
        return  roleRepository.save(isExistRole);
    }

    // Delete
    public  void delete(Long id){
        Role isExistRole = roleRepository.findById(id).orElseThrow(
          () -> new RuntimeException("Not found")
        );
        roleRepository.delete(isExistRole);
        // roleRepository.deleteById(id);
    }
}
