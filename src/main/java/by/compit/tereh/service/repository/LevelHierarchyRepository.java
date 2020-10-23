package by.compit.tereh.service.repository;

import by.compit.tereh.service.dto.LevelHierarchyDTO;
import by.compit.tereh.service.model.product_hierarchy.Criterion;
import by.compit.tereh.service.model.product_hierarchy.Field;
import by.compit.tereh.service.model.product_hierarchy.ProductHierCrit;
import javafx.beans.binding.StringBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class LevelHierarchyRepository {
    @Autowired
    @Qualifier("jdbcTemplateHierarchy")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductHierCritRepository productHierCritRepository;

    /*private static final String GET_CRITERION_OF_HIERARCHY = "select phc.*,c.COMMENTS from PRODUCT_HIER_CRIT phc, user_col_comments c " +
            "where c.table_name = phc.TABLE_NAME" +
            "  and c.COLUMN_NAME = phc.FIELD_NAME" +
            " and phc.GROUP_ID = ?";

    private static final String GET_FIELD_OF_HIERARCHY_SQL = "select table_name, column_name FIELD_NAME, comments from user_col_comments c " +
            "where TABLE_NAME = 'CARD' and ? = 1 and COLUMN_NAME!='ID' AND COMMENTS is not NULL " +
            "union all " +
            "select table_name, column_name, comments from user_col_comments c " +
            "where TABLE_NAME = 'CREDIT' and ? = 2 and COLUMN_NAME!='ID' AND COMMENTS is not NULL " +
            "union all " +
            "select table_name, column_name, comments from user_col_comments c " +
            "where TABLE_NAME = 'DEBIT' and ? = 3 and COLUMN_NAME!='ID' AND COMMENTS is not NULL";


    private static final String GET_FIELD = "select table_name, column_name FIELD_NAME, comments from user_col_comments c "+
            "where TABLE_NAME = ? and COLUMN_NAME!='ID' AND COMMENTS is not NULL ";*/


    public LevelHierarchyDTO getLevelHierarchyDTOList(String tableName,Long productGroupId) {
        LevelHierarchyDTO levelHierarchyDTO = new LevelHierarchyDTO();
        levelHierarchyDTO.setCriterionList(productHierCritRepository.findAllByProductGroupId(productGroupId));
        levelHierarchyDTO.setFieldList(getFieldList(tableName));

        /*Object [] value = {productGroupId};
        for (Map<String,Object> entity:jdbcTemplate.queryForList(GET_CRITERION_OF_HIERARCHY,value)) {
            levelHierarchyDTO.getCriterionList().add(ProductHierCrit.builder()
                    .name(entity.get("COMMENTS").toString())
                    .tableName(entity.get("TABLE_NAME").toString())
                    .id(Long.parseLong(entity.get("ID").toString()))
                    .build());
        }*/

        return levelHierarchyDTO;
    }

    public List<Criterion> getCriterionList(ProductHierCrit crit){
        List<Criterion> criterionList = new ArrayList<>();

        List<Map<String,Object>> result = jdbcTemplate.queryForList("SELECT "+crit.getFieldName()+","+crit.getFieldText()+" FROM "+crit.getTableName());
        for (Map<String,Object> entity:result) {
            criterionList.add(Criterion.builder()
                    .name(entity.get(crit.getFieldText()).toString())
                    .id(Long.parseLong(entity.get(crit.getFieldName()).toString()))
                    .build());
        }
        return criterionList;
    }

    public List<Field> getFieldList(String tableName){
        List<Field> fieldList = new ArrayList<>();

        /*StringBuilder query = new StringBuilder("select table_name, column_name FIELD_NAME,f.data_type comments from user_col_comments c where TABLE_NAME = ");
        query.append("'").append(tableName).append("'");
        query.append(" and COLUMN_NAME!='ID' AND COMMENTS is not NULL ");*/

        StringBuilder query = new StringBuilder("select c.table_name, c.column_name, c.comments, f.data_type, f.data_length from user_col_comments c, user_tab_columns f\n" +
                "where c.TABLE_NAME = f.TABLE_NAME and c.COLUMN_NAME = f.COLUMN_NAME and c.TABLE_NAME = ");
        query.append("'").append(tableName).append("'");

        Long id = 1L;
        for (Map<String,Object> entity:jdbcTemplate.queryForList(query.toString())) {
            fieldList.add(Field.builder()
                    .id(id)
                    .name(entity.get("COMMENTS").toString())
                    .tableName(entity.get("TABLE_NAME").toString())
                    .fieldName(entity.get("COLUMN_NAME").toString())
                    .dataType(entity.get("DATA_TYPE").toString())
                    .dataLength(((BigDecimal) entity.get("DATA_LENGTH")).intValue())
                    .build());
            ++id;
        }
        return fieldList;
    }
}
