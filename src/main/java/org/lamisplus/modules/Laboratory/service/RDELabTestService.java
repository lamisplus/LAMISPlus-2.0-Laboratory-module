package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.*;
import org.lamisplus.modules.base.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.lamisplus.modules.Laboratory.utility.LabOrderStatus.RESULT_REPORTED;

@Service
@Slf4j
@RequiredArgsConstructor
public class RDELabTestService {
    private final LabOrderService labOrderService;
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
            result.setDateResultReported(dto.getDateAssayed());
            result.setTimeResultReported(LocalTime.parse("00:00:00"));
            result.setDateAssayed(dto.getDateAssayed());
            result.setTimeAssayed(LocalTime.parse("00:00:00"));
            resultService.Save(result);
        }

        return labDtoList;
    }

    public List<RDETestDTO> UpdateRDELabTests(int id, List<RDETestDTO> labDtoList) {
        labOrderService.Delete(id);
        return SaveRDELabTests(labDtoList);
    }

    public List<RDETestDTO> GetRDEOrderById(Integer id){
        PatientLabOrderDTO order = labOrderService.GetOrderById(id);
        List<RDETestDTO> testDTOList = new ArrayList<>();

        for(TestResponseDTO dto:order.getLabOrder().getTests()){
            RDETestDTO testDTO = new RDETestDTO();
            testDTO.setLabTestId(dto.getLabTestId());
            testDTO.setComments(dto.getSamples().get(0).getCommentSampleCollected());
            testDTO.setLabTestGroupId(dto.getLabTestGroupId());
            testDTO.setDateAssayed(dto.getResults().get(0).getDateAssayed());
            testDTO.setResult(dto.getResults().get(0).getResultReported());
            testDTO.setLabNumber(dto.getLabNumber());
            testDTO.setPatientId(order.getPatientId());
            testDTO.setSampleCollectionDate(dto.getSamples().get(0).getDateSampleCollected());
            testDTO.setVisitId(order.getLabOrder().getVisitId());
            testDTO.setId(order.getLabOrder().getId());
            testDTO.setViralLoadIndication(dto.getViralLoadIndication());

            testDTOList.add(testDTO);
        }

        return testDTOList;
    }

    public List<RDETestDTO> GetRDEOrderByPatientId(Integer patientId){
        List<PatientLabOrderDTO> orders = labOrderService.GetAllOrdersByPatientId(patientId);
        List<RDETestDTO> testDTOList = new ArrayList<>();

        for(PatientLabOrderDTO order : orders){
            List<RDETestDTO> rdeTestDTOList = GetRDEOrderById(order.getLabOrder().getId());
            testDTOList.addAll(rdeTestDTOList);
        }

        return testDTOList;
    }
}
