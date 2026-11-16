package myspring.com.myspring.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myspring.com.myspring.DTO.PageResponse;
import myspring.com.myspring.DTO.RequestUpdateUser;
import myspring.com.myspring.DTO.RequestUser;
import myspring.com.myspring.DTO.ResponseUser;
import myspring.com.myspring.Service.UserService;
import myspring.com.myspring.Util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Create user
    @PostMapping
    public ResponseEntity<ApiResponse<ResponseUser>> createUser(@Valid @RequestBody RequestUser request) {
        ResponseUser response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "User created successfully"));
    }

    // Get user
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseUser>> getUserById(@PathVariable Long id) {
        ResponseUser response = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "User retrieved successfully"));
    }

    // Get all user
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ResponseUser>>> getAllUsers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        PageResponse<ResponseUser> response = userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(response, "Users retrieved successfully"));
    }

    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseUser>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody RequestUpdateUser request
    ) {
        ResponseUser response = userService.updateUser(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "User updated successfully"));
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(null, "User deleted successfully"));
    }
}