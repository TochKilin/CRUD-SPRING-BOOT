package myspring.com.myspring.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Builder
public class ResponseUser {
    private Long id;
    private String username;
    private String email;
    private Boolean status;

    private Set<ResponseRole> roles;
    private ResponseUserProfile profile;

    private String createdBy;
    private String updatedBy;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}