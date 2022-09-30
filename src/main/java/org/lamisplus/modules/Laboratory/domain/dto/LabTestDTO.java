package org.lamisplus.modules.Laboratory.domain.dto;

import lombok.Data;
import org.lamisplus.modules.Laboratory.domain.entity.SampleType;

import java.util.List;

@Data
public class LabTestDTO {
    private int id;
    private String labTestName;
    private String unit;
    private List<SampleType> sampleType;
}
