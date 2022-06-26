package org.lamisplus.modules.Laboratory.domain.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.lamisplus.modules.Laboratory.domain.entity.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Data
public class LabOrderDTO {
    private int id;
    private int visitId;
    private int patientId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    //private LocalTime orderTime;
    private List<TestDTO> tests;
}
