package by.compit.tereh.service.controller;

import by.compit.tereh.service.model.product.Product;
import by.compit.tereh.service.model.tree.ProductElement;
import by.compit.tereh.service.service.ProductGroupService;
import by.compit.tereh.service.service.ProductService;
import by.compit.tereh.service.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/product_placing")
public class ProductPlacingController {

    @Autowired
    private ProductGroupService productGroupService;

    @Autowired
    private TreeService treeService;

    @Autowired
    private ProductService productService;

    private static final String GET_PRODUCT_PLACING_VIEW = "product_hierarchy/product_placing";

    @GetMapping
    public String getPage(Model model){
        model.addAttribute("productGroups",productGroupService.getFifthLevelGroups());
        model.addAttribute("tree", treeService.getFifthTree(null,null,null,null,null));
        return GET_PRODUCT_PLACING_VIEW;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/filter")
    public String getTree(@RequestParam(required = false) Long firstLevelProductGroup ,
                          @RequestParam(required = false) Long secondLevelProductGroup,
                          @RequestParam(required = false) Long thirdLevelProductGroup,
                          @RequestParam(required = false) Long fourthLevelProductGroup,
                          @RequestParam(required = false) Long fifthLevelProductGroup,
                          Model model) {
        model.addAttribute("tree", treeService.getFifthTree(firstLevelProductGroup, secondLevelProductGroup, thirdLevelProductGroup, fourthLevelProductGroup, fifthLevelProductGroup));
        model.addAttribute("productGroups",productGroupService.getFifthLevelGroups());
        return GET_PRODUCT_PLACING_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/update")
    @ResponseBody
    public Product updateProduct(@RequestBody(required = false) ProductElement product) {
        return productService.updateBnIdAndDbId(product.getId(), product.getBnId(), product.getDbId());
    }
}
