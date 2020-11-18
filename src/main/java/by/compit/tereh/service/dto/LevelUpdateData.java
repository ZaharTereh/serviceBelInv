package by.compit.tereh.service.dto;

import by.compit.tereh.service.model.product_hierarchy.Field;
import by.compit.tereh.service.model.product_hierarchy.ProductHierValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LevelUpdateData {
    private ArrayList<Field> fields;
    private String tableName;
    private ArrayList<String> productsId;
    private String hierarchyId;
    private String productGroupId;
    private String fieldNameProductCode;
    private List<ProductHierValue> productHierValues;
}
