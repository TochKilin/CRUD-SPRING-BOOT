package myspring.com.myspring.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myspring.com.myspring.DTO.PageResponse;
import myspring.com.myspring.DTO.RequestPermission;
import myspring.com.myspring.DTO.ResponsePermission;
import myspring.com.myspring.Service.PermissionService;
import myspring.com.myspring.Util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    // Create permission
    @PostMapping
    public ResponseEntity<ApiResponse<ResponsePermission>> createPermission(@Valid @RequestBody RequestPermission request) {
        ResponsePermission response = permissionService.createPermission(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Permission created successfully"));
    }

    // Get permission
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponsePermission>> getPermissionById(@PathVariable Long id) {
        ResponsePermission response = permissionService.getPermissionById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Permission retrieved successfully"));
    }

    // Get all permission
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ResponsePermission>>> getAllPermissions(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        PageResponse<ResponsePermission> response = permissionService.getAllPermissions(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(response, "Permissions retrieved successfully"));
    }

    // Update permission
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponsePermission>> updatePermission(
            @PathVariable Long id,
            @Valid @RequestBody RequestPermission request
    ) {
        ResponsePermission response = permissionService.updatePermission(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Permission updated successfully"));
    }

    // Delete permission
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Permission deleted successfully"));
    }
}