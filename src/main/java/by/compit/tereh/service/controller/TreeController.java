package by.compit.tereh.service.controller;

import by.compit.tereh.service.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/tree")
public class TreeController {

    @Autowired
    private TreeRepository treeRepository;

    @GetMapping
    public String getTree(Model model){
        model.addAttribute("tree",treeRepository.getTree(null,null,null));
        return "tree";
    }
}
