package myspring.com.myspring.Service;

import lombok.RequiredArgsConstructor;
import myspring.com.myspring.DTO.PageResponse;
import myspring.com.myspring.DTO.RequestRole;
import myspring.com.myspring.DTO.ResponseRole;
import myspring.com.myspring.Exceptions.DuplicateResourceException;
import myspring.com.myspring.Exceptions.ResourceNotFoundException;
import myspring.com.myspring.Mapper.RoleMapper;
import myspring.com.myspring.Model.Permission;
import myspring.com.myspring.Model.Role;
import myspring.com.myspring.Repository.PermissionRepository;
import myspring.com.myspring.Repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    @Transactional
    public ResponseRole createRole(RequestRole request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Role with name " + request.getName() + " already exists");
        }

        Set<Permission> permissions = findPermissions(request.getPermissionIds());

        Role role = roleMapper.toEntity(request, permissions);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toResponse(savedRole);
    }

    @Transactional(readOnly = true)
    public ResponseRole getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        return roleMapper.toResponse(role);
    }

    @Transactional(readOnly = true)
    public PageResponse<ResponseRole> getAllRoles(int pageNumber, int pageSize, String sortBy, String sortDir) {
        List<String> allowedSortFields = List.of("id", "name", "createdAt");
        String sortField = allowedSortFields.contains(sortBy) ? sortBy : "id";

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortField));

        Page<Role> page = roleRepository.findAll(pageable);
        return PageResponse.mapFrom(page, roleMapper::toResponse);
    }

    @Transactional
    public ResponseRole updateRole(Long id, RequestRole request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));

        if (!role.getName().equals(request.getName()) && roleRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Role with name " + request.getName() + " already exists");
        }

        Set<Permission> permissions = findPermissions(request.getPermissionIds());

        roleMapper.updateEntity(role, request, permissions);
        Role updatedRole = roleRepository.save(role);
        return roleMapper.toResponse(updatedRole);
    }

    @Transactional
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        roleRepository.delete(role);
    }

    // រកមើល Permission ទាំងអស់តាម id បើមានមួយមិនឃើញ 404
    private Set<Permission> findPermissions(Set<Long> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            return new HashSet<>();
        }

        List<Permission> permissions = permissionRepository.findAllById(permissionIds);
        if (permissions.size() != permissionIds.size()) {
            Set<Long> missing = new HashSet<>(permissionIds);
            permissions.forEach(permission -> missing.remove(permission.getId()));
            throw new ResourceNotFoundException("Permission not found with id: " + missing);
        }
        return new HashSet<>(permissions);
    }
}