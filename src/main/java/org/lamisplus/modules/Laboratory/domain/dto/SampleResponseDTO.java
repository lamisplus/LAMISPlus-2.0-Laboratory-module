package org.lamisplus.modules.Laboratory.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SampleResponseDTO {
    private int id;
    private String SampleNumber;
    private int sampleTypeId;
    private String sampleTypeName;
    private int sampleCollectionMode;
    private String labNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateSampleCollected;
    private String commentSampleCollected;
    private String sampleCollectedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime dateSampleVerified;
    public String commentSampleVerified;
    private String sampleVerifiedBy;
    private String sampleAccepted;
    public int testId;
}
