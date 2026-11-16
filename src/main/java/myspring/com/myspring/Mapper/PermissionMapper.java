package myspring.com.myspring.Mapper;

import myspring.com.myspring.DTO.RequestPermission;
import myspring.com.myspring.DTO.ResponsePermission;
import myspring.com.myspring.Model.Permission;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {

    public Permission toEntity(RequestPermission request) {
        if (request == null) return null;

        return Permission.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : true)
                .build();
    }

    public void updateEntity(Permission permission, RequestPermission request) {
        if (request == null || permission == null) return;

        permission.setName(request.getName());
        permission.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            permission.setStatus(request.getStatus());
        }
    }

    public ResponsePermission toResponse(Permission permission) {
        if (permission == null) return null;

        return ResponsePermission.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .status(permission.getStatus())
                .createdBy(permission.getCreatedBy())
                .updatedBy(permission.getUpdatedBy())
                .createdAt(permission.getCreatedAt())
                .updatedAt(permission.getUpdatedAt())
                .build();
    }
}