package myspring.com.myspring.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myspring.com.myspring.DTO.RequestBrand;
import myspring.com.myspring.DTO.ResponseBrand;
import myspring.com.myspring.Service.BrandService;
import myspring.com.myspring.Util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    // Create brand
    @PostMapping
    public ResponseEntity<ApiResponse<ResponseBrand>> create(@Valid @RequestBody RequestBrand dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.create(brandService.save(dto)));
    }

    // Get one of brands
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseBrand>> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(brandService.getOne(id)));
    }

    // Get all of brands
    @GetMapping
    public ResponseEntity<ApiResponse<List<ResponseBrand>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(brandService.getAll()));
    }

    // Updpate of brands
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseBrand>> update(@PathVariable Long id, @Valid @RequestBody RequestBrand dto) {
        return ResponseEntity.ok(ApiResponse.success(brandService.update(id, dto), "Brand updated successfully"));
    }

    // Delete brand
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Brand deleted successfully"));
    }
}