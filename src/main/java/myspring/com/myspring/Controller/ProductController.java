package myspring.com.myspring.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myspring.com.myspring.DTO.PageResponse;
import myspring.com.myspring.DTO.RequestProduct;
import myspring.com.myspring.DTO.ResponseProduct;
import myspring.com.myspring.Service.ProductService;
import myspring.com.myspring.Util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Create product
    @PostMapping
    public ResponseEntity<ApiResponse<ResponseProduct>> createProduct(@Valid @RequestBody RequestProduct request) {
        ResponseProduct response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Product created successfully"));
    }

    // Get product
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseProduct>> getProductById(@PathVariable Long id) {
        ResponseProduct response = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Product retrieved successfully"));
    }

    // Get all product
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ResponseProduct>>> getAllProducts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        PageResponse<ResponseProduct> response = productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(response, "Products retrieved successfully"));
    }

    // Update product
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseProduct>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody RequestProduct request
    ) {
        ResponseProduct response = productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Product updated successfully"));
    }

    // Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Product deleted successfully"));
    }
}