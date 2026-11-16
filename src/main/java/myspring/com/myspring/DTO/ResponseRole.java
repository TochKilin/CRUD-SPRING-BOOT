package myspring.com.myspring.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Builder
public class ResponseRole {
    private Long id;
    private String name;
    private String description;
    private Boolean status;

    private Set<ResponsePermission> permissions;

    private String createdBy;
    private String updatedBy;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}