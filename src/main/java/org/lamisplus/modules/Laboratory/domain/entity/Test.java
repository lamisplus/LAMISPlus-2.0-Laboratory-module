package org.lamisplus.modules.Laboratory.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Test {
    @Id
    @GeneratedValue

    private int id;
    private int patient_id;
    private int lab_test_id;
    private String description;
    private String lab_number;
    private int lab_test_group_id;
    private int order_priority;
    private String unit_measurement;
    private int lab_test_order_status;
    private int viral_load_indication;
    private List<Sample> samples;
    private List<Result> reported_results;
}
