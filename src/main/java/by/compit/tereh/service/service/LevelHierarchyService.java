package by.compit.tereh.service.service;

import by.compit.tereh.service.dto.LevelHierarchyDTO;
import by.compit.tereh.service.repository.LevelHierarchyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelHierarchyService {
    @Autowired
    LevelHierarchyRepository levelHierarchyRepository;

    public LevelHierarchyDTO getLevelHierarchy(String tableName,Long productGroupId){
        return levelHierarchyRepository.getLevelHierarchyDTOList(tableName,productGroupId);
    }


}
