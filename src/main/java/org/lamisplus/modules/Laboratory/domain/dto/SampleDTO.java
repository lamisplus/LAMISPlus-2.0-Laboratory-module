package org.lamisplus.modules.Laboratory.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SampleDTO {
    private int id;
    private String SampleNumber;
    private int sampleTypeId;
    private int sampleCollectionMode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateSampleCollected;
    private String commentSampleCollected;
    private String sampleCollectedBy;
    public int testId;
}
