package by.compit.tereh.service.model.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductElement {
    private Long id;
    private String code;
    private Long productId;
    private Long productHierarchyId;
    private Long bnId;
    private Long dbId;
}
