package org.lamisplus.modules.Laboratory.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientLabOrderDTO {
    private String patientFirstName;
    private String patientLastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate PatientDob;
    private String patientHospitalNumber;
    private int patientId;
    private String patientAddress;
    private String patientPhoneNumber;
    private String patientGender;
    private LabOrderResponseDTO labOrder;
}
