package myspring.com.myspring.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
//@Setter
//@Getter
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(nullable = false,unique = true)
    private String name;

    private String description;

    private String test_colo1;
}
