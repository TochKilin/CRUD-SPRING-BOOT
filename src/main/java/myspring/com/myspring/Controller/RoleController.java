package myspring.com.myspring.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myspring.com.myspring.DTO.PageResponse;
import myspring.com.myspring.DTO.RequestRole;
import myspring.com.myspring.DTO.ResponseRole;
import myspring.com.myspring.Service.RoleService;
import myspring.com.myspring.Util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    // Create role
    @PostMapping
    public ResponseEntity<ApiResponse<ResponseRole>> createRole(@Valid @RequestBody RequestRole request) {
        ResponseRole response = roleService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Role created successfully"));
    }

    // Get role
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseRole>> getRoleById(@PathVariable Long id) {
        ResponseRole response = roleService.getRoleById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Role retrieved successfully"));
    }

    // Get all role
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ResponseRole>>> getAllRoles(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        PageResponse<ResponseRole> response = roleService.getAllRoles(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(response, "Roles retrieved successfully"));
    }

    // Update role
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseRole>> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RequestRole request
    ) {
        ResponseRole response = roleService.updateRole(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Role updated successfully"));
    }

    // Delete role
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Role deleted successfully"));
    }
}