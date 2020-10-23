package by.compit.tereh.service.repository;

import by.compit.tereh.service.model.product_hierarchy.ProductHierCrit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductHierCritRepository extends JpaRepository<ProductHierCrit,Long> {
    List<ProductHierCrit> findAllByProductGroupId(Long productGroupId);
    ProductHierCrit findByProductGroupIdAndName(Long productGroupId,String name);
}
