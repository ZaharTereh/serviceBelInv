package by.compit.tereh.service.model.product_hierarchy;

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
@Table(name = "PRODUCT_HIER_CRIT")
public class ProductHierCrit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "CRITERY_NAME")
    private String name;

    @Column(name = "TABLE_NAME")
    private String tableName;

    @Column(name = "FIELD_NAME")
    private String fieldName;

    @Column(name = "FIELD_TEXT")
    private String fieldText;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private ProductGroup productGroup;
}
