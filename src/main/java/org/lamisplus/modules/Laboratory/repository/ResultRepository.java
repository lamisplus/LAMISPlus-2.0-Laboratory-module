package org.lamisplus.modules.Laboratory.repository;

import org.lamisplus.modules.Laboratory.domain.entity.LabOrder;
import org.lamisplus.modules.Laboratory.domain.entity.Result;
import org.lamisplus.modules.Laboratory.domain.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository  extends JpaRepository<Result, Integer> {
    List<Result> findAllByTestId(int testId);
}
