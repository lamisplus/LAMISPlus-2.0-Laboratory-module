package org.lamisplus.modules.Laboratory.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TestResponseDTO {
    private int id;
    private int labTestId;
    private String description;
    private String labTestName;
    private String labNumber;
    private String unitMeasurement;
    private int labTestGroupId;
    private String labTestGroupName;
    private int orderPriority;
    private String orderPriorityName;
    private int labTestOrderStatus;
    private String labTestOrderStatusName;
    private int viralLoadIndication;
    private String viralLoadIndicationName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    private List<SampleResponseDTO> samples;
    private List<ResultDTO> results;
}
