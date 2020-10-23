package by.compit.tereh.service.controller;

import by.compit.tereh.service.dto.LevelHierarchyDTO;
import by.compit.tereh.service.model.product_hierarchy.ProductGroup;
import by.compit.tereh.service.model.product_hierarchy.ProductHierarchy;
import by.compit.tereh.service.service.LevelHierarchyService;
import by.compit.tereh.service.service.ProductGroupService;
import by.compit.tereh.service.service.ProductHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/product_hierarchy")
public class ProductHierarchyController {
    private static final String GET_PRODUCT_HIERARCHY_VIEW = "product_hierarchy/product_hierarchy";

    @Autowired
    private ProductHierarchyService productHierarchyService;
    @Autowired
    private ProductGroupService productGroupService;
    @Autowired
    private LevelHierarchyService levelHierarchyService;



    @RequestMapping(method = RequestMethod.GET,value = "/by_group")
    public String getGetProductHierarchyByGroup(Model model, @RequestParam(required = false) Long firstLevelProductGroup,
                                                             @RequestParam(required = false) Long secondLevelProductGroup,
                                                             @RequestParam(required = false) Long thirdLevelProductGroup)
    {
        model.addAttribute("productGroups",productGroupService.getProductGroupTreeDTOList());
        model.addAttribute("productHierarchyList",productHierarchyService.getProductHierarchyByProductGroups(firstLevelProductGroup,secondLevelProductGroup,thirdLevelProductGroup));

        return GET_PRODUCT_HIERARCHY_VIEW;
    }

    @GetMapping
    public String getProductHierarchy(Model model){
        model.addAttribute("productGroups",productGroupService.getProductGroupTreeDTOList());
        return GET_PRODUCT_HIERARCHY_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/get_hierarchy_level")
    @ResponseBody
    public LevelHierarchyDTO getHierarchyLevel(@RequestBody Map<String,String> parameters){
        return levelHierarchyService.getLevelHierarchy(parameters.get("tableName"),Long.parseLong(parameters.get("productGroupId")));
    }

    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public String create(@RequestBody ProductHierarchy productHierarchy){
        productHierarchy.setUserId(0);
        productHierarchyService.create(productHierarchy);
        return GET_PRODUCT_HIERARCHY_VIEW;
    }



    @RequestMapping(method = RequestMethod.POST,value = "/get_hierarchy_by_name")
    @ResponseBody
    public boolean getProductHierarchyByName(@RequestBody ProductGroup productGroup){
        List<ProductHierarchy> list = productHierarchyService.getProductHierarchyByName(productGroup.getName());
        return list.isEmpty();
    }



}
