package org.lamisplus.modules.Laboratory.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "laboratory_order")
public class LabOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "visit_id")
    private int visitId;
    @Column(name = "patient_id")
    private int patientId;
    @Column(name = "userid")
    private String userId;
    @Column(name = "order_date")
    private LocalDate orderDate;
    @Column(name = "order_time")
    private LocalTime orderTime;
    @Column(name = "patient_uuid")
    private String patientUuid;
    @Column(name = "facility_id")
    private Long facilityId;
    @JoinColumn(name = "lab_order_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Test> tests;
}
