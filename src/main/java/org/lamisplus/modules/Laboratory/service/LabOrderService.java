package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.*;
import org.lamisplus.modules.Laboratory.domain.entity.LabOrder;
import org.lamisplus.modules.Laboratory.domain.entity.Test;
import org.lamisplus.modules.Laboratory.domain.mapper.LabMapper;
import org.lamisplus.modules.Laboratory.repository.LabOrderRepository;
import org.lamisplus.modules.Laboratory.repository.ResultRepository;
import org.lamisplus.modules.Laboratory.repository.SampleRepository;
import org.lamisplus.modules.Laboratory.utility.JsonNodeTransformer;
import org.lamisplus.modules.base.security.SecurityUtils;
import org.lamisplus.modules.patient.domain.dto.PersonResponseDto;
import org.lamisplus.modules.patient.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LabOrderService {
    private final LabOrderRepository labOrderRepository;
    private final SampleRepository sampleRepository;
    private final ResultRepository resultRepository;

    private final LabMapper labMapper;
    private final PersonService personService;
    private final JsonNodeTransformer jsonNodeTransformer;

    public LabOrderDTO Save(LabOrderDTO labOrderDTO){
        LabOrder labOrder = labMapper.toLabOrder(labOrderDTO);
        labOrder.setUserId(SecurityUtils.getCurrentUserLogin().orElse(""));
        return labMapper.toLabOrderDto(labOrderRepository.save(labOrder));
    }

    public LabOrderDTO Update(int order_id, LabOrderDTO labOrderDTO){
        LabOrder labOrder = labMapper.toLabOrder(labOrderDTO);
        labOrder.setUserId(SecurityUtils.getCurrentUserLogin().orElse(""));
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

    public LabOrderDTO GetOrderById(int id){
        return  labMapper.toLabOrderDto(labOrderRepository.findById(id).orElse(null));
    }

    public List<LabOrderDTO> GetAllOrdersByVisitId(int visit_id){
        return  labMapper.toLabOrderDtoList(labOrderRepository.findAllByVisitId(visit_id));
    }

    public List<PatientLabOrderDTO> GetAllLabOrders(){
        List<LabOrder> orders = labOrderRepository.findAll();
        return AppendPatientDetails(orders);
    }

    public List<PatientLabOrderDTO> GetOrdersPendingSampleCollection(){
        List<LabOrder> orders = labOrderRepository.findAllPendingSampleCollection();
        for(LabOrder order: orders){
            for(Test test: order.getTests()) {
                test.setLabTestOrderStatus(0);
            }
        }
        return AppendPatientDetails(orders);
    }

    public List<PatientLabOrderDTO> GetOrdersPendingSampleVerification(){
        List<LabOrder> orders = labOrderRepository.findAllPendingSampleVerification();
        for(LabOrder order: orders){
            for(Test test: order.getTests()) {
                test.setLabTestOrderStatus(1);
            }
        }
        return AppendPatientDetails(orders);
    }

    public List<PatientLabOrderDTO> GetOrdersPendingResults(){
        List<LabOrder> orders = labOrderRepository.findAllPendingResults();
        for(LabOrder order: orders){
            for(Test test: order.getTests()) {
                test.setLabTestOrderStatus(3);
            }
        }
        return AppendPatientDetails(orders);
    }

    private LabOrderDTO AppendSamplesAndResults(LabOrderDTO labOrderDTO){
        List<TestDTO> testDTOList = labOrderDTO.getTests();
        for (TestDTO testDTO: testDTOList) {
            List<SampleResponseDTO> sampleDTOList = labMapper.toSampleResponseDtoList(sampleRepository.findAllByTestId(testDTO.getId()));
            List<ResultDTO> resultDTOList = labMapper.toResultDtoList(resultRepository.findAllByTestId(testDTO.getId()));
            testDTO.setSamples(sampleDTOList);
            testDTO.setResults(resultDTOList);
        }
        labOrderDTO.setTests(testDTOList);
        return labOrderDTO;
    }

    private List<PatientLabOrderDTO> AppendPatientDetails(List<LabOrder> orders){
        List<PatientLabOrderDTO> patientLabOrderDTOS = new ArrayList<>();

        for (LabOrder order: orders) {
            PersonResponseDto personResponseDTO = personService.getPersonById((long) order.getPatientId());
            PatientLabOrderDTO dto = new PatientLabOrderDTO();
            dto.setPatientAddress(jsonNodeTransformer.getNodeValue(personResponseDTO.getAddress(), "address", "city", true));
            dto.setPatientDob(personResponseDTO.getDateOfBirth());
            dto.setPatientGender(jsonNodeTransformer.getNodeValue(personResponseDTO.getGender(), null, "display", false));
            dto.setPatientFirstName(personResponseDTO.getFirstName());
            dto.setPatientId(order.getPatientId());
            dto.setPatientHospitalNumber(jsonNodeTransformer.getNodeValue(personResponseDTO.getIdentifier(), "identifier", "value", true));
            dto.setPatientLastName(personResponseDTO.getSurname());
            dto.setPatientPhoneNumber(jsonNodeTransformer.getNodeValue(personResponseDTO.getContactPoint(),"contactPoint", "value", true));
            dto.setLabOrder(AppendSamplesAndResults(labMapper.toLabOrderDto(order)));

            patientLabOrderDTOS.add(dto);
        }

        return patientLabOrderDTOS;
    }
}
