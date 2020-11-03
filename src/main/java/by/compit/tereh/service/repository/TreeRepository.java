package by.compit.tereh.service.repository;

import by.compit.tereh.service.model.product_hierarchy.ProductGroup;
import by.compit.tereh.service.model.product_hierarchy.ProductHierarchy;
import by.compit.tereh.service.model.tree.TreeElement;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class TreeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String QUERY_GET_HIERARCHY_TREE = "WITH" +
            "    t3 AS (  SELECT id id," +
            "                    hi_id hi_id," +
            "                    name," +
            "                    LEVEL lev" +
            "               FROM product_group" +
            "              WHERE     LEVEL <= 3" +
            "                    AND (   (LEVEL = 1 AND (:a1 IS NULL OR id = :a1))" +
            "                         OR (LEVEL = 2 AND (:a2 IS NULL OR id = :a2))" +
            "                         OR (LEVEL = 3 AND (:a3 IS NULL OR id = :a3)))" +
            "         CONNECT BY PRIOR id = hi_id" +
            "         START WITH hi_id IS NULL)," +
            "    t1 AS (  " +
            "    SELECT TO_CHAR (id) id," +
            "                    LEV         lev," +
            "                    TO_CHAR (hi_id) hi_id," +
            "                    name," +
            "                    0 typ," +
            "                    NULL PRODUCT_HIER_ID" +
            "               FROM t3" +
            "              WHERE (hi_id IS NULL OR PRIOR id = hi_id)" +
            "         CONNECT BY PRIOR id = hi_id START WITH hi_id IS NULL" +
            "         UNION ALL" +
            "         SELECT TO_CHAR (s.id) || LPAD (v.VALUE, 10, ' ')" +
            "                    id," +
            "                s.LEV + 3" +
            "                    lev," +
            "                CASE" +
            "                    WHEN lev = 1" +
            "                    THEN (SELECT TO_CHAR (product_group)" +
            "                           FROM PRODUCT_HIERARCHY" +
            "                          WHERE id = s.PRODUCT_HIER_ID)" +
            "                    ELSE" +
            "                        (SELECT TO_CHAR (s1.id) || LPAD (v1.VALUE, 10, ' ')" +
            "                           FROM PRODUCT_HIER_STRUCT s1, PRODUCT_HIER_VALUE v1" +
            "                          WHERE     s1.id = v1.id" +
            "                                AND s1.CRIT IS NOT NULL" +
            "                                AND s1.PRODUCT_HIER_ID = s.PRODUCT_HIER_ID" +
            "                                AND s1.lev = s.lev - 1" +
            "                                AND s1.CRIT IS NOT NULL)" +
            "                END" +
            "                    hi_id," +
            "                PKG_HIER.EXEC_SQL (" +
            "                    sSQL =>" +
            "                           'select '||c.FIELD_TEXT||' from '||c.TABLE_NAME||' where to_char('||c.FIELD_NAME||') = '''" +
            "                        ||v.VALUE||'''')" +
            "                    name," +
            "                    1 typ," +
            "                    s.PRODUCT_HIER_ID" +
            "           FROM PRODUCT_HIER_STRUCT  s," +
            "                PRODUCT_HIER_VALUE   v," +
            "                PRODUCT_HIER_CRIT    c" +
            "          WHERE s.id = v.id AND s.CRIT IS NOT NULL AND s.CRIT = c.ID " +
            "          union " +
            "          select TO_CHAR (ps.id) || LPAD (pv.VALUE, 10, ' ') || LPAD(TO_CHAR (p.id),10,' ') id," +
            "                    ph.LEVEL_QUANT + 4 lev," +
            "                    TO_CHAR (ps.id) || LPAD (pv.VALUE, 10, ' ') hi_id," +
            "                    PRODUCT_CODE name," +
            "                    2 typ," +
            "                    p.PRODUCT_HIERARCHY_ID PRODUCT_HIER_ID" +
            "              from product p, PRODUCT_HIERARCHY ph, PRODUCT_HIER_STRUCT ps, PRODUCT_HIER_VALUE pv " +
            "              where p.PRODUCT_HIERARCHY_ID = ph.ID " +
            "                and ph.ID = ps.PRODUCT_HIER_ID and ps.CRIT is not NULL and ps.LEV = ph.LEVEL_QUANT" +
            "                and pv.ID = ps.ID" +
            "          )" +
            "    SELECT id," +
            "           HI_ID," +
            "           LEV," +
            "           CASE WHEN lev <= 4 THEN NULL ELSE PRIOR NAME || ' ' END || NAME name," +
            "           TYP, " +
            "           case when lev = 4 then NULL" +
            "                when typ = 2 then NULL " +
            "                when lev >= 5 then TO_NUMBER(substr(HI_ID,1,length(ID) - 10)) " +
            "                ELSE TO_NUMBER(HI_ID) " +
            "           END REAL_HI_ID," +
            "           case when typ = 1 then TO_NUMBER(substr(ID,1,length(ID) - 10)) " +
            "                when typ = 2 then TO_NUMBER(substr(ID,1,length(ID) - 20)) " +
            "                ELSE TO_NUMBER(ID) " +
            "           END REAL_ID " +
            "      FROM t1 " +
            "CONNECT BY PRIOR id = hi_id " +
            "START WITH hi_id IS NULL";

    @SuppressWarnings("unchecked")
    public List<TreeElement> getTree(Long pg1, Long pg2, Long pg3){
        List<Tuple> tupleList = entityManager.createNativeQuery(QUERY_GET_HIERARCHY_TREE,Tuple.class)
                .setParameter("a1", new TypedParameterValue(StandardBasicTypes.LONG, pg1))
                .setParameter("a2", new TypedParameterValue(StandardBasicTypes.LONG, pg2))
                .setParameter("a3", new TypedParameterValue(StandardBasicTypes.LONG, pg3))
                .getResultList();

        return tupleList.stream().map(tuple->TreeElement.builder()
                .id(tuple.get("ID",String.class))
                .hiId(tuple.get("HI_ID", String.class))
                .lev(tuple.get("LEV", BigDecimal.class).byteValue())
                .name(tuple.get("NAME", String.class))
                .typ(tuple.get("TYP", BigDecimal.class).byteValue())
                .realHiId(convertToLong(tuple.get("REAL_HI_ID", BigDecimal.class)))
                .realId(tuple.get("REAL_ID", BigDecimal.class).longValue())
                .build()).collect(Collectors.toList());
    }

    private Long convertToLong(BigDecimal bigDecimal){
        if(bigDecimal == null){
            return null;
        }else {
            return bigDecimal.longValue();
        }
    }
}
