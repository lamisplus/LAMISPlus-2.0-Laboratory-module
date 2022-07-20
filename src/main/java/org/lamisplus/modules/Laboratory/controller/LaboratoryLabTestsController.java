package org.lamisplus.modules.Laboratory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.LabTestGroupDTO;
import org.lamisplus.modules.Laboratory.service.LabTestGroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/laboratory")
public class LaboratoryLabTestsController {
    private final LabTestGroupService labTestGroupService;

    @GetMapping("/labtestgroups")
    public List<LabTestGroupDTO> GetAllLabTestGroups(){
        return labTestGroupService.GetAllLabTestGroups();
    }
}
