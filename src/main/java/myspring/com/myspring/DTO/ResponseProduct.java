package myspring.com.myspring.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class ResponseProduct {
    private Long id;
    private String name;
    private String code;
    private BigDecimal price;
    private Integer stockQuantity;
    private String description;
    private Boolean status;

    // បង្ហាញព័ត៌មានលម្អិតខ្លះៗរបស់ Category និង Brand ជំនួសឱ្យការបង្ហាញ ID
    private ResponseCategory category;
    private ResponseBrand brand;

    private String createdBy;
    private String updatedBy;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}