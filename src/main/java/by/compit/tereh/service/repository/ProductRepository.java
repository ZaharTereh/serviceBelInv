package by.compit.tereh.service.repository;

import by.compit.tereh.service.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByProductHierarchyId(Long productHierarchyId);
    Optional<Product> findById(Long id);
    List<Product> findAllByProductGroupId(Long productGroupId);
    List<Product> findAllByProductGroupIdAndBnId(Long productGroupId,Long bnId);
    List<Product> findAllByProductGroupIdAndBnIdAndDbId(Long productGroupId,Long bnId,Long dbId);

}
