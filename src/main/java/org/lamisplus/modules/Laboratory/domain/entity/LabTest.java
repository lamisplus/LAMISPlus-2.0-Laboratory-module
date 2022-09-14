package org.lamisplus.modules.Laboratory.domain.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "laboratory_labtest")
public class LabTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "lab_test_name")
    private String labTestName;
    @Column(name = "unit")
    private String unit;
    @Column(name = "uuid")
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "labtestgroup_id")
    private LabTestGroup labTestGroup;
}
