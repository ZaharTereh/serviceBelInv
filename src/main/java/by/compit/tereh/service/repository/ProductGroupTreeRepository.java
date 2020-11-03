package by.compit.tereh.service.repository;

import by.compit.tereh.service.dto.ProductGroupTreeDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class ProductGroupTreeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private static final String GET_PRODUCT_GROUP_QUERY = "SELECT id, hi_id, name, LEVEL lev, CONNECT_BY_ISLEAF AS leaf, table_name " +
            "FROM product_group " +
            "WHERE level <= 3 " +
            "CONNECT BY PRIOR id = hi_id START WITH hi_id IS NULL";


    @SuppressWarnings("unchecked")
    public List<ProductGroupTreeDTO> getTreeProductGroup() {
        List<Tuple> tupleList = entityManager.createNativeQuery(GET_PRODUCT_GROUP_QUERY, Tuple.class).getResultList();
        return tupleList.stream().map(tuple ->
                ProductGroupTreeDTO.builder()
                        .id((tuple.get("ID",BigDecimal.class)).longValue())
                        .hiId(convertToLong(tuple.get("HI_ID",BigDecimal.class)))
                        .leaf((tuple.get("LEAF",BigDecimal.class)).byteValue())
                        .lev((tuple.get("LEV",BigDecimal.class)).byteValue())
                        .name(tuple.get("NAME",String.class))
                        .tableName(Optional.ofNullable(tuple.get("TABLE_NAME",String.class)).orElse(null))
                        .build()
        ).collect(Collectors.toList());
    }

    private Long convertToLong(BigDecimal bigDecimal){
        if(bigDecimal == null){
            return null;
        }else {
            return bigDecimal.longValue();
        }
    }
}
