package myspring.com.myspring.Mapper;

import lombok.RequiredArgsConstructor;
import myspring.com.myspring.DTO.RequestProduct;
import myspring.com.myspring.DTO.ResponseProduct;
import myspring.com.myspring.Model.Brand;
import myspring.com.myspring.Model.Category;
import myspring.com.myspring.Model.Product;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryMapper categoryMapper;
    private final BrandMapper brandMapper;

    public Product toEntity(RequestProduct request, Category category, Brand brand) {
        if (request == null) return null;

        return Product.builder()
                .name(request.getName())
                .code(request.getCode())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : true)
                .category(category)
                .brand(brand)
                .build();
    }

    public void updateEntity(Product product, RequestProduct request, Category category, Brand brand) {
        if (request == null || product == null) return;

        product.setName(request.getName());
        product.setCode(request.getCode());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            product.setStatus(request.getStatus());
        }
        product.setCategory(category);
        product.setBrand(brand);
    }

    public ResponseProduct toResponse(Product product) {
        if (product == null) return null;

        return ResponseProduct.builder()
                .id(product.getId())
                .name(product.getName())
                .code(product.getCode())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .description(product.getDescription())
                .status(product.getStatus())
                .category(product.getCategory() != null ? categoryMapper.toResponse(product.getCategory()) : null) // ⬅️ ប្រើប្រាស់ instance របស់ categoryMapper
                .brand(product.getBrand() != null ? brandMapper.toResponse(product.getBrand()) : null)
                .createdBy(product.getCreatedBy())
                .updatedBy(product.getUpdatedBy())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}