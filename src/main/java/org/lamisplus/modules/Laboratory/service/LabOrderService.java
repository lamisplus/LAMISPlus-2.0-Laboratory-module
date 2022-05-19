package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.LabOrderDTO;
import org.lamisplus.modules.Laboratory.domain.entity.LabOrder;
import org.lamisplus.modules.Laboratory.domain.mapper.LabMapper;
import org.lamisplus.modules.Laboratory.repository.LabOrderRepository;
import org.springframework.stereotype.Service;
import org.lamisplus.modules.Laboratory.domain.dto.PatientLabOrderDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class LabOrderService {
    private final LabOrderRepository labOrderRepository;

    private final LabMapper labMapper;

    public LabOrderDTO Save(LabOrderDTO labOrderDTO){
        LabOrder labOrder = labMapper.toLabOrder(labOrderDTO);
        labOrder.setUserId("Test User");
        return labMapper.toLabOrderDto(labOrderRepository.save(labOrder));
    }

    public LabOrderDTO Update(int order_id, LabOrderDTO labOrderDTO){
        LabOrder labOrder = labMapper.toLabOrder(labOrderDTO);
        labOrder.setUserId("Test User");
        return labMapper.toLabOrderDto(labOrderRepository.save(labOrder));
    }

    public String Delete(Integer id){
        LabOrder labOrder = labOrderRepository.findById(id).orElse(null);
        labOrderRepository.delete(labOrder);
        return id.toString() + " deleted successfully";
    }

    public List<LabOrderDTO> GetAllOrdersByPatientId(int patient_id){
        return  labMapper.toLabOrderDtoList(labOrderRepository.findAllByPatientId(patient_id));
    }

    public List<PatientLabOrderDTO> GetAllLabOrders(){
        List<PatientLabOrderDTO> patientLabOrderDTOS = new ArrayList<>();
        List<LabOrder> orders = labOrderRepository.findAll();
        for (LabOrder order: orders) {
            PatientLabOrderDTO dto = new PatientLabOrderDTO();
            dto.setPatientAddress("Sample Address");
            dto.setPatientDob(null);
            dto.setPatientGender("Male");
            dto.setPatientFirstName("John");
            dto.setPatientId(order.getPatientId());
            dto.setPatientHospitalNumber("12345XYZ");
            dto.setPatientLastName("Doe");
            dto.setPatientPhoneNumber("+234567890");
            dto.setLabOrder(labMapper.toLabOrderDto(order));

            patientLabOrderDTOS.add(dto);
        }

        return patientLabOrderDTOS;
    }
}
