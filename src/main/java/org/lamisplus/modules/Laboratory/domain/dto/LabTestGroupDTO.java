package org.lamisplus.modules.Laboratory.domain.dto;

import lombok.Data;
import org.lamisplus.modules.Laboratory.domain.entity.LabTest;

import java.util.List;

@Data
public class LabTestGroupDTO {
    private int id;
    private String GroupName;
    private List<LabTestDTO> labTests;
}
