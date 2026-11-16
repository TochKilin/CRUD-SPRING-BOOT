package myspring.com.myspring.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseLogin {
    private String token;
    private String tokenType;
    private long expiresIn; // វិនាទី
    private String username;
    private List<String> authorities;
}