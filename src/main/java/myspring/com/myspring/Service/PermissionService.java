package myspring.com.myspring.Service;

import lombok.RequiredArgsConstructor;
import myspring.com.myspring.DTO.PageResponse;
import myspring.com.myspring.DTO.RequestPermission;
import myspring.com.myspring.DTO.ResponsePermission;
import myspring.com.myspring.Exceptions.DuplicateResourceException;
import myspring.com.myspring.Exceptions.ResourceNotFoundException;
import myspring.com.myspring.Mapper.PermissionMapper;
import myspring.com.myspring.Model.Permission;
import myspring.com.myspring.Repository.PermissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public ResponsePermission createPermission(RequestPermission request) {
        if (permissionRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Permission with name " + request.getName() + " already exists");
        }

        Permission permission = permissionMapper.toEntity(request);
        Permission savedPermission = permissionRepository.save(permission);
        return permissionMapper.toResponse(savedPermission);
    }

    public ResponsePermission getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
        return permissionMapper.toResponse(permission);
    }

    public PageResponse<ResponsePermission> getAllPermissions(int pageNumber, int pageSize, String sortBy, String sortDir) {
        List<String> allowedSortFields = List.of("id", "name", "createdAt");
        String sortField = allowedSortFields.contains(sortBy) ? sortBy : "id";

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortField));

        Page<Permission> page = permissionRepository.findAll(pageable);
        return PageResponse.mapFrom(page, permissionMapper::toResponse);
    }

    public ResponsePermission updatePermission(Long id, RequestPermission request) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));

        if (!permission.getName().equals(request.getName()) && permissionRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Permission with name " + request.getName() + " already exists");
        }

        permissionMapper.updateEntity(permission, request);
        Permission updatedPermission = permissionRepository.save(permission);
        return permissionMapper.toResponse(updatedPermission);
    }

    public void deletePermission(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
        permissionRepository.delete(permission);
    }
}