package org.lamisplus.modules.Laboratory.repository;

import org.lamisplus.modules.Laboratory.domain.entity.LabOrder;
import org.lamisplus.modules.Laboratory.domain.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Integer> {
    @Query(value = "select * from public.laboratory_test where id not in (select id from laboratory_sample)", nativeQuery = true)
    List<Test> findAllPendingSampleCollection();
    @Query(value = "select * from public.laboratory_test where id in (select id from laboratory_sample where " +
            "date_sample_verified is null)", nativeQuery = true)
    List<Test> findAllPendingSampleVerification();
    @Query(value = "select * from public.laboratory_test where id not in (select id from laboratory_result)", nativeQuery = true)
    List<Test> findAllPendingResults();
}
