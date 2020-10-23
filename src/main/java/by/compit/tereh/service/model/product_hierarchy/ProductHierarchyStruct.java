package by.compit.tereh.service.model.product_hierarchy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCT_HIER_STRUCT")
public class ProductHierarchyStruct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_HIER_ID")
    @JsonIgnore
    private ProductHierarchy productHierarchy;

    @Column(name = "LEV")
    private Integer level;

    @Column(name = "TABLE_NAME")
    private String tableName;

    @Column(name = "FIELD_NAME")
    private String fieldName;

    @Column(name = "CRIT")
    private Long crit;

    @Override
    public String toString() {
        StringBuilder fieldNameForJSON = new StringBuilder();
        char letter;
        for (int i=0;i<fieldName.length();i++) {
            letter = fieldName.charAt(i);
            if(letter == '\"' || letter == '\''){
                fieldNameForJSON.append('\\');
            }
            fieldNameForJSON.append(letter);
        }

        return "{" +
                "\"id\":" + id +
                " ,\"productHierarchy\":" + productHierarchy.getId() +
                " ,\"level\":" + level +
                " ,\"tableName\":\"" + tableName + '\"' +
                " ,\"fieldName\":\"" + fieldNameForJSON + '\"' +
                " ,\"crit\":" + crit +
                '}';
    }

}
