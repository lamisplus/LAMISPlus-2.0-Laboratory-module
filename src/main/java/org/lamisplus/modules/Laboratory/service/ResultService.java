package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.ResultDTO;
import org.lamisplus.modules.Laboratory.domain.entity.Result;
import org.lamisplus.modules.Laboratory.domain.entity.Test;
import org.lamisplus.modules.Laboratory.domain.mapper.LabMapper;
import org.lamisplus.modules.Laboratory.repository.LabOrderRepository;
import org.lamisplus.modules.Laboratory.repository.ResultRepository;
import org.lamisplus.modules.Laboratory.repository.TestRepository;
import org.lamisplus.modules.Laboratory.utility.JsonNodeTransformer;
import org.lamisplus.modules.base.security.SecurityUtils;
import org.lamisplus.modules.patient.service.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.lamisplus.modules.Laboratory.utility.LabOrderStatus.RESULT_REPORTED;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository repository;
    private final LabMapper labMapper;
    private final TestRepository testRepository;
    private final LabOrderService labOrderService;
    private final JsonNodeTransformer jsonNodeTransformer;
    private final PersonService personService;
    private final LabOrderRepository labOrderRepository;

    public ResultDTO Save(ResultDTO resultDTO){
        Result result = labMapper.toResult(resultDTO);
        result.setResultReportedBy(SecurityUtils.getCurrentUserLogin().orElse(""));

        Test test = testRepository.findById(result.getTestId()).orElse(null);
        test.setLabTestOrderStatus(RESULT_REPORTED);
        testRepository.save(test);

        return labMapper.toResultDto(repository.save(result));
    }

    public ResultDTO Update(int order_id, ResultDTO resultDTO){
        Result updated_result = labMapper.toResult(resultDTO);
        return labMapper.toResultDto(repository.save(updated_result));
    }

    public String Delete(Integer id){
        Result labOrder = repository.findById(id).orElse(null);
        repository.delete(labOrder);
        return id.toString() + " deleted successfully";
    }

    public ResultDTO GetResultsById(Integer id){
        return labMapper.toResultDto(repository.findById(id).orElse(null));
    }

    public ResultDTO GetResultsByTestId(Integer TestId){
        return labMapper.toResultDto(repository.findAllByTestId(TestId).get(0));
    }

//    public List<HistoricalResultResponseDTO> GetHistoricalResultsByPatientId(Integer patientId){
//        List<LabOrderResponseDTO> orders =  labMapper.toLabOrderResponseDtoList(labOrderRepository.findAllByPatientId(patientId));
//        List<HistoricalResultResponseDTO> historicalResults = new ArrayList<>();
//
//        for(LabOrderResponseDTO order: orders){
//            LabOrderResponseDTO updated_order = labOrderService.AppendAdditionalTestDetails(order);
//
//            for(TestResponseDTO test: updated_order.getTests()){
//                HistoricalResultResponseDTO result = new HistoricalResultResponseDTO();
//
//                result.setId(test.getId());
//                result.setOrderId(updated_order.getId());
//                result.setPatientId(patientId);
//                result.setOrderDate(updated_order.getOrderDate());
//                result.setOrderTime(updated_order.getOrderTime());
//                result.setLabTestName(test.getLabTestName());
//                result.setGroupName(test.getLabTestGroupName());
//
//                /*
//                if((long) test.getSamples().size() > 0) {
//                    result.setDateSampleCollected(test.getSamples().get(0).getDateSampleCollected());
//                    result.setTimeSampleCollected(test.getSamples().get(0).getTimeSampleCollected());
//                    result.setDateSampleVerified(test.getSamples().get(0).getDateSampleVerified());
//                    result.setTimeSampleVerified(test.getSamples().get(0).getTimeSampleVerified());
//                }
//                if((long) test.getResults().size() > 0) {
//                    result.setResultReported(test.getResults().get(0).getResultReported());
//                    result.setDateResultReported(test.getResults().get(0).getDateResultReported());
//                    result.setTimeResultReported(test.getResults().get(0).getTimeResultReported());
//                }*/
//
//                PersonResponseDto personResponseDTO = personService.getPersonById((long) updated_order.getPatientId());
//                result.setPatientAddress(jsonNodeTransformer.getNodeValue(personResponseDTO.getAddress(), "address", "city", true));
//                result.setPatientDob(personResponseDTO.getDateOfBirth());
//                result.setPatientGender(jsonNodeTransformer.getNodeValue(personResponseDTO.getGender(), null, "display", false));
//                result.setPatientFirstName(personResponseDTO.getFirstName());
//                result.setPatientHospitalNumber(jsonNodeTransformer.getNodeValue(personResponseDTO.getIdentifier(), "identifier", "value", true));
//                result.setPatientLastName(personResponseDTO.getSurname());
//                result.setPatientPhoneNumber(jsonNodeTransformer.getNodeValue(personResponseDTO.getContactPoint(),"contactPoint", "value", true));
//
//                historicalResults.add(result);
//            }
//        }
//
//        return historicalResults;
//    }
}
