package myspring.com.myspring.Mapper;


import myspring.com.myspring.DTO.RequestCategory;
import myspring.com.myspring.DTO.ResponseCategory;
import myspring.com.myspring.Model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toEntity(RequestCategory dto) {
        return Category.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .status(dto.getStatus())
                .description(dto.getDescription())
                .build();
    }

    public ResponseCategory toResponse(Category entity){
        return ResponseCategory.builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .status(entity.getStatus())
                .description(entity.getDescription())
                .createdBy(entity.getCreatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedBy(entity.getUpdatedBy())
                .updatedAt(entity.getUpdatedAt())

                .build();

    }
}
