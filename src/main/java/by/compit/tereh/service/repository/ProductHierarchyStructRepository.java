package by.compit.tereh.service.repository;

import by.compit.tereh.service.model.product_hierarchy.ProductHierarchy;
import by.compit.tereh.service.model.product_hierarchy.ProductHierarchyStruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductHierarchyStructRepository extends JpaRepository<ProductHierarchyStruct,Long> {
    void deleteAllByProductHierarchy(ProductHierarchy productHierarchy);
}
