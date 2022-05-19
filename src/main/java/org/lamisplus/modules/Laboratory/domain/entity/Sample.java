package org.lamisplus.modules.Laboratory.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "laboratory_sample")
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "sample_type_id")
    private int sampleTypeId;
    @Column(name = "sample_order_date")
    private LocalDate sampleOrderDate;
    @Column(name = "sample_order_time")
    private LocalTime sampleOrderTime;
    @Column(name = "sample_collection_mode")
    private int sampleCollectionMode;
    @Column(name = "date_sample_collected")
    private LocalDate dateSampleCollected;
    @Column(name = "time_sample_collected")
    private LocalTime timeSampleCollected;
    @Column(name = "comment_sample_collected")
    private String commentSampleCollected;
    @Column(name = "sample_collected_by")
    private String sampleCollectedBy;
    @Column(name = "date_sample_verified")
    private LocalDate dateSampleVerified;
    @Column(name = "time_sample_verified")
    private LocalTime timeSampleVerified;
    @Column(name = "comment_sample_verified")
    private String commentSampleVerified;
    @Column(name = "sample_verified_by")
    private String sampleVerifiedBy;
    @Column(name = "test_id")
    private int testId;
}
