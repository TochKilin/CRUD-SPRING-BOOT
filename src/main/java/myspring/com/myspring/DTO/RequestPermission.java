package myspring.com.myspring.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestPermission {
    @NotBlank(message = "Permission name is required")
    private String name;

    private String description;
    private Boolean status;
}