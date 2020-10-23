package by.compit.tereh.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LevelUpdateData {
    private Map<String,Object> fields;
    private String tableName;
    private ArrayList<String> productsId;
}
