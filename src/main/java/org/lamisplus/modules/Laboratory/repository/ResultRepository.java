package org.lamisplus.modules.Laboratory.repository;

import org.lamisplus.modules.Laboratory.domain.entity.LabOrder;
import org.lamisplus.modules.Laboratory.domain.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository  extends JpaRepository<Result, Integer> {
}
