package by.compit.tereh.service.service;

import by.compit.tereh.service.dto.LevelUpdateData;
import by.compit.tereh.service.model.product.Product;
import by.compit.tereh.service.dto.ProductDTO;
import by.compit.tereh.service.repository.ProductRepository;
import by.compit.tereh.service.repository.product.ProductJDBCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductJDBCRepository productJDBCRepository;

    public List<Product> getProductsByHierarchyId(Long productHierarchyId){
        return productRepository.findAllByProductHierarchyId(productHierarchyId);
    }

    public List<ProductDTO> getProductAsMapByHierarchyId(Long productHierarchyId){
        List<ProductDTO> productDTOList = new ArrayList<>();
        List<Product> productList = getProductsByHierarchyId(productHierarchyId);
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

    public int updateHierarchyLevel(LevelUpdateData data){
        return productJDBCRepository.updateHierarchyLevel(data);
    }
}
