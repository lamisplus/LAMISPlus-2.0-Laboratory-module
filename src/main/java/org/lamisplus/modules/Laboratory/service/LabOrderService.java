package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.Laboratory.domain.entity.LabOrder;
import org.lamisplus.modules.Laboratory.repository.LabOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabOrderService {
    private final LabOrderRepository labOrderRepository;

    public LabOrder Save(LabOrder labOrder){
        return labOrderRepository.save(labOrder);
    }

    public LabOrder Update(int order_id, LabOrder updated_Lab_order){
        return labOrderRepository.save(updated_Lab_order);
    }

    public String Delete(Integer id){
        LabOrder labOrder = labOrderRepository.findById(id).orElse(null);
        labOrderRepository.delete(labOrder);
        return id.toString() + " deleted successfully";
    }

    public List<LabOrder> GetAllOrdersByPatientId(int patient_id){
        return labOrderRepository.findAllByPatientId(patient_id);
    }
}
