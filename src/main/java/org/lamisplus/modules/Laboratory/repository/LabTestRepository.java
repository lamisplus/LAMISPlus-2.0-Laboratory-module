package org.lamisplus.modules.Laboratory.repository;

import org.lamisplus.modules.Laboratory.domain.entity.LabOrder;
import org.lamisplus.modules.Laboratory.domain.entity.LabTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabTestRepository  extends JpaRepository<LabTest, Integer> {
}
