package org.lamisplus.modules.Laboratory.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoricalResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "order_id")
    private int OrderId;
    @Column(name = "patient_id")
    private int PatientId;
    @Column(name = "lab_test_name")
    private String LabTestName;
    @Column(name = "group_name")
    private String GroupName;
    @Column(name = "order_date")
    private LocalDate OrderDate;
    @Column(name = "order_time")
    private LocalTime OrderTime;
    @Column(name = "date_sample_collected")
    private LocalDate DateSampleCollected;
    @Column(name = "time_sample_collected")
    private LocalTime TimeSampleCollected;
    @Column(name = "date_sample_verified")
    private LocalDate DateSampleVerified;
    @Column(name = "time_sample_verified")
    private LocalTime TimeSampleVerified;
    @Column(name = "date_result_reported")
    private LocalDate DateResultReported;
    @Column(name = "time_result_reported")
    private LocalTime TimeResultReported;
    @Column(name = "result_reported")
    private String ResultReported;
}
