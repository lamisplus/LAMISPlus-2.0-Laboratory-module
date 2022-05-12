package org.lamisplus.modules.Laboratory.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sample {
    @Id
    @GeneratedValue

    private int id;
    private int sample_type_id;
    private LocalDate sample_order_date;
    private int sample_collection_mode;
    private LocalDate date_sample_collected;
    private String comment_sample_collected;
    private int sample_collected_by;
    private LocalDate date_sample_verified;
    private String comment_sample_verified;
    private int sample_verified_by;
}
