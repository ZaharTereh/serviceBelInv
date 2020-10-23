package by.compit.tereh.service.model.product_hierarchy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Level {
    private Integer level;
    private List<Criterion> criterionList;
    private List<Field> fieldList = new ArrayList<>();
}
