package by.compit.tereh.service.service;

import by.compit.tereh.service.dto.LevelUpdateData;
import by.compit.tereh.service.model.product.Product;
import by.compit.tereh.service.dto.ProductDTO;
import by.compit.tereh.service.model.product_hierarchy.Field;
import by.compit.tereh.service.model.product_hierarchy.ProductGroup;
import by.compit.tereh.service.model.product_hierarchy.ProductHierValue;
import by.compit.tereh.service.model.product_hierarchy.ProductHierarchy;
import by.compit.tereh.service.repository.ProductHierValueRepository;
import by.compit.tereh.service.repository.ProductRepository;
import by.compit.tereh.service.repository.product.ProductJDBCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductJDBCRepository productJDBCRepository;

    @Autowired
    private ProductHierValueRepository productHierValueRepository;


    @Transactional
    public List<Product> getProductsByHierarchyId(Long productHierarchyId){
        return productRepository.findAllByProductHierarchyId(productHierarchyId);
    }

    @Transactional
    public List<ProductDTO> getProductAsMapByHierarchyId(Long productHierarchyId){
        List<ProductDTO> productDTOList = new ArrayList<>();
        List<Product> productList = getProductsByHierarchyId(productHierarchyId);
        productList.forEach(product -> product.setProductHierValues(productHierValueRepository.findAllByProductId(product.getId())));
        if(!productList.isEmpty()) {
            Long productGroup = productList.get(0).getProductGroup().getId();

        /*switch (productGroup.intValue()){
            case 1:
                for (Product product:productList) {
                    productDTOList.add(ProductDTO.builder()
                            .product(product)
                            .card(cardRepository.getById(product.getProductId()))
                            .build());
                }
                for (Product product:productList) {
                    productDTOList.add(ProductDTO.builder()
                            .product(product)
                            .realProduct(cardJDBCRepository.getCardAsMapByHierarchyId(product.getProductId()))
                            .build());
                }
                break;
            case 2:
                for (Product product:productList) {
                    productDTOList.add(ProductDTO.builder()
                            .product(product)
                            .realProduct(cardJDBCRepository.getCardAsMapByHierarchyId(product.getProductId()))
                            .build());
                }
                break;
            case 3:
                for (Product product:productList) {
                    productDTOList.add(ProductDTO.builder()
                            .product(product)
                            .realProduct(cardJDBCRepository.getCardAsMapByHierarchyId(product.getProductId()))
                            .build());
                }
                break;
        }*/

            for (Product product : productList) {
                productDTOList.add(ProductDTO.builder()
                        .product(product)
                        .realProduct(productJDBCRepository.getProductAsMapByProductIdAndGroupId(product.getProductId(), productGroup))
                        .build());
            }

            return productDTOList;
        }
        else return null;
    }

    @Transactional
    public int updateHierarchyLevel(LevelUpdateData data){
        return productJDBCRepository.updateHierarchyLevel(data.getFields(),data.getTableName(),data.getProductsId());
    }

    @Transactional
    public Product createProduct(LevelUpdateData data){
        Long realProductId = productJDBCRepository.createRealProduct(data.getFields(),data.getTableName());

        Product product = Product.builder()
                .productId(realProductId)
                .productGroup(ProductGroup.builder().id(Long.parseLong(data.getProductGroupId())).build())
                .productHierarchy(ProductHierarchy.builder().id(Long.parseLong(data.getHierarchyId())).build())
                .productCode(data.getFieldNameProductCode())
                .productHierValues(data.getProductHierValues())
                .build();

        productRepository.save(product);

        product.getProductHierValues().forEach(element->element.setProduct(product));
        productHierValueRepository.saveAll(product.getProductHierValues());

        return product;
    }
}
