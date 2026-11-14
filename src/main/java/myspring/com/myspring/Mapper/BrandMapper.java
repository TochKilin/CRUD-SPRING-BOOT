package myspring.com.myspring.Mapper;

import myspring.com.myspring.DTO.RequestBrand;
import myspring.com.myspring.DTO.ResponseBrand;
import myspring.com.myspring.Model.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public Brand toEntity(RequestBrand dto) {
        return Brand.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .build();
    }

    public ResponseBrand toResponse(Brand entity) {
        return ResponseBrand.builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .createdBy(entity.getCreatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedBy(entity.getUpdatedBy())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}