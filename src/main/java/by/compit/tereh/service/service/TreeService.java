package by.compit.tereh.service.service;

import by.compit.tereh.service.model.product.Product;
import by.compit.tereh.service.model.tree.ProductElement;
import by.compit.tereh.service.model.tree.TreeElement;
import by.compit.tereh.service.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TreeService {
    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private ProductService productService;

    @Transactional
    public TreeElement getThirdLevelTree(Long pg1, Long pg2, Long pg3){
        List<TreeElement> elementList = treeRepository.getTree(pg1,pg2,pg3);

        return shapeTree(elementList);
    }

    @Transactional
    public TreeElement getFifthTree(Long pg1, Long pg2, Long pg3, Long pg4, Long pg5){
        List<TreeElement> productGroupList = treeRepository.getFifthLevelTree(pg1, pg2, pg3);


            productGroupList.forEach(group -> {
                List<Product> productList;
                if(group.getLev() == 3){

                    if(pg4 == null && pg5 == null){
                        productList = productService.findAllByProductGroup(group.getRealId());
                    }else if (pg4 != null && pg5 == null){
                        productList = productService.findAllByProductGroupIdAndBnId(group.getRealId(),pg4);
                    }else {
                        productList = productService.findAllByProductGroupIdAndBnIdAndDbId(group.getRealId(),pg4,pg5);
                    }

                    group.setChild(productList.stream().map(product ->
                                TreeElement.builder()
                                    .id(product.getId().toString())
                                    .hiId(group.getId())
                                    .lev((byte)4)
                                    .name(productService.getNameOfProductById(product.getProductGroup().getTableName(),product.getProductGroup().getProductName(),product.getProductId()))
                                    .typ((byte) 2)
                                    .productElement(ProductElement.builder()
                                                            .code(product.getProductCode())
                                                            .bnId(product.getBnId())
                                                            .dbId(product.getDbId())
                                                        .build()
                                    )
                            .build()
                            ).collect(Collectors.toList())
                    );
                }
            });

        return shapeTree(productGroupList);
    }

    private TreeElement shapeTree(List<TreeElement> elementList){
        TreeElement tree = TreeElement.builder().child(new ArrayList<>()).build();

        int lastLevel = elementList.stream().mapToInt(TreeElement::getLev).max().orElseThrow(NoSuchElementException::new);
        for(int i = lastLevel;i > 1; i--){
            int temp = i;
            List<TreeElement> leafs = elementList.stream().filter(treeElement -> treeElement.getLev()==temp).collect(Collectors.toList());
            leafs.forEach(leafElement -> {
                elementList.forEach(treeElement -> {
                    if(treeElement.getId().equals(leafElement.getHiId())){
                        treeElement.getChild().add(leafElement);
                    }
                });
            });

        }
        elementList.forEach(treeElement -> {
            if(treeElement.getLev() == 1){
                tree.getChild().add(treeElement);
            }
        });

        return tree;
    }
}
