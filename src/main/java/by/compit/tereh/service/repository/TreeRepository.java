package by.compit.tereh.service.repository;

import by.compit.tereh.service.model.product_hierarchy.ProductGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TreeRepository {
    @Autowired
    @Qualifier("jdbcTemplateHierarchy")
    private JdbcTemplate jdbcTemplate;

    public List<ProductGroup> getTree(){
        return new ArrayList<>();
    }
}
