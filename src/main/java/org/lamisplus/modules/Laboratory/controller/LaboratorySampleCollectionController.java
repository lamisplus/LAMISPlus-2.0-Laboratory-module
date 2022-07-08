package org.lamisplus.modules.Laboratory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.SampleDTO;
import org.lamisplus.modules.Laboratory.service.SampleService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/laboratory")
public class LaboratorySampleCollectionController {
    private final SampleService sampleService;

    @PostMapping("/samples/{labNumber}")
    public SampleDTO SaveSample(@PathVariable String labNumber, @RequestBody SampleDTO sample){
        return sampleService.Save(labNumber, sample);
    }

    @PutMapping("/samples/{id}/{labNumber}")
    public SampleDTO UpdateSample(@PathVariable int id, @PathVariable String labNumber, @RequestBody SampleDTO sampleDTO){
        return sampleService.Update(id, labNumber, sampleDTO);
    }

    @DeleteMapping("/samples/{id}")
    public String DeleteSample(@PathVariable Integer id){
        return sampleService.Delete(id);
    }
}
