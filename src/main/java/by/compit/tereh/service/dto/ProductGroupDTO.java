package by.compit.tereh.service.dto;

import by.compit.tereh.service.model.product_hierarchy.ProductGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductGroupDTO {
    private Long id;
    private String name;
    private Long hiId;
    private String tableName;

    public static ProductGroupDTO toDTO(ProductGroup productGroup){
        return ProductGroupDTO.builder()
                .id(productGroup.getId())
                .hiId(productGroup.getHiId())
                .name(productGroup.getName())
                .tableName(productGroup.getTableName())
                .build();
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                " ,\"name\":\"" + name + '\"' +
                " ,\"hiId\":" + hiId +
                " ,\"tableName\":\"" + tableName + '\"' +
                '}';
    }
}
