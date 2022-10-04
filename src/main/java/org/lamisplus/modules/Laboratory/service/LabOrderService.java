package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.LabOrderDTO;
import org.lamisplus.modules.Laboratory.domain.dto.TestDTO;
import org.lamisplus.modules.Laboratory.domain.entity.*;
import org.lamisplus.modules.Laboratory.domain.mapper.LabMapper;
import org.lamisplus.modules.Laboratory.repository.LabOrderRepository;
import org.lamisplus.modules.Laboratory.repository.ResultRepository;
import org.lamisplus.modules.Laboratory.repository.SampleRepository;
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
    private final SampleRepository sampleRepository;
    private final ResultRepository resultRepository;

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

    public List<PatientLabOrderDTO> GetAllLabOrders(){
        List<LabOrder> orders = labOrderRepository.findAll();
        return  AppendPatientDetails(orders);
    }

    public List<PatientLabOrderDTO> GetAllOrdersByPatientId(int patientId){
        List<LabOrder> orders = labOrderRepository.findAllByPatientId(patientId);
        return  AppendPatientDetails(orders);
    }

    public List<PatientLabOrderDTO> AppendPatientDetails(List<LabOrder> orders){
        List<PatientLabOrderDTO> patientLabOrderDTOS = new ArrayList<>();
        for (LabOrder order: orders) {
            LabOrderDTO labOrderDTO = labMapper.toLabOrderDto(order);

            for(TestDTO testDTO: labOrderDTO.getTests()){
                List<Sample> samples = sampleRepository.findAllByTestId(testDTO.getId());
                List<Result> results = resultRepository.findAllByTestId(testDTO.getId());
                testDTO.setSamples(labMapper.tosSampleDtoList(samples));
                testDTO.setResults(labMapper.toResultDtoList(results));
            }

            PatientLabOrderDTO dto = new PatientLabOrderDTO();
            dto.setPatientAddress("Sample Address");
            dto.setPatientDob(null);
            dto.setPatientGender("Male");
            dto.setPatientFirstName("John");
            dto.setPatientId(order.getPatientId());
            dto.setPatientHospitalNumber("12345XYZ");
            dto.setPatientLastName("Doe");
            dto.setPatientPhoneNumber("+234567890");
            dto.setLabOrder(labOrderDTO);

            patientLabOrderDTOS.add(dto);
        }

        return patientLabOrderDTOS;
    }
}
