package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.Laboratory.domain.entity.LabTestGroup;
import org.lamisplus.modules.Laboratory.repository.LabTestGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabTestGroupService {
    private final LabTestGroupRepository labTestGroupRepository;

    public List<LabTestGroup> GetAllLabTestGroups(){
        return labTestGroupRepository.findAll();
    }

    public  LabTestGroup SaveLabTestGroup(LabTestGroup LabTestGroup){
        return labTestGroupRepository.save(LabTestGroup);
    }
}
