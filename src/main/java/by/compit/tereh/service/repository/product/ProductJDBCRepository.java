package by.compit.tereh.service.repository.product;

import antlr.ANTLRStringBuffer;
import by.compit.tereh.service.dto.LevelUpdateData;
import by.compit.tereh.service.model.product_hierarchy.Field;
import by.compit.tereh.service.model.product_hierarchy.ProductHierValue;
import by.compit.tereh.service.model.tree.TreeElement;
import javafx.beans.binding.StringBinding;
import org.apache.catalina.LifecycleState;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Repository
public class ProductJDBCRepository {

    @Autowired
    @Qualifier("jdbcTemplateCTL")
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    public Map<String,Object> getProductAsMapByProductIdAndGroupId(Long productId,String tableName){
        /*String nameOfTable = null;
        switch (productGroup.intValue()){
            case 1:nameOfTable = "CARD";break;
            case 2:nameOfTable = "CREDIT";break;
            case 3:nameOfTable = "DEBIT";break;
        }*/
        return jdbcTemplate.queryForMap("SELECT * FROM " + tableName + " WHERE ID = " + productId);
    }

    public int updateHierarchyLevel(List<Field> fields,String tableName,List<String> productsId){
        /*StringBuilder QUERY = new StringBuilder("UPDATE ");
        QUERY.append(tableName).append(" ");
        int current = 0;
        QUERY.append("SET ");
        for (Field field : fields){
            current++;
            QUERY.append(field.getFieldName());
            QUERY.append("=?");

            if(field.getDataType().equals("VARCHAR") || field.getDataType().equals("VARCHAR2") || field.getDataType().equals("DATE") ){
                if(field.getValue().equals("")){
                    QUERY.append("NULL");
                }else {
                    QUERY.append("'").append(field.getValue()).append("'");
                }
            }else{
                if(field.getValue().equals("")){
                    QUERY.append("NULL");
                }else {
                    QUERY.append(field.getValue());
                }

            }
            if(current != fields.size()){
                QUERY.append(" , ");
            }
        }

        current = 0;
        QUERY.append(" WHERE ID IN (");
        for (String id: productsId){
            current++;
            QUERY.append(id);
            if (current != productsId.size()){
                QUERY.append(",");
            }
        }
        QUERY.append(")");*/

        /**
         *Create dynamic query for update
         */
        StringBuilder QUERY = new StringBuilder("UPDATE " + tableName +" SET ");
        int current = 0;
        for(Field field:fields){
            current++;
            if(field.getFieldName().equals("ID")){
                continue;
            }
            QUERY.append(field.getFieldName()).append(" =:").append(field.getFieldName());
            if(current != fields.size()){
                QUERY.append(" , ");
            }
        }
        QUERY.append(" WHERE ID IN (");
        current = 0;
        for (String id: productsId){
            current++;
            QUERY.append(id);
            if (current != productsId.size()){
                QUERY.append(",");
            }
        }
        QUERY.append(")");
        Query query = entityManager.createNativeQuery(QUERY.toString());

        /**
         * Inject params for query
         */

        for(Field field:fields){
            if(field.getFieldName().equals("ID")){
                continue;
            }
            if(field.getDataType().equals("VARCHAR") || field.getDataType().equals("VARCHAR2") || field.getDataType().equals("DATE") ){
                if(field.getValue().equals("")){
                    query.setParameter(field.getFieldName(),new TypedParameterValue(StandardBasicTypes.STRING,null));
                }else {
                    query.setParameter(field.getFieldName(),new TypedParameterValue(StandardBasicTypes.STRING,field.getValue()));
                }
            }else{
                if(field.getValue().equals("")){
                    query.setParameter(field.getFieldName(),new TypedParameterValue(StandardBasicTypes.LONG,null));
                }else {
                    query.setParameter(field.getFieldName(),new TypedParameterValue(StandardBasicTypes.LONG,Long.parseLong(field.getValue())));
                }
            }
        }

        return query.executeUpdate();
    }

    public Long createRealProduct(List<Field> fields, String tableName){

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource());
        simpleJdbcInsert.withTableName(tableName);
        simpleJdbcInsert.usingGeneratedKeyColumns("ID");
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (Field field:fields) {
            if(field.getFieldName().equals("ID")){
                continue;
            }
            if(field.getDataType().equals("VARCHAR") || field.getDataType().equals("VARCHAR2") || field.getDataType().equals("DATE") ){
                if(field.getValue().equals("")){
                    parameters.addValue(field.getFieldName(),null);
                }else {
                    parameters.addValue(field.getFieldName(), field.getValue());
                }
            }else{
                if(field.getValue().equals("")){
                    parameters.addValue(field.getFieldName(),null);
                }else {
                    parameters.addValue(field.getFieldName(),field.getValue());
                }
            }
        }
        /*StringBuilder QUERY = new StringBuilder("INSERT INTO ");
        StringBuilder VALUES = new StringBuilder("VALUES (");
        QUERY.append(tableName).append(" ");
        QUERY.append("(");
        int current = 0;
        for (Field field : fields){
            current++;
            if(field.getFieldName().equals("ID")){
                continue;
            }
            QUERY.append(field.getFieldName());
            if(field.getDataType().equals("VARCHAR") || field.getDataType().equals("VARCHAR2") || field.getDataType().equals("DATE") ){
                if(field.getValue().equals("")){
                    VALUES.append("NULL");
                }else {
                    VALUES.append('\'').append(field.getValue()).append('\'');
                }
            }else{
                if(field.getValue().equals("")){
                    VALUES.append("NULL");
                }else {
                    VALUES.append(field.getValue());
                }
            }

            if(current != fields.size()){
                QUERY.append(" , ");
                VALUES.append(" , ");
            }
        }
        QUERY.append(" ) ");
        VALUES.append(" ) ");
        QUERY.append(VALUES);
        System.out.println(QUERY);*/
        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
        return id.longValue();
    }

    public String getNameOfProductById(String tableName,String nameOfField, Long id){
        List<String> name = jdbcTemplate.queryForList("SELECT " + nameOfField + " as name FROM " + tableName + " WHERE ID=" + id,String.class);
        return name.stream().findFirst().get();
    }

}
