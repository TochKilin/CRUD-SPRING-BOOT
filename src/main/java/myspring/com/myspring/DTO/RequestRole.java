package myspring.com.myspring.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class RequestRole {
    @NotBlank(message = "Role name is required")
    private String name;

    private String description;
    private Boolean status;

    // មិនបាច់ដាក់ក៏បាន (Role គ្មាន Permission)
    private Set<Long> permissionIds;
}