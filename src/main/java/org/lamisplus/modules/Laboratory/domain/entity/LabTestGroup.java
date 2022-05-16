package org.lamisplus.modules.Laboratory.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "laboratory_labtestgroup")
public class LabTestGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "group_name")
    private String GroupName;

    @OneToMany(mappedBy = "labTestGroup")
    private List<LabTest> labTests;
}
