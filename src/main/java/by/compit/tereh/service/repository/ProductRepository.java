package by.compit.tereh.service.repository;

import by.compit.tereh.service.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByProductHierarchyId(Long productHierarchyId);
}
