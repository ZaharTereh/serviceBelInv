package by.compit.tereh.service.repository.product;

import by.compit.tereh.service.dto.LevelUpdateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository
public class ProductJDBCRepository {

    @Autowired
    @Qualifier("jdbcTemplateCTL")
    private JdbcTemplate jdbcTemplate;

    private static final String GET_CARD_BY_HIERARCHY_ID = "SELECT * FROM ? WHERE ID = ?";

    public Map<String,Object> getProductAsMapByProductIdAndGroupId(Long productId,Long productGroup){
        String nameOfTable = null;
        switch (productGroup.intValue()){
            case 1:nameOfTable = "CARD";break;
            case 2:nameOfTable = "CREDIT";break;
            case 3:nameOfTable = "DEBIT";break;
        }
        return jdbcTemplate.queryForMap("SELECT * FROM " + nameOfTable + " WHERE ID = " + productId);
    }

    public int updateHierarchyLevel(LevelUpdateData data){
        StringBuilder QUERY = new StringBuilder("UPDATE ");
        QUERY.append(data.getTableName()).append(" ");
        int current = 0;
        QUERY.append("SET ");
        for (Map.Entry<String, Object> entry : data.getFields().entrySet()){
            current++;
            QUERY.append(entry.getKey());
            QUERY.append(" = ");
            QUERY.append(entry.getValue().toString());
            if(entry.getValue().toString().equals("")){
                QUERY.append("NULL");
            }
            if(current != data.getFields().size()){
                QUERY.append(" , ");
            }
        }
        current = 0;
        QUERY.append(" WHERE ID IN (");
        for (String string: data.getProductsId()){
            current++;
            QUERY.append(string);
            if (current != data.getProductsId().size()){
                QUERY.append(",");
            }
        }
        QUERY.append(")");

        return jdbcTemplate.update(QUERY.toString());
    }
}
