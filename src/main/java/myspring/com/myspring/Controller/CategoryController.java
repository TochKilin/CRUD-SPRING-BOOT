package myspring.com.myspring.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor; // ត្រូវប្រាកដថាមាន import នេះ
import myspring.com.myspring.DTO.PageResponse;
import myspring.com.myspring.DTO.RequestCategory;
import myspring.com.myspring.DTO.ResponseCategory;
import myspring.com.myspring.Service.CategoryService;
import myspring.com.myspring.Util.ApiResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Create category
    @PostMapping
    ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody RequestCategory dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.create(this.categoryService.save(dto)));
    }

    // Get list of category
    @GetMapping
    public ResponseEntity<ApiResponse<List<ResponseCategory>>> getList() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(this.categoryService.getList()));
    }

    // Get one of category
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseCategory>> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(this.categoryService.getOne(id)));
    }

    // Update of category
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseCategory>> update(@PathVariable Long id, @Valid @RequestBody RequestCategory dto) {
        return ResponseEntity.ok(ApiResponse.success(this.categoryService.update(id, dto), "Category updated successfully"));
    }

    // Delete of category
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Category deleted successfully"));
    }

    // Filters data
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<PageResponse<ResponseCategory>>> filterCategories(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDir,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {

        PageResponse<ResponseCategory> result = categoryService.filterCategories(
                name, status, startDate, endDate, sortBy, sortDir, pageNumber, pageSize
        );
        return ResponseEntity.ok(ApiResponse.success(result, "Paged and filtered categories retrieved successfully"));
    }

}