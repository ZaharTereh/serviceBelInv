package by.compit.tereh.service.service;

import by.compit.tereh.service.dto.FullProductHierarchyDTO;
import by.compit.tereh.service.dto.ProductGroupDTO;
import by.compit.tereh.service.dto.ProductHierarchyDTO;
import by.compit.tereh.service.model.product_hierarchy.*;
import by.compit.tereh.service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ProductHierarchyService {
    @Autowired
    private ProductGroupRepository productGroupRepository;

    @Autowired
    private ProductHierarchyRepository productHierarchyRepository;

    @Autowired
    private ProductHierarchyStructRepository productHierarchyStructRepository;

    @Autowired
    private LevelHierarchyRepository levelHierarchyRepository;

    @Autowired
    private ProductHierCritRepository productHierCritRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private HierarchyRepository hierarchyRepository;




    @Transactional
    public List<FullProductHierarchyDTO> getProductHierarchyByProductGroups(Long pg1, Long pg2, Long pg3){
        List<FullProductHierarchyDTO> fullProductHierarchyDTOList = FullProductHierarchyDTO.toDTOList(hierarchyRepository.getProductHierarchyByProductGroups(pg1, pg2, pg3));

        for (FullProductHierarchyDTO fullProductHierarchyDTO:fullProductHierarchyDTOList) {
            fullProductHierarchyDTO.setSecondProductGroup(ProductGroupDTO.toDTO(productGroupRepository.getOne(fullProductHierarchyDTO.getProductHierarchyDTO().getProductGroupDTO().getHiId())));
            fullProductHierarchyDTO.setFirstProductGroup(ProductGroupDTO.toDTO(productGroupRepository.getOne(fullProductHierarchyDTO.getSecondProductGroup().getHiId())));
        }
        return fullProductHierarchyDTOList;
    }

    @Transactional
    public FullProductHierarchyDTO findById(Long hierarchyId){
        FullProductHierarchyDTO fullProductHierarchyDTO = FullProductHierarchyDTO.toDTO(productHierarchyRepository.findById(hierarchyId).get());
        fullProductHierarchyDTO.setSecondProductGroup(ProductGroupDTO.toDTO(productGroupRepository.getOne(fullProductHierarchyDTO.getProductHierarchyDTO().getProductGroupDTO().getHiId())));
        fullProductHierarchyDTO.setFirstProductGroup(ProductGroupDTO.toDTO(productGroupRepository.getOne(fullProductHierarchyDTO.getSecondProductGroup().getHiId())));

        List<Field> fieldList = levelHierarchyRepository.getFieldList(fullProductHierarchyDTO.getProductHierarchyDTO().getProductGroupDTO().getTableName());
        fullProductHierarchyDTO.setLevels(
                getLevelList(fullProductHierarchyDTO.getProductHierarchyDTO().getProductHierarchyStructs(),fieldList)
        );
        fullProductHierarchyDTO.setFreeFields(fieldList);
        fullProductHierarchyDTO.setProductList(productService.getProductAsMapByHierarchyId(fullProductHierarchyDTO.getProductHierarchyDTO().getId()));
        return fullProductHierarchyDTO;
    }

    private List<Level> getLevelList(List<ProductHierarchyStruct> structList,List<Field> fieldList){
        List<Level> levels = new ArrayList<>();

        for (ProductHierarchyStruct struct: structList) {
            Level newLevel;
            if (struct.getCrit() != null) {
                newLevel = new Level();
                ProductHierCrit crit = productHierCritRepository.findById(struct.getCrit()).get();
                newLevel.setCriterionList(levelHierarchyRepository.getCriterionList(crit));
                newLevel.setLevel((struct.getLevel()));
                levels.add(newLevel);
            }

        }
        for (ProductHierarchyStruct struct: structList) {
            if (struct.getCrit() == null) {
                Level currentLevel = levels.stream().filter(level -> level.getLevel()==struct.getLevel()).findFirst().get();
                Iterator<Field> iterator = fieldList.listIterator();
                Field field;
                while (iterator.hasNext()){
                    field = iterator.next();
                    if(field.getFieldName().equals(struct.getFieldName())){
                        currentLevel.getFieldList().add(field);
                        iterator.remove();
                    }
                }
            }
        }

        levels.stream().sorted();
        return levels;
    }

    @Transactional
    public List<ProductHierarchy> findAllByProductGroupId(Long productGroupId){
        return productHierarchyRepository.findAllByProductGroup_Id(productGroupId);
    }

    @Transactional
    public boolean create(ProductHierarchy productHierarchy){
        if(productHierarchy.getId()!=null){
            productHierarchyStructRepository.deleteAllByProductHierarchy(productHierarchy);
        }

        for (ProductHierarchyStruct struct:productHierarchy.getProductHierarchyStructs()) {
            struct.setProductHierarchy(productHierarchy);
        }
        productHierarchyRepository.save(productHierarchy);
        return true;
    }

    @Transactional
    public List<ProductHierarchy> getProductHierarchyByName(String name){
        return productHierarchyRepository.findProductHierarchiesByName(name);
    }

    @Transactional
    public List<ProductHierarchyDTO> findAllNamesAndId(){
        return productHierarchyRepository.findAll().stream().map(element->ProductHierarchyDTO.builder()
                    .id(element.getId())
                    .name(element.getName())
                    .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProductHierarchyDTO> findAll(){
        return productHierarchyRepository.findAll().stream().map(ProductHierarchyDTO::toDTO).collect(Collectors.toList());
    }
}
