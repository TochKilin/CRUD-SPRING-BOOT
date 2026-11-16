package myspring.com.myspring.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class RequestUpdateUser {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    private Boolean status;

    @NotEmpty(message = "At least one role is required")
    private Set<Long> roleIds;

    @Valid
    private RequestUserProfile profile;
}