package by.compit.tereh.service.model.product;

import by.compit.tereh.service.model.product_hierarchy.ProductGroup;
import by.compit.tereh.service.model.product_hierarchy.ProductHierarchy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_GROUP")
    private ProductGroup productGroup;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_HIERARCHY_ID")
    private ProductHierarchy productHierarchy;

    @Column(name = "BN_ID")
    private Long bn_id;

    @Column(name = "DB_ID")
    private Long db_id;
}