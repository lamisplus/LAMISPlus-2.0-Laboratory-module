package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.TestDTO;
import org.lamisplus.modules.Laboratory.domain.entity.Test;
import org.lamisplus.modules.Laboratory.domain.mapper.LabMapper;
import org.lamisplus.modules.Laboratory.repository.TestRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestService {
    private final TestRepository repository;
    private final LabMapper labMapper;

    public TestDTO Save(TestDTO testDTO){
        Test test = labMapper.toTest(testDTO);
        test.setUuid(UUID.randomUUID().toString());
        return labMapper.toTestDto(repository.save(test));
    }

    public TestDTO Update(int order_id, TestDTO testDTO){
        Test test = labMapper.toTest(testDTO);
        return labMapper.toTestDto(repository.save(test));
    }

    public String Delete(Integer id){
        Test labOrder = repository.findById(id).orElse(null);
        assert labOrder != null;
        repository.delete(labOrder);
        return id + " deleted successfully";
    }

    public TestDTO FindById(Integer id){
        return labMapper.toTestDto(repository.findAllById(id).get(0));
    }
}
