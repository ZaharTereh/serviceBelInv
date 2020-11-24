package by.compit.tereh.service.model.tree;

import by.compit.tereh.service.dto.ProductDTO;
import by.compit.tereh.service.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreeElement {
    private List<TreeElement> child;

    private String id;
    private String hiId;
    private Byte lev;
    private String name;
    private Byte typ;
    private Long realHiId;
    private Long realId;

    private ProductElement productElement;

    @Override
    public String toString() {
        return "TreeElement{" +
                "lev=" + lev +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", hiId='" + hiId + '\'' +
                ", realId='" + realId + '\'' +
                ", realHiId='" + realHiId + '\'' +
                "child=" + child +
                '}';
    }
}
