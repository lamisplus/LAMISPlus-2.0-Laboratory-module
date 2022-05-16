package org.lamisplus.modules.Laboratory.service;

import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.Laboratory.domain.entity.Result;
import org.lamisplus.modules.Laboratory.repository.ResultRepository;
import org.lamisplus.modules.Laboratory.repository.ResultRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository repository;

    public Result Save(Result result){
        return repository.save(result);
    }

    public Result Update(int order_id, Result updated_result){
        return repository.save(updated_result);
    }

    public String Delete(Integer id){
        Result labOrder = repository.findById(id).orElse(null);
        repository.delete(labOrder);
        return id.toString() + " deleted successfully";
    }
}
