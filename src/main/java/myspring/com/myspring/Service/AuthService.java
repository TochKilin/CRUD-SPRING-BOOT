package myspring.com.myspring.Service;

import lombok.RequiredArgsConstructor;
import myspring.com.myspring.DTO.RequestLogin;
import myspring.com.myspring.DTO.RequestRegister;
import myspring.com.myspring.DTO.RequestUser;
import myspring.com.myspring.DTO.RequestUserProfile;
import myspring.com.myspring.DTO.ResponseLogin;
import myspring.com.myspring.DTO.ResponseUser;
import myspring.com.myspring.Exceptions.ResourceNotFoundException;
import myspring.com.myspring.Model.Role;
import myspring.com.myspring.Repository.RoleRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String DEFAULT_ROLE = "USER";

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public ResponseUser register(RequestRegister request) {
        Role defaultRole = roleRepository.findByName(DEFAULT_ROLE)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Default role " + DEFAULT_ROLE + " not found. Please create it first"));

        RequestUserProfile profile = new RequestUserProfile();
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setPhone(request.getPhone());

        RequestUser requestUser = new RequestUser();
        requestUser.setUsername(request.getUsername());
        requestUser.setEmail(request.getEmail());
        requestUser.setPassword(request.getPassword());
        requestUser.setStatus(true);
        requestUser.setRoleIds(Set.of(defaultRole.getId()));
        requestUser.setProfile(profile);

        return userService.createUser(requestUser);
    }

    public ResponseLogin login(RequestLogin request) {
        // បោះ BadCredentialsException បើ username ឬ password ខុស
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .sorted()
                .toList();

        return ResponseLogin.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationMs() / 1000)
                .username(userDetails.getUsername())
                .authorities(authorities)
                .build();
    }
}