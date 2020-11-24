package by.compit.tereh.service.service;

import by.compit.tereh.service.dto.ProductGroupTreeDTO;
import by.compit.tereh.service.model.product_hierarchy.ProductGroup;
import by.compit.tereh.service.repository.ProductGroupRepository;
import by.compit.tereh.service.repository.ProductGroupTreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductGroupService {

    @Autowired
    private ProductGroupTreeRepository productGroupTreeRepository;

    @Autowired
    private ProductGroupRepository productGroupRepository;

    public List<ProductGroupTreeDTO> getProductGroupTreeDTOList(){
        return productGroupTreeRepository.getThirdLevelProductGroupTree();
    }

    public List<ProductGroupTreeDTO> getFifthLevelGroups(){
        return productGroupTreeRepository.getFifthLevelProductGroupTree();
    }

    public List<ProductGroup> findAll(){
        return productGroupRepository.findAll();
    }

    public ProductGroup findById(Long id){
        return productGroupRepository.findById(id).get();
    }

}
