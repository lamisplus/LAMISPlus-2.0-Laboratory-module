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
public class PendingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "order_id")
    private int orderId;
    @Column(name = "order_date")
    private LocalDate orderDate;
    @Column(name = "order_time")
    private LocalTime orderTime;
    @Column(name = "test_orders")
    private int testOrders;
    @Column(name = "collected_samples")
    private int collectedSamples;
    @Column(name = "verified_samples")
    private int verifiedSamples;
    @Column(name = "reported_results")
    private int reportedResults;
    @Column(name = "patient_id")
    private int patientId;
}
