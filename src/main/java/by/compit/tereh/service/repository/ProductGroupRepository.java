package by.compit.tereh.service.repository;

import by.compit.tereh.service.dto.ProductGroupTreeDTO;
import by.compit.tereh.service.model.product_hierarchy.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroup,Long> {
    Optional<ProductGroup> findById(Long id);
}
