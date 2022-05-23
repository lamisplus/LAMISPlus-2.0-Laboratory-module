package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.SampleDTO;
import org.lamisplus.modules.Laboratory.domain.dto.SampleVerificationDTO;
import org.lamisplus.modules.Laboratory.domain.entity.Sample;
import org.lamisplus.modules.Laboratory.domain.mapper.LabMapper;
import org.lamisplus.modules.Laboratory.repository.SampleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public SampleDTO SaveVerification(SampleVerificationDTO sampleVerificationDTO) {
        Sample sample = repository.findById(sampleVerificationDTO.getSampleId()).orElse(null);
        sample.setDateSampleVerified(sampleVerificationDTO.getDateSampleVerified());
        sample.setTimeSampleVerified(sampleVerificationDTO.getTimeSampleVerified());
        sample.setSampleConfirm(sampleVerificationDTO.getSampleConfirm());
        sample.setCommentSampleVerified(sampleVerificationDTO.getCommentSampleVerified());

        return labMapper.tosSampleDto(repository.save(sample));
    }

    public String DeleteVerification(int id) {
        Sample sample = repository.findById(id).orElse(null);
        sample.setDateSampleVerified(null);
        sample.setTimeSampleVerified(null);
        sample.setSampleConfirm(null);
        sample.setCommentSampleVerified(null);

        return "Sample verification deleted for id " + id;
    }
}
