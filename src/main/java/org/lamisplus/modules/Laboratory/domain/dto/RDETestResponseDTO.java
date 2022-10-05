package org.lamisplus.modules.Laboratory.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RDETestResponseDTO {
    private int id;
    private int orderId;
    private int patientId;
    private int visitId;
    private int labTestGroupId;
    private String labTestGroupName;
    private int labTestId;
    private String labTestName;
    private String labNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate sampleCollectionDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateAssayed;
    private String result;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateResultReceived;
    private String comments;
    private int viralLoadIndication;
    private String viralLoadIndicationName;
}
