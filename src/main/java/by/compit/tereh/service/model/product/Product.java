package by.compit.tereh.service.model.product;

import by.compit.tereh.service.model.product_hierarchy.ProductGroup;
import by.compit.tereh.service.model.product_hierarchy.ProductHierValue;
import by.compit.tereh.service.model.product_hierarchy.ProductHierarchy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductHierValue> productHierValues;

    @Column(name = "BN_ID")
    private Long bnId;

    @Column(name = "DB_ID")
    private Long dbId;
}
