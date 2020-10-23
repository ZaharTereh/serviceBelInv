package by.compit.tereh.service.model.product_hierarchy;

import by.compit.tereh.service.model.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCT_HIERARCHY")
public class ProductHierarchy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ACTIVITY")
    private Integer activity;

    @Column(name = "LEVEL_QUANT")
    private Integer levelQuant;

    @Column(name = "DATE_BEGIN")
    private LocalDate dateBegin;

    @Column(name = "DATE_END")
    private LocalDate dateEnd;

    @Column(name = "CREATED_BY")
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_GROUP")
    private ProductGroup productGroup;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "productHierarchy",fetch = FetchType.EAGER)
    private List<ProductHierarchyStruct> productHierarchyStructs;


    @Override
    public String toString() {
        return "ProductHierarchy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", activity=" + activity +
                ", levelQuant=" + levelQuant +
                ", dateBegin=" + dateBegin +
                ", dataEnd=" + dateEnd +
                ", user_id=" + userId +
                ", productHierarchyStructs=" + productHierarchyStructs +
                '}';
    }
}
