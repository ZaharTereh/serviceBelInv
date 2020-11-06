package by.compit.tereh.service.repository;

import by.compit.tereh.service.model.product_hierarchy.ProductHierarchy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductHierarchyRepository extends JpaRepository<ProductHierarchy,Long> {
    List<ProductHierarchy> findAllByProductGroup_Id(Long productGroupId);
    List<ProductHierarchy> findProductHierarchiesByName(String name);
    Optional<ProductHierarchy> findById(Long hierarchyId);

    @Query("select id,name from ProductHierarchy")
    List<ProductHierarchy> findAllNamesAndId();

    List<ProductHierarchy> findAll();
}
