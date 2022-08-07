package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.audit4j.core.util.Log;
import org.jetbrains.annotations.NotNull;
import org.lamisplus.modules.Laboratory.domain.dto.*;
import org.lamisplus.modules.Laboratory.domain.entity.LabOrder;
import org.lamisplus.modules.Laboratory.domain.entity.Test;
import org.lamisplus.modules.Laboratory.domain.mapper.LabMapper;
import org.lamisplus.modules.Laboratory.repository.*;
import org.lamisplus.modules.Laboratory.utility.JsonNodeTransformer;
import org.lamisplus.modules.base.security.SecurityUtils;
import org.lamisplus.modules.patient.domain.dto.PersonResponseDto;
import org.lamisplus.modules.patient.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.lamisplus.modules.Laboratory.utility.LabOrderStatus.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class LabOrderService {
    private final LabOrderRepository labOrderRepository;
    private final SampleRepository sampleRepository;
    private final ResultRepository resultRepository;
    private final LabTestGroupService labTestGroupService;
    private final LabTestService labTestService;
    private final CodesetRepository codesetRepository;
    private final PendingOrderRepository pendingOrderRepository;

    private final LabMapper labMapper;
    private final PersonService personService;
    private final JsonNodeTransformer jsonNodeTransformer;

    private static final Integer APPLICATION_CODE_SET = 1;
    private static final Integer LAB_TEST = 2;
    private static final Integer LAB_TEST_UNITS = 3;
    private static final Integer LAB_TEST_GROUP = 4;
    private static final Integer LAB_ORDER_STATUS = 5;

    public LabOrderResponseDTO Save(LabOrderDTO labOrderDTO){
        try {
            LabOrder labOrder = labMapper.toLabOrder(labOrderDTO);
            labOrder.setUserId(SecurityUtils.getCurrentUserLogin().orElse(""));
            labOrder.setUuid(UUID.randomUUID().toString());
            for (Test test : labOrder.getTests()) {
                test.setLabTestOrderStatus(PENDING_SAMPLE_COLLECTION);
            }
            Log.info(labOrderDTO);
            return labMapper.toLabOrderResponseDto(labOrderRepository.save(labOrder));
        }
        catch(Exception e){
            Log.error(e);
            return null;
        }
    }

    public LabOrderResponseDTO Update(int order_id, LabOrderDTO labOrderDTO){
        LabOrder labOrder = labMapper.toLabOrder(labOrderDTO);
        labOrder.setUserId(SecurityUtils.getCurrentUserLogin().orElse(""));
        for (Test test:labOrder.getTests()){
            test.setLabTestOrderStatus(PENDING_SAMPLE_COLLECTION);
        }
        return labMapper.toLabOrderResponseDto(labOrderRepository.save(labOrder));
    }

    public String Delete(Integer id){
        LabOrder labOrder = labOrderRepository.findById(id).orElse(null);
        labOrderRepository.delete(labOrder);
        return id + " deleted successfully";
    }

    public List<PatientLabOrderDTO> GetAllOrdersByPatientId(int patient_id){
        return  AppendPatientDetails(labOrderRepository.findAllByPatientId(patient_id));
    }

    public PatientLabOrderDTO GetOrderById(int id){
        List<LabOrder> orders =  new ArrayList<>();
        orders.add(labOrderRepository.findById(id).orElse(null));
        List<PatientLabOrderDTO> patientLabOrderDTOS = AppendPatientDetails(orders);
        return patientLabOrderDTOS.get(0);
    }

    public List<PatientLabOrderDTO> GetAllOrdersByVisitId(int visit_id){
        return AppendPatientDetails(labOrderRepository.findAllByVisitId(visit_id));
    }

    public List<PatientLabOrderDTO> GetAllLabOrders(){
        List<LabOrder> orders = labOrderRepository.findAll();
        return AppendPatientDetails(orders);
    }

    public List<PendingOrderDTO> GetOrdersPendingSampleCollection(){
        List<PendingOrderDTO> pendingOrderList = labMapper.toPendingOrderDtoList(pendingOrderRepository.findAllPendingSampleCollection());
        return getPendingOrderDTOS(pendingOrderList);
    }

    public List<PendingOrderDTO> GetOrdersPendingSampleVerification(){
        List<PendingOrderDTO> pendingOrderList = labMapper.toPendingOrderDtoList(pendingOrderRepository.findAllPendingSampleVerification());
        return getPendingOrderDTOS(pendingOrderList);
    }

    @NotNull
    private List<PendingOrderDTO> getPendingOrderDTOS(List<PendingOrderDTO> pendingOrderList) {
        for (PendingOrderDTO dto: pendingOrderList) {
            PersonResponseDto personResponseDTO = personService.getPersonById((long) dto.getPatientId());
            dto.setPatientAddress(jsonNodeTransformer.getNodeValue(personResponseDTO.getAddress(), "address", "city", true));
            dto.setPatientDob(personResponseDTO.getDateOfBirth());
            dto.setPatientGender(jsonNodeTransformer.getNodeValue(personResponseDTO.getGender(), null, "display", false));
            dto.setPatientFirstName(personResponseDTO.getFirstName());
            dto.setPatientId(dto.getPatientId());
            dto.setPatientHospitalNumber(jsonNodeTransformer.getNodeValue(personResponseDTO.getIdentifier(), "identifier", "value", true));
            dto.setPatientLastName(personResponseDTO.getSurname());
            dto.setPatientPhoneNumber(jsonNodeTransformer.getNodeValue(personResponseDTO.getContactPoint(),"contactPoint", "value", true));
        }

        return pendingOrderList;
    }

    public List<PendingOrderDTO> GetOrdersPendingResults(){
        List<PendingOrderDTO> pendingOrderList = labMapper.toPendingOrderDtoList(pendingOrderRepository.findAllPendingResults());
        return getPendingOrderDTOS(pendingOrderList);
    }

    public LabOrderResponseDTO AppendAdditionalTestDetails(LabOrderResponseDTO labOrderDTO){
        List<TestResponseDTO> testDTOList = UpdateTestResponses(labOrderDTO.getTests());
        for (TestResponseDTO testDTO: testDTOList) {
            List<SampleResponseDTO> sampleDTOList = labMapper.toSampleResponseDtoList(sampleRepository.findAllByTestId(testDTO.getId()));

            for (SampleResponseDTO sampleResponseDTO : sampleDTOList) {
                sampleResponseDTO.setSampleTypeName(GetNameById(sampleResponseDTO.getSampleTypeId(), APPLICATION_CODE_SET));
                sampleResponseDTO.setLabNumber(testDTO.getLabNumber());
            }

            List<ResultDTO> resultDTOList = labMapper.toResultDtoList(resultRepository.findAllByTestId(testDTO.getId()));
            testDTO.setSamples(sampleDTOList);
            testDTO.setResults(resultDTOList);
            testDTO.setOrderDate(labOrderDTO.getOrderDate());
            testDTO.setOrderTime(labOrderDTO.getOrderTime());
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
            dto.setLabOrder(AppendAdditionalTestDetails(labMapper.toLabOrderResponseDto(order)));

            patientLabOrderDTOS.add(dto);
        }

        return patientLabOrderDTOS;
    }

    private List<TestResponseDTO> UpdateTestResponses(List<TestResponseDTO> testResponseDTOList) {
        for(TestResponseDTO testResponseDTO:testResponseDTOList){
            testResponseDTO.setLabTestName(GetNameById(testResponseDTO.getLabTestId(), LAB_TEST));
            testResponseDTO.setLabTestGroupName(GetNameById(testResponseDTO.getLabTestGroupId(), LAB_TEST_GROUP));
            testResponseDTO.setUnitMeasurement(GetNameById(testResponseDTO.getLabTestId(), LAB_TEST_UNITS));
            testResponseDTO.setOrderPriorityName(GetNameById(testResponseDTO.getOrderPriority(), APPLICATION_CODE_SET));
            testResponseDTO.setLabTestOrderStatusName(GetNameById(testResponseDTO.getLabTestOrderStatus(), LAB_ORDER_STATUS));
            testResponseDTO.setViralLoadIndicationName(GetNameById(testResponseDTO.getViralLoadIndication(), APPLICATION_CODE_SET));
        }

        return testResponseDTOList;
    }

    public String GetNameById(Integer id, Integer itemType){
        try {
            if (Objects.equals(itemType, APPLICATION_CODE_SET)) {
                if (id > 0) {
                    return Objects.requireNonNull(codesetRepository.findById(id).orElse(null)).getDisplay();
                } else {
                    return "";
                }
            } else if (Objects.equals(itemType, LAB_TEST)) {
                return labTestService.FindLabTestNameById(id);
            } else if (Objects.equals(itemType, LAB_TEST_UNITS)) {
                return labTestService.FindLabTestMeasurementById(id);
            } else if (Objects.equals(itemType, LAB_TEST_GROUP)) {
                return labTestGroupService.FindLabTestGroupNameById(id);
            } else if (Objects.equals(itemType, LAB_ORDER_STATUS)) {
                if (Objects.equals(id, PENDING_SAMPLE_COLLECTION)) {
                    return "Pending Sample Collection";
                }
                else if (Objects.equals(id, SAMPLE_COLLECTED)) {
                    return "Sample collected";
                }
                else if (Objects.equals(id, SAMPLE_TRANSFERRED)) {
                    return "Sample Transferred";
                }
                else if (Objects.equals(id, SAMPLE_VERIFIED)) {
                    return "Sample Verified";
                }
                else if (Objects.equals(id, SAMPLE_REJECTED)) {
                    return "Sample Rejected";
                }
                else if (Objects.equals(id, RESULT_REPORTED)) {
                    return "Result Reported";
                }
                else {
                    return "";
                }
            } else {
                return "";
            }
        }
        catch (Exception exception){
            return "";
        }
    }

    public List<HistoricalResultResponseDTO> GetHistoricalResultsByPatientId(Integer patientId){
        List<LabOrderResponseDTO> orders =  labMapper.toLabOrderResponseDtoList(labOrderRepository.findAllByPatientId(patientId));
        List<HistoricalResultResponseDTO> historicalResults = new ArrayList<>();

        for(LabOrderResponseDTO order: orders){
            LabOrderResponseDTO updated_order = AppendAdditionalTestDetails(order);

            for(TestResponseDTO test: updated_order.getTests()){
                HistoricalResultResponseDTO result = new HistoricalResultResponseDTO();

                result.setId(test.getId());
                result.setOrderId(updated_order.getId());
                result.setPatientId(patientId);
                result.setOrderDate(updated_order.getOrderDate());
                result.setOrderTime(updated_order.getOrderTime());
                result.setLabTestName(test.getLabTestName());
                result.setGroupName(test.getLabTestGroupName());


                if((long) test.getSamples().size() > 0) {
                    result.setDateSampleCollected(test.getSamples().get(0).getDateSampleCollected());
                    result.setTimeSampleCollected(test.getSamples().get(0).getTimeSampleCollected());
                    result.setDateSampleVerified(test.getSamples().get(0).getDateSampleVerified());
                    result.setTimeSampleVerified(test.getSamples().get(0).getTimeSampleVerified());
                    result.setSampleTypeName(test.getSamples().get(0).getSampleTypeName());
                }
                if((long) test.getResults().size() > 0) {
                    result.setResultReported(test.getResults().get(0).getResultReported());
                    result.setDateResultReported(test.getResults().get(0).getDateResultReported());
                    result.setTimeResultReported(test.getResults().get(0).getTimeResultReported());
                }

                PersonResponseDto personResponseDTO = personService.getPersonById((long) updated_order.getPatientId());
                result.setPatientAddress(jsonNodeTransformer.getNodeValue(personResponseDTO.getAddress(), "address", "city", true));
                result.setPatientDob(personResponseDTO.getDateOfBirth());
                result.setPatientGender(jsonNodeTransformer.getNodeValue(personResponseDTO.getGender(), null, "display", false));
                result.setPatientFirstName(personResponseDTO.getFirstName());
                result.setPatientHospitalNumber(jsonNodeTransformer.getNodeValue(personResponseDTO.getIdentifier(), "identifier", "value", true));
                result.setPatientLastName(personResponseDTO.getSurname());
                result.setPatientPhoneNumber(jsonNodeTransformer.getNodeValue(personResponseDTO.getContactPoint(),"contactPoint", "value", true));

                historicalResults.add(result);
            }
        }

        return historicalResults;
    }
}
