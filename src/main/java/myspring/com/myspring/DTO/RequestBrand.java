package myspring.com.myspring.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestBrand {
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Code is required")
    private String code;
    private String description;
    private Boolean status;
}