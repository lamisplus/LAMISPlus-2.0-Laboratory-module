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
@Table(name = "laboratory_result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "date_assayed")
    private LocalDate dateAssayed;
    @Column(name = "time_assayed")
    private LocalTime timeAssayed;
    @Column(name = "date_result_reported")
    private LocalDate dateResultReported;
    @Column(name = "time_result_reported")
    private LocalTime timeResultReported;
    @Column(name = "result_reported")
    private String resultReported;
    @Column(name = "result_reported_by")
    private String resultReportedBy;
    @Column(name = "test_id")
    private int testId;
    @Column(name = "patient_uuid")
    private String patientUuid;
    @Column(name = "facility_id")
    private Long facilityId;
    @Column(name = "patient_id")
    private int PatientId;
}
