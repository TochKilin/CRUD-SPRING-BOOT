package myspring.com.myspring.Repository;

import myspring.com.myspring.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>, JpaSpecificationExecutor<Category> {

    // Fixed: changed 'existByCode' to 'existsByCode'
    boolean existsByCode(String code);
    List<Category> findByNameContainingIgnoreCase(String name);

}

