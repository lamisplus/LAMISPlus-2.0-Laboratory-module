package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.Laboratory.domain.entity.LabOrder;
import org.lamisplus.modules.Laboratory.domain.entity.Test;
import org.lamisplus.modules.Laboratory.repository.LabOrderRepository;
import org.lamisplus.modules.Laboratory.repository.TestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository repository;

    public Test Save(Test test){
        return repository.save(test);
    }

    public Test Update(int order_id, Test updated_test){
        return repository.save(updated_test);
    }

    public String Delete(Integer id){
        Test labOrder = repository.findById(id).orElse(null);
        repository.delete(labOrder);
        return id.toString() + " deleted successfully";
    }

    public List<Test> GetTestPendingSampleCollection(){
        return repository.findAllBySamplesIsNull();
    }

    public List<Test> GetTestPendingResults(){
        return repository.findAllBySamplesIsNull();
    }
}
