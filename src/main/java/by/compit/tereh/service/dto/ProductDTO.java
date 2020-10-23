package by.compit.tereh.service.dto;

import by.compit.tereh.service.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Product product;
    private Map<String,Object> realProduct;
    /*private Card card;
    private Credit credit;
    private Debit debit;*/
}
