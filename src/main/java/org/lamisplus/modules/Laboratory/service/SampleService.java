package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.SampleDTO;
import org.lamisplus.modules.Laboratory.domain.entity.Sample;
import org.lamisplus.modules.Laboratory.domain.entity.Test;
import org.lamisplus.modules.Laboratory.domain.mapper.LabMapper;
import org.lamisplus.modules.Laboratory.repository.SampleRepository;
import org.lamisplus.modules.Laboratory.repository.TestRepository;
import org.lamisplus.modules.base.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.lamisplus.modules.Laboratory.utility.LabOrderStatus.SAMPLE_COLLECTED;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SampleService {
    private final SampleRepository repository;
    private final LabMapper labMapper;
    private final TestRepository testRepository;

    public SampleDTO Save(String labNumber, SampleDTO sampleDTO){
        Sample sample = labMapper.tosSample(sampleDTO);
        sample.setSampleCollectedBy(SecurityUtils.getCurrentUserLogin().orElse(""));
        SaveLabNumber(sample.getTestId(), labNumber, SAMPLE_COLLECTED);
        return labMapper.tosSampleDto(repository.save(sample));
    }

    public void SaveLabNumber(int testId, String labNumber, int orderStatus){
        Test test = testRepository.findById(testId).orElse(null);
        test.setLabNumber(labNumber);
        test.setLabTestOrderStatus(orderStatus);
        testRepository.save(test);
    }

    public SampleDTO Update(int orderId, String labNumber, SampleDTO sampleDTO){
        Sample updated_sample = labMapper.tosSample(sampleDTO);
        SaveLabNumber(updated_sample.getTestId(), labNumber, SAMPLE_COLLECTED);
        return labMapper.tosSampleDto(repository.save(updated_sample));
    }

    public String Delete(Integer id){
        Sample labOrder = repository.findById(id).orElse(null);
        repository.delete(labOrder);
        return id + " deleted successfully";
    }
}
