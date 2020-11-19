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

    private static final String QUERY_GET_HIERARCHY_TREE = "WITH\n" +
            "    t3 AS (  SELECT id id,\n" +
            "                    hi_id hi_id,\n" +
            "                    name,\n" +
            "                    LEVEL lev\n" +
            "             FROM product_group\n" +
            "             WHERE     LEVEL <= 3\n" +
            "               AND (   (LEVEL = 1 AND (:a1 IS NULL OR id = :a1))\n" +
            "                 OR (LEVEL = 2 AND (:a2 IS NULL OR id = :a2))\n" +
            "                 OR (LEVEL = 3 AND (:a3 IS NULL OR id = :a3)))\n" +
            "             CONNECT BY PRIOR id = hi_id\n" +
            "             START WITH hi_id IS NULL),\n" +
            "    t1 AS (\n" +
            "        SELECT TO_CHAR (id) id,\n" +
            "               LEV         lev,\n" +
            "               TO_CHAR (hi_id) hi_id,\n" +
            "               name,\n" +
            "               0 typ,\n" +
            "               NULL PRODUCT_HIER_ID,\n" +
            "               ID REAL_ID,\n" +
            "               HI_ID REAL_HI_ID\n" +
            "        FROM t3\n" +
            "        WHERE (hi_id IS NULL OR PRIOR id = hi_id)\n" +
            "        CONNECT BY PRIOR id = hi_id START WITH hi_id IS NULL\n" +
            "        UNION ALL\n" +
            "        SELECT TO_CHAR (s.id) || LPAD (v.VALUE, 10, ' ')\n" +
            "                   id,\n" +
            "               s.LEV + 3\n" +
            "                   lev,\n" +
            "               CASE\n" +
            "                   WHEN lev = 1\n" +
            "                       THEN (SELECT TO_CHAR (product_group)\n" +
            "                             FROM PRODUCT_HIERARCHY\n" +
            "                             WHERE id = s.PRODUCT_HIER_ID)\n" +
            "                   ELSE\n" +
            "                       (SELECT DISTINCT TO_CHAR (s1.id) || LPAD (v1.VALUE, 10, ' ')\n" +
            "                        FROM PRODUCT_HIER_STRUCT s1, PRODUCT_HIER_VALUE v1\n" +
            "                        WHERE     s1.id = v1.idstruct\n" +
            "                          AND s1.CRIT IS NOT NULL\n" +
            "                          AND s1.PRODUCT_HIER_ID = s.PRODUCT_HIER_ID\n" +
            "                          AND s1.lev = s.lev - 1)\n" +
            "                   END\n" +
            "                   hi_id,\n" +
            "               PKG_HIER.EXEC_SQL (\n" +
            "                       sSQL =>\n" +
            "                               'select '||c.FIELD_TEXT||' from '||c.TABLE_NAME||' where to_char('||c.FIELD_NAME||') = '''\n" +
            "                               ||v.VALUE||'''')\n" +
            "                   name,\n" +
            "               1 typ,\n" +
            "               s.PRODUCT_HIER_ID,\n" +
            "               s.ID REAL_ID,\n" +
            "               NULL REAL_HI_ID\n" +
            "        FROM PRODUCT_HIER_STRUCT  s,\n" +
            "             PRODUCT_HIER_VALUE   v,\n" +
            "             PRODUCT_HIER_CRIT    c\n" +
            "        WHERE s.id = v.idstruct AND s.CRIT IS NOT NULL AND s.CRIT = c.ID\n" +
            "        union\n" +
            "        select TO_CHAR (ps.id) || LPAD (pv.VALUE, 10, ' ') || LPAD(TO_CHAR (p.id),10,' ') id,\n" +
            "               ph.LEVEL_QUANT + 4 lev,\n" +
            "               TO_CHAR (ps.id) || LPAD (pv.VALUE, 10, ' ') hi_id,\n" +
            "               PRODUCT_CODE name,\n" +
            "               2 typ,\n" +
            "               p.PRODUCT_HIERARCHY_ID PRODUCT_HIER_ID,\n" +
            "               p.ID,\n" +
            "               NULL\n" +
            "        from product p, PRODUCT_HIERARCHY ph, PRODUCT_HIER_STRUCT ps, PRODUCT_HIER_VALUE pv\n" +
            "        where p.PRODUCT_HIERARCHY_ID = ph.ID\n" +
            "          and p.ID = pv.IDPRODUCT\n" +
            "          and ph.ID = ps.PRODUCT_HIER_ID and ps.CRIT is not NULL and ps.LEV = ph.LEVEL_QUANT\n" +
            "          and pv.IDSTRUCT = ps.ID\n" +
            "    )\n" +
            "SELECT id,\n" +
            "       HI_ID,\n" +
            "       LEV,\n" +
            "       CASE WHEN lev <= 4 THEN NULL ELSE NAME || ' ' END || NAME name,\n" +
            "       TYP, -- 0 - группа продуктов, 1 - дерево критериев, 2 - продукты\n" +
            "       REAL_HI_ID,\n" +
            "       REAL_ID\n" +
            "FROM t1\n" +
            "CONNECT BY PRIOR id = hi_id\n" +
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
                .child(new ArrayList<>())
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
