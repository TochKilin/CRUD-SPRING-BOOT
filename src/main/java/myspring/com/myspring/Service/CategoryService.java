package myspring.com.myspring.Service;

import lombok.RequiredArgsConstructor;
import myspring.com.myspring.DTO.PageResponse;
import myspring.com.myspring.DTO.RequestCategory;
import myspring.com.myspring.DTO.ResponseCategory;
import myspring.com.myspring.Exceptions.ResourceNotFoundException;
import myspring.com.myspring.Mapper.CategoryMapper;
import myspring.com.myspring.Model.Category;
import myspring.com.myspring.Repository.CategoryRepository;
import myspring.com.myspring.Repository.CategorySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // Create
    public ResponseCategory save(RequestCategory dto){
        Category category = categoryMapper.toEntity(dto);
        Category categorySave = this.categoryRepository.save(category);
        return categoryMapper.toResponse(categorySave);
    }

    // Get list
    public List<ResponseCategory> getList(){
        return this.categoryRepository.findAll().stream().map(categoryMapper::toResponse).toList();
    }

    // Get one
    public ResponseCategory getOne(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return categoryMapper.toResponse(category);
    }

    // Update
    public ResponseCategory update(Long id, RequestCategory dto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        existingCategory.setName(dto.getName());
        existingCategory.setCode(dto.getCode());
        existingCategory.setDescription(dto.getDescription());
        existingCategory.setStatus(dto.getStatus());

        Category updated = categoryRepository.save(existingCategory);
        return categoryMapper.toResponse(updated);
    }

    // Delete
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    // Filter data with Allowed Sort Properties (Whitelist)
    public PageResponse<ResponseCategory> filterCategories(
            String name,
            Boolean status,
            LocalDate startDate,
            LocalDate endDate,
            String sortBy,
            String sortDir,
            int pageNumber,
            int pageSize
    ) {
        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(23, 59, 59) : null;

        Specification<Category> spec = Specification.where(CategorySpecification.hasName(name))
                .and(CategorySpecification.hasStatus(status))
                .and(CategorySpecification.createdBetween(startDateTime, endDateTime));

        // Allowed Sort Properties (Whitelist)
        List<String> allowedSortFields = List.of("id", "name", "code", "status", "createdAt", "updatedAt");
        String sortField = (sortBy != null && allowedSortFields.contains(sortBy)) ? sortBy : "id";

        Sort.Direction direction = (sortDir != null && sortDir.equalsIgnoreCase("asc"))
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Sort sort = Sort.by(direction, sortField);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> categoryPage = categoryRepository.findAll(spec, pageable);

        // ប្រើប្រាស់ PageResponse.mapFrom ជាមួយ Mapper យ៉ាងមានរបៀបរៀបរយ
        return PageResponse.mapFrom(categoryPage, categoryMapper::toResponse);
    }
}