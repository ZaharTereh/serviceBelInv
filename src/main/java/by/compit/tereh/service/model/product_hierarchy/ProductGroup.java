package by.compit.tereh.service.model.product_hierarchy;

import by.compit.tereh.service.model.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCT_GROUP")
public class ProductGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "HI_ID")
    private Long hiId;

    @Column(name = "TABLE_NAME")
    private String tableName;

    @OneToMany(mappedBy = "productGroup",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<ProductHierarchy> productHierarchies = new ArrayList<>();

}
