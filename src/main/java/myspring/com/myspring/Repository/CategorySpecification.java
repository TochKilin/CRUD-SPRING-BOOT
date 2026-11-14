package myspring.com.myspring.Repository;

import myspring.com.myspring.Model.Category;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class CategorySpecification {

    // ត្រងតាម Name (ស្វែងរកបែប Contains និងមិនគិតតួអក្សរតូចធំ)
    public static Specification<Category> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.trim().isEmpty()) {
                return criteriaBuilder.conjunction(); // បើអត់មានតម្លៃ មិនបាច់ filter លើ field នេះទេ
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }

    // ត្រងតាម Status (Boolean)
    public static Specification<Category> hasStatus(Boolean status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction(); // បើអត់បញ្ជូន status មក មិនបាច់ filter
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Category> createdBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null && endDate == null) {
                return criteriaBuilder.conjunction(); // បើអត់មានដាក់ Date ទាំងពីរ មិនបាច់ filter
            }
            if (startDate != null && endDate != null) {
                return criteriaBuilder.between(root.get("createdAt"), startDate, endDate);
            } else if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate);
            }
        };
    }
}