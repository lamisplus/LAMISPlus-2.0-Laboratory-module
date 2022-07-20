package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.HistoricalResultResponseDTO;
import org.lamisplus.modules.Laboratory.domain.dto.ResultDTO;
import org.lamisplus.modules.Laboratory.domain.entity.Result;
import org.lamisplus.modules.Laboratory.domain.entity.Test;
import org.lamisplus.modules.Laboratory.domain.mapper.LabMapper;
import org.lamisplus.modules.Laboratory.repository.HistoricalResultRepository;
import org.lamisplus.modules.Laboratory.repository.ResultRepository;
import org.lamisplus.modules.Laboratory.repository.TestRepository;
import org.lamisplus.modules.Laboratory.utility.JsonNodeTransformer;
import org.lamisplus.modules.base.security.SecurityUtils;
import org.lamisplus.modules.patient.domain.dto.PersonResponseDto;
import org.lamisplus.modules.patient.service.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.lamisplus.modules.Laboratory.utility.LabOrderStatus.RESULT_REPORTED;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository repository;
    private final LabMapper labMapper;
    private final TestRepository testRepository;
    private final HistoricalResultRepository historicalResultRepository;
    private final JsonNodeTransformer jsonNodeTransformer;
    private final PersonService personService;

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

    public List<HistoricalResultResponseDTO> GetHistoricalResultsByPatientId(Integer patientId){
        List<HistoricalResultResponseDTO> historicalResults =
                labMapper.toHistoricalResultResponseDtoList(historicalResultRepository
                        .findAllResultsByPatientId(patientId));

        for (HistoricalResultResponseDTO dto: historicalResults) {
            PersonResponseDto personResponseDTO = personService.getPersonById((long) dto.getPatientId());
            dto.setPatientAddress(jsonNodeTransformer.getNodeValue(personResponseDTO.getAddress(), "address", "city", true));
            dto.setPatientDob(personResponseDTO.getDateOfBirth());
            dto.setPatientGender(jsonNodeTransformer.getNodeValue(personResponseDTO.getGender(), null, "display", false));
            dto.setPatientFirstName(personResponseDTO.getFirstName());
            dto.setPatientHospitalNumber(jsonNodeTransformer.getNodeValue(personResponseDTO.getIdentifier(), "identifier", "value", true));
            dto.setPatientLastName(personResponseDTO.getSurname());
            dto.setPatientPhoneNumber(jsonNodeTransformer.getNodeValue(personResponseDTO.getContactPoint(),"contactPoint", "value", true));
        }

        return historicalResults;
    }
}
