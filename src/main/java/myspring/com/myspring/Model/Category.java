package myspring.com.myspring.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private String createdBy;

    private OffsetDateTime updatedAt;

    private String updatedBy; // Added this field to match your @PreUpdate method

    @PrePersist
    public void onCreate() {
        this.createdAt = OffsetDateTime.now();
        this.createdBy = "admin"; // user.name, user.id
        if (this.status == null) this.status = true;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
        this.updatedBy = "dara";
    }
}