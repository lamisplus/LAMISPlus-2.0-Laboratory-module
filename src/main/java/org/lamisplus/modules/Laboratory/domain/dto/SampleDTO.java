package org.lamisplus.modules.Laboratory.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SampleDTO {
    private int id;
    private Object sampleType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate sampleOrderDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime sampleOrderTime;
    private Object sampleCollectionMode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateSampleCollected;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime timeSampleCollected;
    private String commentSampleCollected;
    private int sampleCollectedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateSampleVerified;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime timeSampleVerified;
    private int sampleConfirm;
    private String commentSampleVerified;
    private int testId;
}
