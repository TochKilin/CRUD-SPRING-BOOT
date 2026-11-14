package myspring.com.myspring.Service;

import lombok.RequiredArgsConstructor;
import myspring.com.myspring.DTO.PageResponse;
import myspring.com.myspring.DTO.RequestProduct;
import myspring.com.myspring.DTO.ResponseProduct;
import myspring.com.myspring.Exceptions.DuplicateResourceException;
import myspring.com.myspring.Exceptions.ResourceNotFoundException;
import myspring.com.myspring.Mapper.ProductMapper;
import myspring.com.myspring.Model.Brand;
import myspring.com.myspring.Model.Category;
import myspring.com.myspring.Model.Product;
import myspring.com.myspring.Repository.BrandRepository;
import myspring.com.myspring.Repository.CategoryRepository;
import myspring.com.myspring.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;

    public ResponseProduct createProduct(RequestProduct request) {
        if (productRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Product with code " + request.getCode() + " already exists");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + request.getBrandId()));

        Product product = productMapper.toEntity(request, category, brand);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    public ResponseProduct getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.toResponse(product);
    }

    public PageResponse<ResponseProduct> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        List<String> allowedSortFields = List.of("id", "name", "price", "stockQuantity", "createdAt");
        String sortField = allowedSortFields.contains(sortBy) ? sortBy : "id";

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortField));

        Page<Product> page = productRepository.findAll(pageable);
        return PageResponse.mapFrom(page, productMapper::toResponse);
    }

    public ResponseProduct updateProduct(Long id, RequestProduct request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + request.getBrandId()));

        productMapper.updateEntity(product, request, category, brand);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }
}