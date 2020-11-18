package by.compit.tereh.service.repository;

import by.compit.tereh.service.model.product_hierarchy.ProductHierValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductHierValueRepository extends JpaRepository<ProductHierValue,Long> {
    List<ProductHierValue> findAllByProductId(Long productId);
}
