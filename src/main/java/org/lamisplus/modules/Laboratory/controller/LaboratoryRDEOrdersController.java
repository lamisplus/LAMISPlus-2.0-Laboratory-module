package org.lamisplus.modules.Laboratory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.RDETestDTO;
import org.lamisplus.modules.Laboratory.service.RDELabTestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/laboratory")
public class LaboratoryRDEOrdersController {
    private final RDELabTestService labTestService;

    @PostMapping("/rde-orders")
    public List<RDETestDTO> SaveRDELabOrder(@RequestBody List<RDETestDTO> labOrders){
        return labTestService.SaveRDELabTests(labOrders);
    }

    @PutMapping("/rde-orders/{id}")
    public List<RDETestDTO> UpdateRDELabOrder(@PathVariable int id, @RequestBody List<RDETestDTO> labOrders){
        return labTestService.UpdateRDELabTests(id, labOrders);
    }

    @GetMapping("/rde-orders/{id}")
    public List<RDETestDTO> GetRDEOrderById(@PathVariable int id){
        return labTestService.GetRDEOrderById(id);
    }

    @GetMapping("/rde-orders/patients/{id}")
    public List<RDETestDTO> GetRDEOrderByPatientId(@PathVariable int id){
        return labTestService.GetRDEOrderByPatientId(id);
    }
}
