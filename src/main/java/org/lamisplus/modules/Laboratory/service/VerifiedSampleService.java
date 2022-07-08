package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.VerifiedSampleDTO;
import org.lamisplus.modules.Laboratory.domain.entity.Sample;
import org.lamisplus.modules.Laboratory.domain.mapper.LabMapper;
import org.lamisplus.modules.Laboratory.repository.SampleRepository;
import org.lamisplus.modules.base.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class VerifiedSampleService {
    private final SampleRepository repository;
    private final LabMapper labMapper;

    public VerifiedSampleDTO Save(VerifiedSampleDTO verifiedSampleDTO, Integer sampleId){
        Sample sample = repository.findById(sampleId).orElse(null);
        sample.setDateSampleVerified(verifiedSampleDTO.getDateSampleVerified());
        sample.setTimeSampleVerified(verifiedSampleDTO.getTimeSampleVerified());
        sample.setCommentSampleVerified(verifiedSampleDTO.getCommentSampleVerified());
        sample.setSampleVerifiedBy(SecurityUtils.getCurrentUserLogin().orElse(""));

        return labMapper.toVerifiedSampleDto(repository.save(sample));
    }

    public String Delete(Integer sampleId){
        Sample sample = repository.findById(sampleId).orElse(null);
        sample.setDateSampleVerified(null);
        sample.setTimeSampleVerified(null);
        sample.setCommentSampleVerified(null);

        return "Sample verification deleted successfully";
    }
}
