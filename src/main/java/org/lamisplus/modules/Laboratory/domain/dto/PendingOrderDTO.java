package org.lamisplus.modules.Laboratory.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PendingOrderDTO {
    private int patientId;
    private String patientFirstName;
    private String patientLastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate PatientDob;
    private String patientHospitalNumber;
    private String patientAddress;
    private String patientPhoneNumber;
    private String patientGender;

    private int orderId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;
    private int testOrders;
    private int collectedSamples;
    private int verifiedSamples;
    private int reportedResults;
}
