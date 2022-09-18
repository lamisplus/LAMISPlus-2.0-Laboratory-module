package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.*;
import org.lamisplus.modules.base.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.lamisplus.modules.Laboratory.utility.LabUtils.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class RDELabTestService {
    private final LabOrderService labOrderService;
    private final TestService testService;
    private final SampleService sampleService;
    private final ResultService resultService;

    //RDE
    public List<RDETestDTO> SaveRDELabTests(List<RDETestDTO> labDtoList){
        //Save order
        LabOrderDTO labOrderDTO=new LabOrderDTO();
        List<TestDTO> tests = new ArrayList<>();

        RDETestDTO rdeTestDTO = labDtoList.get(0);
        labOrderDTO.setOrderDate(rdeTestDTO.getSampleCollectionDate());
        labOrderDTO.setOrderTime(LocalTime.parse("00:00:00"));
        labOrderDTO.setPatientId(rdeTestDTO.getPatientId());
        labOrderDTO.setVisitId(rdeTestDTO.getVisitId());

        for (RDETestDTO dto:labDtoList) {
            TestDTO test = new TestDTO();
            test.setLabTestId(dto.getLabTestId());
            test.setLabTestGroupId(dto.getLabTestGroupId());
            test.setViralLoadIndication(dto.getViralLoadIndication());
            test.setDescription(dto.getComments());
            test.setLabTestOrderStatus(RESULT_REPORTED);
            tests.add(test);
        }

        labOrderDTO.setTests(tests);
        LabOrderResponseDTO responseDTO = labOrderService.Save(labOrderDTO);

        //save sample and result
        for(TestResponseDTO test:responseDTO.getTests()){
            RDETestDTO dto = labDtoList.stream().filter(submittedTest -> submittedTest.getLabTestId()==test.getLabTestId()).findFirst().orElse(null);

            //save sample
            SampleDTO sample = new SampleDTO();
            assert dto != null;
            sample.setDateSampleCollected(dto.getSampleCollectionDate());
            sample.setTimeSampleCollected(LocalTime.parse("00:00:00"));
            sample.setSampleCollectedBy(SecurityUtils.getCurrentUserLogin().orElse(""));
            sample.setTestId(test.getId());
            sample.setSampleTypeId(0);
            sampleService.Save(dto.getLabNumber(), sample);

            //save result
            ResultDTO result = new ResultDTO();
            result.setTestId(test.getId());
            result.setResultReported(dto.getResult());
            result.setDateResultReported(dto.getDateResultReceived());
            result.setTimeResultReported(LocalTime.parse("00:00:00"));
            result.setDateAssayed(dto.getDateAssayed());
            result.setTimeAssayed(LocalTime.parse("00:00:00"));
            resultService.Save(result);
        }

        return labDtoList;
    }

    public List<RDETestDTO> UpdateRDELabTests(int orderId, List<RDETestDTO> labDtoList) {
        labOrderService.Delete(orderId);
        return SaveRDELabTests(labDtoList);
    }

    public RDETestDTO UpdateRDELabTest(int orderId, RDETestDTO rdeTestDTO){
        TestDTO test = testService.FindById(rdeTestDTO.getId());
        test.setLabTestId(rdeTestDTO.getLabTestId());
        test.setLabTestGroupId(rdeTestDTO.getLabTestGroupId());
        test.setViralLoadIndication(rdeTestDTO.getViralLoadIndication());
        test.setDescription(rdeTestDTO.getComments());
        test.setLabTestOrderStatus(RESULT_REPORTED);
        testService.Update(orderId, test);

        SampleDTO sample = sampleService.FindByTestId(test.getId());
        sample.setDateSampleCollected(rdeTestDTO.getSampleCollectionDate());
        sample.setTimeSampleCollected(LocalTime.parse("00:00:00"));
        sample.setSampleCollectedBy(SecurityUtils.getCurrentUserLogin().orElse(""));
        sample.setTestId(test.getId());
        sample.setSampleTypeId(0);
        sampleService.Save(rdeTestDTO.getLabNumber(), sample);

        //save result
        ResultDTO result = resultService.GetResultsByTestId(test.getId());
        result.setTestId(test.getId());
        result.setResultReported(rdeTestDTO.getResult());
        result.setDateResultReported(rdeTestDTO.getDateResultReceived());
        result.setTimeResultReported(LocalTime.parse("00:00:00"));
        result.setDateAssayed(rdeTestDTO.getDateAssayed());
        result.setTimeAssayed(LocalTime.parse("00:00:00"));
        resultService.Save(result);

        return rdeTestDTO;
    }

    public String DeleteRDELabTest(int id){
        testService.Delete(id);
        SampleDTO sample = sampleService.FindByTestId(id);
        sampleService.Delete(sample.getId());
        ResultDTO result = resultService.GetResultsByTestId(id);
        resultService.Delete(result.getId());
        return id+" deleted successfully";
    }

    public List<RDETestResponseDTO> GetRDEOrderById(Integer id){
        PatientLabOrderDTO order = labOrderService.GetOrderById(id);
        List<RDETestResponseDTO> testDTOList = new ArrayList<>();

        for(TestResponseDTO dto:order.getLabOrder().getTests()){
            RDETestResponseDTO testDTO = new RDETestResponseDTO();
            testDTO.setLabTestId(dto.getLabTestId());
            testDTO.setLabNumber(dto.getLabNumber());
            testDTO.setPatientId(order.getPatientId());
            testDTO.setVisitId(order.getLabOrder().getVisitId());
            testDTO.setOrderId(order.getLabOrder().getId());
            testDTO.setId(dto.getId());
            testDTO.setComments(dto.getDescription());
            testDTO.setViralLoadIndication(dto.getViralLoadIndication());
            testDTO.setLabTestGroupId(dto.getLabTestGroupId());

            testDTO.setLabTestGroupName(labOrderService.GetNameById(dto.getLabTestGroupId(), LAB_TEST_GROUP));
            testDTO.setLabTestName(labOrderService.GetNameById(dto.getLabTestId(), LAB_TEST));
            testDTO.setViralLoadIndicationName(labOrderService.GetNameById(dto.getViralLoadIndication(), APPLICATION_CODE_SET));

            if(dto.getSamples().size()>0) {
                testDTO.setSampleCollectionDate(dto.getSamples().get(0).getDateSampleCollected());
            }

            if(dto.getResults().size()>0) {
                testDTO.setDateAssayed(dto.getResults().get(0).getDateAssayed());
                testDTO.setResult(dto.getResults().get(0).getResultReported());
                testDTO.setDateResultReceived(dto.getResults().get(0).getDateResultReported());
            }

            testDTOList.add(testDTO);
        }

        return testDTOList;
    }

    public List<RDETestResponseDTO> GetRDEOrderByPatientId(Integer patientId){
        List<PatientLabOrderDTO> orders = labOrderService.GetAllOrdersByPatientId(patientId);
        List<RDETestResponseDTO> testDTOList = new ArrayList<>();

        for(PatientLabOrderDTO order : orders){
            List<RDETestResponseDTO> rdeTestDTOList = GetRDEOrderById(order.getLabOrder().getId());
            testDTOList.addAll(rdeTestDTOList);
        }

        return testDTOList;
    }

    public RDETestResponseDTO GetLatestVL(Integer patientId){
        List<RDETestResponseDTO> labTests = GetRDEOrderByPatientId(patientId);
        List<RDETestResponseDTO> viralLoads = labTests.stream().filter(x -> x.getLabTestName().equals("Viral Load"))
                .sorted(Comparator.comparing(RDETestResponseDTO::getDateResultReceived)).collect(Collectors.toList());
        return viralLoads.get(viralLoads.size()-1);
    }
}
