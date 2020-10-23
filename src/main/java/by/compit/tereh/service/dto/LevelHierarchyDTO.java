package by.compit.tereh.service.dto;

import by.compit.tereh.service.model.product_hierarchy.ProductHierCrit;
import by.compit.tereh.service.model.product_hierarchy.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LevelHierarchyDTO {
    List<ProductHierCrit> criterionList;
    List<Field> fieldList;
}