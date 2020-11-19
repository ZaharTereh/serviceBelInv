package by.compit.tereh.service.service;

import by.compit.tereh.service.model.tree.TreeElement;
import by.compit.tereh.service.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TreeForUpdateService {
    @Autowired
    private TreeRepository treeRepository;

    public TreeElement getTree(Long pg1, Long pg2, Long pg3){
        TreeElement tree = TreeElement.builder().child(new ArrayList<>()).build();
        List<TreeElement> list = treeRepository.getTree(pg1,pg2,pg3);

        int lastLevel = list.stream().mapToInt(TreeElement::getLev).max().orElseThrow(NoSuchElementException::new);

        for(int i = lastLevel;i > 1; i--){
            int temp = i;
            List<TreeElement> leafs = list.stream().filter(treeElement -> treeElement.getLev()==temp).collect(Collectors.toList());
            leafs.forEach(leafElement -> {
                list.forEach(treeElement -> {
                    if(treeElement.getId().equals(leafElement.getHiId())){
                        treeElement.getChild().add(leafElement);
                    }
                });
            });

        }
        list.forEach(treeElement -> {
            if(treeElement.getLev() == 1){
                tree.getChild().add(treeElement);
            }
        });
        return tree;
    }
}
