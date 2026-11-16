package myspring.com.myspring.Mapper;

import lombok.RequiredArgsConstructor;
import myspring.com.myspring.DTO.*;
import myspring.com.myspring.Model.Role;
import myspring.com.myspring.Model.User;
import myspring.com.myspring.Model.UserProfile;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleMapper roleMapper;

    public User toEntity(RequestUser request, Set<Role> roles, String encodedPassword) {
        if (request == null) return null;

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .status(request.getStatus() != null ? request.getStatus() : true)
                .roles(new HashSet<>(roles))
                .build();

        // ត្រូវប្រើ setProfile ដើម្បីភ្ជាប់ 2 ខាង
        user.setProfile(toProfileEntity(request.getProfile()));
        return user;
    }

    public void updateEntity(User user, RequestUpdateUser request, Set<Role> roles) {
        if (request == null || user == null) return;

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        if (request.getStatus() != null) {
            user.setStatus(request.getStatus());
        }

        user.getRoles().clear();
        user.getRoles().addAll(roles);

        if (request.getProfile() != null) {
            if (user.getProfile() == null) {
                user.setProfile(toProfileEntity(request.getProfile()));
            } else {
                updateProfileEntity(user.getProfile(), request.getProfile());
            }
        }
    }

    public ResponseUser toResponse(User user) {
        if (user == null) return null;

        Set<ResponseRole> roles = user.getRoles().stream()
                .map(roleMapper::toResponse)
                .collect(Collectors.toSet());

        return ResponseUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .roles(roles)
                .profile(toProfileResponse(user.getProfile()))
                .createdBy(user.getCreatedBy())
                .updatedBy(user.getUpdatedBy())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    private UserProfile toProfileEntity(RequestUserProfile request) {
        if (request == null) return null;

        return UserProfile.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .avatarUrl(request.getAvatarUrl())
                .build();
    }

    private void updateProfileEntity(UserProfile profile, RequestUserProfile request) {
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setPhone(request.getPhone());
        profile.setAddress(request.getAddress());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setGender(request.getGender());
        profile.setAvatarUrl(request.getAvatarUrl());
    }

    private ResponseUserProfile toProfileResponse(UserProfile profile) {
        if (profile == null) return null;

        return ResponseUserProfile.builder()
                .id(profile.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .phone(profile.getPhone())
                .address(profile.getAddress())
                .dateOfBirth(profile.getDateOfBirth())
                .gender(profile.getGender())
                .avatarUrl(profile.getAvatarUrl())
                .build();
    }
}