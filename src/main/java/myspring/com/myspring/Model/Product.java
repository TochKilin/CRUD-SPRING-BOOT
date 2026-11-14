package myspring.com.myspring.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQuantity;

    private String description;

    @Column(nullable = false)
    private Boolean status;

    // ទំនាក់ទំនង Many-to-One ជាមួយ Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // ទំនាក់ទំនង Many-to-One ជាមួយ Brand
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private String createdBy;

    private OffsetDateTime updatedAt;
    private String updatedBy;

    @PrePersist
    public void onCreate() {
        this.createdAt = OffsetDateTime.now();
        this.createdBy = "admin";
        if (this.status == null) this.status = true;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
        this.updatedBy = "dara";
    }
}