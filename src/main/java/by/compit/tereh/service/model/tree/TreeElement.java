package by.compit.tereh.service.model.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreeElement {
    private TreeElement child;

    private String id;
    private String hiId;
    private Byte lev;
    private String name;
    private Byte typ;
    private Long realHiId;
    private Long realId;
}
