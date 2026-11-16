package myspring.com.myspring.Mapper;

import lombok.RequiredArgsConstructor;
import myspring.com.myspring.DTO.RequestRole;
import myspring.com.myspring.DTO.ResponsePermission;
import myspring.com.myspring.DTO.ResponseRole;
import myspring.com.myspring.Model.Permission;
import myspring.com.myspring.Model.Role;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final PermissionMapper permissionMapper;

    public Role toEntity(RequestRole request, Set<Permission> permissions) {
        if (request == null) return null;

        return Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : true)
                .permissions(new HashSet<>(permissions))
                .build();
    }

    public void updateEntity(Role role, RequestRole request, Set<Permission> permissions) {
        if (request == null || role == null) return;

        role.setName(request.getName());
        role.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            role.setStatus(request.getStatus());
        }

        // ប្រសិនបើមិនផ្ញើ permissionIds មកទេ (null) ទុក Permission ចាស់
        if (request.getPermissionIds() != null) {
            role.getPermissions().clear();
            role.getPermissions().addAll(permissions);
        }
    }

    public ResponseRole toResponse(Role role) {
        if (role == null) return null;

        Set<ResponsePermission> permissions = role.getPermissions().stream()
                .map(permissionMapper::toResponse)
                .collect(Collectors.toSet());

        return ResponseRole.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .status(role.getStatus())
                .permissions(permissions)
                .createdBy(role.getCreatedBy())
                .updatedBy(role.getUpdatedBy())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .build();
    }
}