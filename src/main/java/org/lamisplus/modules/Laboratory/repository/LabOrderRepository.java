package org.lamisplus.modules.Laboratory.repository;

import org.lamisplus.modules.Laboratory.domain.entity.LabOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LabOrderRepository extends JpaRepository<LabOrder, Integer> {
    List<LabOrder> findAllByPatientId(int patient_id);
    List<LabOrder> findAllByVisitId(int visit_id);
    @Query(value = "select * from laboratory_order where id not in (select b.lab_order_id from laboratory_sample a \n" +
            "inner join public.laboratory_test b on a.test_id = b.id)", nativeQuery = true)
    List<LabOrder> findAllPendingSampleCollection();
    @Query(value = "select * from laboratory_order where id in (select b.lab_order_id from laboratory_sample a \n" +
            "inner join public.laboratory_test b on a.test_id = b.id where a.date_sample_verified is null)", nativeQuery = true)
    List<LabOrder> findAllPendingSampleVerification();
    @Query(value = "select * from laboratory_order where id not in (select x.lab_order_id from laboratory_test x \n" +
            "inner join laboratory_result y on x.id=y.test_id) \n" +
            "and id in (select b.lab_order_id from laboratory_sample a \n" +
            "inner join laboratory_test b on a.test_id = b.id where a.date_sample_verified is not null)", nativeQuery = true)
    List<LabOrder> findAllPendingResults();
}
