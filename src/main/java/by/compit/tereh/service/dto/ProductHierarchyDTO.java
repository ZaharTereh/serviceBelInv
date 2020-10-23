package by.compit.tereh.service.dto;

import by.compit.tereh.service.model.product_hierarchy.ProductGroup;
import by.compit.tereh.service.model.product_hierarchy.ProductHierarchy;
import by.compit.tereh.service.model.product_hierarchy.ProductHierarchyStruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductHierarchyDTO {

    private Long id;
    private String name;
    private Integer activity;
    private Integer levelQuant;
    private LocalDate dateBegin;
    private LocalDate dateEnd;
    private Integer userId;
    private ProductGroupDTO productGroupDTO;
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

    public static ProductHierarchyDTO toDTO(ProductHierarchy productHierarchy){
        return ProductHierarchyDTO.builder()
                .id(productHierarchy.getId())
                .name(productHierarchy.getName())
                .activity(productHierarchy.getActivity())
                .levelQuant(productHierarchy.getLevelQuant())
                .dateBegin(productHierarchy.getDateBegin())
                .dateEnd(productHierarchy.getDateEnd())
                .userId(productHierarchy.getUserId())
                .productGroupDTO(ProductGroupDTO.toDTO(productHierarchy.getProductGroup()))
                .productHierarchyStructs(productHierarchy.getProductHierarchyStructs())
                .build();
    }

}
