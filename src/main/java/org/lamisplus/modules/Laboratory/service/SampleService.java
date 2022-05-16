package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.Laboratory.domain.entity.Sample;
import org.lamisplus.modules.Laboratory.repository.SampleRepository;
import org.lamisplus.modules.Laboratory.repository.SampleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleService {
    private final SampleRepository repository;

    public Sample Save(Sample sample){
        return repository.save(sample);
    }

    public Sample Update(int order_id, Sample updated_sample){
        return repository.save(updated_sample);
    }

    public String Delete(Integer id){
        Sample labOrder = repository.findById(id).orElse(null);
        repository.delete(labOrder);
        return id.toString() + " deleted successfully";
    }
}
