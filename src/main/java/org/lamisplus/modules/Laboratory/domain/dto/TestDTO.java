package org.lamisplus.modules.Laboratory.domain.dto;

import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
public class TestDTO {
    private int id;
    private int patientId;
    private int labTestId;
    private String description;
    private String labNumber;
    private int labTestGroupId;
    private int orderPriority;
    private String unitMeasurement;
    private int labTestOrderStatus;
    private int viralLoadIndication;
    private List<SampleDTO> samples;
    private List<ResultDTO> results;
}
