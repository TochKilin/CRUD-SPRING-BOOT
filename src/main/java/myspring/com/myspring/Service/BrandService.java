package myspring.com.myspring.Service;

import lombok.RequiredArgsConstructor;
import myspring.com.myspring.DTO.RequestBrand;
import myspring.com.myspring.DTO.ResponseBrand;
import myspring.com.myspring.DTO.ResponseCategory;
import myspring.com.myspring.Exceptions.DuplicateResourceException;
import myspring.com.myspring.Exceptions.ResourceNotFoundException;
import myspring.com.myspring.Mapper.BrandMapper;
import myspring.com.myspring.Model.Brand;
import myspring.com.myspring.Repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    // Create
    public ResponseBrand save(RequestBrand dto) {
        if (brandRepository.existsByCode(dto.getCode())) {
            throw new DuplicateResourceException("Brand code already exists: " + dto.getCode());
        }
        Brand saved = brandRepository.save(brandMapper.toEntity(dto));
        return brandMapper.toResponse(saved);
    }

    // Get one
    public ResponseBrand getOne(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
        return brandMapper.toResponse(brand);
    }

    // Get all
    public List<ResponseBrand> getAll() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toResponse)
                .toList();
    }

    // Update
    public ResponseBrand update(Long id, RequestBrand dto) {
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
        if (!existingBrand.getCode().equals(dto.getCode()) && brandRepository.existsByCode(dto.getCode())) {
            throw new DuplicateResourceException("Brand code already exists: " + dto.getCode());
        }

        existingBrand.setName(dto.getName());
        existingBrand.setCode(dto.getCode());
        Brand updated = brandRepository.save(existingBrand);
        return brandMapper.toResponse(updated);
    }

    // Update
    public void delete(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Brand not found with id: " + id);
        }
        brandRepository.deleteById(id);
    }

}