package org.lamisplus.modules.Laboratory.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class TestResponseDTO {
    private int id;
    private int labTestId;
    private String labTestName;
    private String unitMeasurement;
    private int labTestGroupId;
    private String labTestGroupName;
    private int orderPriority;
    private String orderPriorityName;
    private int labTestOrderStatus;
    private String labTestOrderStatusName;
    private int viralLoadIndication;
    private String viralLoadIndicationName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime orderTime;

    private List<SampleResponseDTO> samples;
    private List<ResultDTO> results;
}
