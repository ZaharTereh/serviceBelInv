package by.compit.tereh.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductGroupTreeDTO {
    private Long id;
    private Long hiId;
    private String name;
    private Byte lev;
    private Byte leaf;
    private String tableName;
}
