package myspring.com.myspring.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class ResponsePermission {
    private Long id;
    private String name;
    private String description;
    private Boolean status;
    private String createdBy;
    private String updatedBy;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}