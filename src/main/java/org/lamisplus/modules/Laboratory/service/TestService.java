package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.PatientTestDTO;
import org.lamisplus.modules.Laboratory.domain.dto.TestDTO;
import org.lamisplus.modules.Laboratory.domain.entity.Test;
import org.lamisplus.modules.Laboratory.domain.mapper.LabMapper;
import org.lamisplus.modules.Laboratory.repository.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TestService {
    private final TestRepository repository;
    private final LabMapper labMapper;

    public TestDTO Save(TestDTO testDTO){
        Test test = labMapper.toTest(testDTO);
        return labMapper.toTestDto(repository.save(test));
    }

    public TestDTO Update(int order_id, TestDTO testDTO){
        Test test = labMapper.toTest(testDTO);
        return labMapper.toTestDto(repository.save(test));
    }

    public String Delete(Integer id){
        Test labOrder = repository.findById(id).orElse(null);
        repository.delete(labOrder);
        return id.toString() + " deleted successfully";
    }

    public List<PatientTestDTO> GetTestsPendingSampleCollection(){
        return appendPatientDetails(repository.findAllPendingSampleCollection());
    }

    public List<PatientTestDTO> GetTestsPendingSampleVerification(){
        return appendPatientDetails(repository.findAllPendingSampleVerification());
    }

    public List<PatientTestDTO> GetTestsPendingResults(){
        return appendPatientDetails(repository.findAllPendingResults());
    }

    private List<PatientTestDTO> appendPatientDetails(List<Test> testsList){
        List<PatientTestDTO> patientTestDTOS = new ArrayList<>();
        for (Test test: testsList) {
            PatientTestDTO dto = new PatientTestDTO();
            dto.setPatientAddress("Sample Address");
            dto.setPatientDob(null);
            dto.setPatientGender("Male");
            dto.setPatientFirstName("John");
            dto.setPatientId(test.getPatientId());
            dto.setPatientHospitalNumber("12345XYZ");
            dto.setPatientLastName("Doe");
            dto.setPatientPhoneNumber("+234567890");
            dto.setTest(labMapper.toTestDto(test));

            patientTestDTOS.add(dto);
        }

        return patientTestDTOS;
    }
}
