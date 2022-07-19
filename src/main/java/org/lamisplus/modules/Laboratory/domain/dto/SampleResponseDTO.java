package org.lamisplus.modules.Laboratory.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SampleResponseDTO {
    private int id;
    private int sampleTypeId;
    private String sampleTypeName;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    //private LocalDate sampleOrderDate;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    //private LocalTime sampleOrderTime;
    private int sampleCollectionMode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateSampleCollected;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime timeSampleCollected;
    private String commentSampleCollected;
    private String sampleCollectedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate dateSampleVerified;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    public LocalTime timeSampleVerified;
    public String commentSampleVerified;
    private String sampleVerifiedBy;
    private String sampleAccepted;
    public int testId;
}
