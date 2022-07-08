package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.ResultDTO;
import org.lamisplus.modules.Laboratory.domain.entity.Result;
import org.lamisplus.modules.Laboratory.domain.mapper.LabMapper;
import org.lamisplus.modules.Laboratory.repository.ResultRepository;
import org.lamisplus.modules.base.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository repository;
    private final LabMapper labMapper;

    public ResultDTO Save(ResultDTO resultDTO){
        Result result = labMapper.toResult(resultDTO);
        result.setResultReportedBy(SecurityUtils.getCurrentUserLogin().orElse(""));
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
}
