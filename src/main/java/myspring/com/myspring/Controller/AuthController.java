package myspring.com.myspring.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myspring.com.myspring.DTO.RequestLogin;
import myspring.com.myspring.DTO.RequestRegister;
import myspring.com.myspring.DTO.ResponseLogin;
import myspring.com.myspring.DTO.ResponseUser;
import myspring.com.myspring.Service.AuthService;
import myspring.com.myspring.Util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Register new user
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<ResponseUser>> register(@Valid @RequestBody RequestRegister request) {
        ResponseUser response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "User registered successfully"));
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<ResponseLogin>> login(@Valid @RequestBody RequestLogin request) {
        ResponseLogin response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Login successful"));
    }
}