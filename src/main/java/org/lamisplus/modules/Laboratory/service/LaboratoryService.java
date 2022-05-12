package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.Laboratory.domain.entity.LabTest;
import org.lamisplus.modules.Laboratory.domain.entity.LabTestGroup;
import org.lamisplus.modules.Laboratory.domain.entity.Order;
import org.lamisplus.modules.Laboratory.repository.LaboratoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaboratoryService {
    @Autowired
    private LaboratoryRepository repository;

    public Order Save(Order order){
        return repository.Save(order);
    }

    public Order Update(int id, Order order){
        return repository.Update(id, order);
    }

    public List<Order> GetAllOrdersByPatientId(int patient_id) {
        return repository.findOrdersByPatientId(patient_id);
    }

    public String Delete(int id) {
        return repository.Delete(id);
    }

    public List<LabTestGroup> GetAllLabTests(){
        return repository.GetLabTests();
    }

    public LabTest SaveLabTest(LabTest labTest, int group_id){
        return repository.saveLabTest(labTest, group_id);
    }

    public  LabTestGroup SaveLabTestGroup(LabTestGroup LabTestGroup){
        return repository.saveLabTestGroup(LabTestGroup);
    }
}
