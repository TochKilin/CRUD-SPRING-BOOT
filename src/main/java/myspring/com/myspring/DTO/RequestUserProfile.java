package myspring.com.myspring.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestUserProfile {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private String gender;
    private String avatarUrl;
}