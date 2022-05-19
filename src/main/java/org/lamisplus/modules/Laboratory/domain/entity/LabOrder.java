package org.lamisplus.modules.Laboratory.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

    @JoinColumn(name = "labOrderId")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Test> tests;
}
