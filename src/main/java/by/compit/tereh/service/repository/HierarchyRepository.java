package by.compit.tereh.service.repository;

import by.compit.tereh.service.model.product_hierarchy.ProductHierarchy;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class HierarchyRepository {

    @PersistenceContext
    private EntityManager entityManager;


    private static final String QUERY_GET_HIERARCHY_LIST_BY_PRODUCTS_GROUP = "select * from PRODUCT_HIERARCHY where PRODUCT_GROUP in (" +
            "    with t1 as (select id,hi_id from product_group" +
            "                WHERE level <= 3" +
            "                  and ((level = 1 and (:a1 is NULL or id = :a1))" +
            "                    or (level = 2 and (:a2 is NULL or id = :a2))" +
            "                    or (level = 3 and (:a3 is NULL or id = :a3)))" +
            "                CONNECT BY PRIOR id = hi_id START WITH hi_id IS NULL)" +
            "    SELECT id" +
            "    FROM t1 t2" +
            "    WHERE exists (select null from t1 where t1.id = t2.hi_id)" +
            "    CONNECT BY PRIOR id = hi_id START WITH hi_id IS NULL) ";

    @SuppressWarnings("unchecked")
    public List<ProductHierarchy> getProductHierarchyByProductGroups(Long pg1, Long pg2, Long pg3){
         return entityManager.createNativeQuery(QUERY_GET_HIERARCHY_LIST_BY_PRODUCTS_GROUP,ProductHierarchy.class)
                .setParameter("a1", new TypedParameterValue(StandardBasicTypes.LONG, pg1))
                .setParameter("a2", new TypedParameterValue(StandardBasicTypes.LONG, pg2))
                .setParameter("a3", new TypedParameterValue(StandardBasicTypes.LONG, pg3))
                .getResultList();
    }

}
