package org.lamisplus.modules.Laboratory.repository;

import org.lamisplus.modules.Laboratory.domain.entity.PendingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PendingOrderRepository extends JpaRepository<PendingOrder, Integer> {
    @Query(value = "select * from\n" +
            "(\n" +
            "select a.id as id, a.patient_id, a.id as order_id, a.order_date, sum(1) as test_orders\n" +
            ", sum(case when c.id is not null then 1 else 0 end) as collected_samples\n" +
            ", sum(case when c.date_sample_verified is not null then 1 else 0 end) as verified_samples\n" +
            ", sum(case when d.id is not null then 1 else 0 end) as reported_results\n" +
            "from laboratory_order a\n" +
            "inner join laboratory_test b on a.id=b.lab_order_id\n" +
            "left join laboratory_sample c on b.id=c.test_id\n" +
            "left join laboratory_result d on b.id=d.test_id\n" +
            "group by a.patient_id, a.id\n" +
            ") a where a.collected_samples < test_orders", nativeQuery = true)
    List<PendingOrder> findAllPendingSampleCollection();

    @Query(value = "select * from\n" +
            "(\n" +
            "select a.id as id, a.patient_id, a.id as order_id, a.order_date, sum(1) as test_orders\n" +
            ", sum(case when c.id is not null then 1 else 0 end) as collected_samples\n" +
            ", sum(case when c.date_sample_verified is not null then 1 else 0 end) as verified_samples\n" +
            ", sum(case when d.id is not null then 1 else 0 end) as reported_results\n" +
            "from laboratory_order a\n" +
            "inner join laboratory_test b on a.id=b.lab_order_id\n" +
            "left join laboratory_sample c on b.id=c.test_id\n" +
            "left join laboratory_result d on b.id=d.test_id\n" +
            "group by a.patient_id, a.id\n" +
            ") a where a.verified_samples < collected_samples", nativeQuery = true)
    List<PendingOrder> findAllPendingSampleVerification();

    @Query(value = "select * from\n" +
            "(\n" +
            "select a.id as id, a.patient_id, a.id as order_id, a.order_date, sum(1) as test_orders\n" +
            ", sum(case when c.id is not null then 1 else 0 end) as collected_samples\n" +
            ", sum(case when c.date_sample_verified is not null then 1 else 0 end) as verified_samples\n" +
            ", sum(case when d.id is not null then 1 else 0 end) as reported_results\n" +
            "from laboratory_order a\n" +
            "inner join laboratory_test b on a.id=b.lab_order_id\n" +
            "left join laboratory_sample c on b.id=c.test_id\n" +
            "left join laboratory_result d on b.id=d.test_id\n" +
            "group by a.patient_id, a.id\n" +
            ") a where a.verified_samples >= 1", nativeQuery = true)
    List<PendingOrder> findAllPendingResults();
}
