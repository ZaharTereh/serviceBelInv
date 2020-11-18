package by.compit.tereh.service.model.product_hierarchy;

import by.compit.tereh.service.model.product.Product;
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
@Table(name = "PRODUCT_HIER_VALUE")
public class ProductHierValue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "IDSTRUCT")
    private ProductHierarchyStruct productHierarchyStruct;

    @ManyToOne
    @JoinColumn(name = "IDPRODUCT")
    private Product product;

    @Column(name = "VALUE")
    private String value;
}
