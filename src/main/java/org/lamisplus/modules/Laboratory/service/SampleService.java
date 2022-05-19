package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.SampleDTO;
import org.lamisplus.modules.Laboratory.domain.entity.Sample;
import org.lamisplus.modules.Laboratory.domain.mapper.LabMapper;
import org.lamisplus.modules.Laboratory.repository.SampleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SampleService {
    private final SampleRepository repository;
    private final LabMapper labMapper;

    public SampleDTO Save(SampleDTO sampleDTO){
        Sample sample = labMapper.tosSample(sampleDTO);
        return labMapper.tosSampleDto(repository.save(sample));
    }

    public SampleDTO Update(int order_id, SampleDTO sampleDTO){
        Sample updated_sample = labMapper.tosSample(sampleDTO);
        return labMapper.tosSampleDto(repository.save(updated_sample));
    }

    public String Delete(Integer id){
        Sample labOrder = repository.findById(id).orElse(null);
        repository.delete(labOrder);
        return id.toString() + " deleted successfully";
    }
}
