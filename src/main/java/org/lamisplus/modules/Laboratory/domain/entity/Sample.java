package org.lamisplus.modules.Laboratory.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "int-array", typeClass = IntArrayType.class),
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
        @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class),
        @TypeDef(name = "json-node", typeClass = JsonNodeStringType.class),
})
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

    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "sample_type", columnDefinition = "jsonb")
    private Object sampleType;

    @Column(name = "sample_order_date")
    private LocalDate sampleOrderDate;
    @Column(name = "sample_order_time")
    private LocalTime sampleOrderTime;

    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "sample_collection_mode", columnDefinition = "jsonb")
    private Object sampleCollectionMode;

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
    @Column(name = "sample_confirm")
    private Integer sampleConfirm;
    @Column(name = "comment_sample_verified")
    private String commentSampleVerified;
    @Column(name = "sample_verified_by")
    private String sampleVerifiedBy;

    @Column(name = "test_id")
    private int testId;
}
