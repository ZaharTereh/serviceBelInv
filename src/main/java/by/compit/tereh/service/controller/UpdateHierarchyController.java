package by.compit.tereh.service.controller;

import by.compit.tereh.service.dto.LevelUpdateData;
import by.compit.tereh.service.model.product.Product;
import by.compit.tereh.service.model.product_hierarchy.ProductGroup;
import by.compit.tereh.service.model.product_hierarchy.ProductHierarchy;
import by.compit.tereh.service.repository.product.ProductJDBCRepository;
import by.compit.tereh.service.service.ProductGroupService;
import by.compit.tereh.service.service.ProductHierarchyService;
import by.compit.tereh.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hierarchy_update")
public class UpdateHierarchyController {
    private static final String GET_HIERARCHY_UPDATE_VIEW = "product_hierarchy/hierarchy_update";

    @Autowired
    private ProductGroupService productGroupService;

    @Autowired
    private ProductHierarchyService productHierarchyService;

    @Autowired
    private ProductService productService;


    @RequestMapping(method = RequestMethod.GET,value = "/get_hierarchy")
    public String getGetProductHierarchyByGroup(Model model, @RequestParam Long hierarchyId){
        model.addAttribute("productGroups",productGroupService.getProductGroupTreeDTOList());
        model.addAttribute("hierarchies",productHierarchyService.findAllNamesAndId());
        model.addAttribute("fullProductHierarchyDTO",productHierarchyService.findById(hierarchyId));
        return GET_HIERARCHY_UPDATE_VIEW;
    }

    @GetMapping
    public String getProductHierarchy(Model model){
        model.addAttribute("productGroups",productGroupService.getProductGroupTreeDTOList());
        model.addAttribute("hierarchies",productHierarchyService.findAllNamesAndId());
        return GET_HIERARCHY_UPDATE_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/get_hierarchies_by_group")
    @ResponseBody
    public List<ProductHierarchy> getProductHierarchyByGroup(@RequestBody ProductGroup productGroup){
        List<ProductHierarchy> productHierarchyList = productHierarchyService.findAllByProductGroupId(productGroup.getId());
        return productHierarchyList;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/update")
    @ResponseBody
    public Integer updateHierarchyLevel(@RequestBody LevelUpdateData levelUpdateData){
        return productService.updateHierarchyLevel(levelUpdateData);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/create-product")
    @ResponseBody
    public Long createProduct(@RequestBody LevelUpdateData levelUpdateData){
        return productService.createProduct(levelUpdateData).getId();
    }
}
