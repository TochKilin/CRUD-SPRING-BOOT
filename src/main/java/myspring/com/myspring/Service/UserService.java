package myspring.com.myspring.Service;

import lombok.RequiredArgsConstructor;
import myspring.com.myspring.DTO.PageResponse;
import myspring.com.myspring.DTO.RequestUpdateUser;
import myspring.com.myspring.DTO.RequestUser;
import myspring.com.myspring.DTO.ResponseUser;
import myspring.com.myspring.Exceptions.DuplicateResourceException;
import myspring.com.myspring.Exceptions.ResourceNotFoundException;
import myspring.com.myspring.Mapper.UserMapper;
import myspring.com.myspring.Model.Role;
import myspring.com.myspring.Model.User;
import myspring.com.myspring.Repository.RoleRepository;
import myspring.com.myspring.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseUser createUser(RequestUser request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("User with username " + request.getUsername() + " already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User with email " + request.getEmail() + " already exists");
        }

        Set<Role> roles = findRoles(request.getRoleIds());
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = userMapper.toEntity(request, roles, encodedPassword);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public ResponseUser getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public PageResponse<ResponseUser> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        List<String> allowedSortFields = List.of("id", "username", "email", "createdAt");
        String sortField = allowedSortFields.contains(sortBy) ? sortBy : "id";

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortField));

        Page<User> page = userRepository.findAll(pageable);
        return PageResponse.mapFrom(page, userMapper::toResponse);
    }

    @Transactional
    public ResponseUser updateUser(Long id, RequestUpdateUser request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!user.getUsername().equals(request.getUsername()) && userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("User with username " + request.getUsername() + " already exists");
        }
        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User with email " + request.getEmail() + " already exists");
        }

        Set<Role> roles = findRoles(request.getRoleIds());

        userMapper.updateEntity(user, request, roles);
        User updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    // រកមើល Role ទាំងអស់តាម id បើមានមួយមិនឃើញ 404
    private Set<Role> findRoles(Set<Long> roleIds) {
        List<Role> roles = roleRepository.findAllById(roleIds);
        if (roles.size() != roleIds.size()) {
            Set<Long> foundIds = new HashSet<>();
            roles.forEach(role -> foundIds.add(role.getId()));
            Set<Long> missing = new HashSet<>(roleIds);
            missing.removeAll(foundIds);
            throw new ResourceNotFoundException("Role not found with id: " + missing);
        }
        return new HashSet<>(roles);
    }
}