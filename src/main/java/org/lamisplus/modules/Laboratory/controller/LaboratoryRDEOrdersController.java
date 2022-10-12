package org.lamisplus.modules.Laboratory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.RDETestDTO;
import org.lamisplus.modules.Laboratory.domain.dto.RDETestResponseDTO;
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
    @PutMapping("/rde-orders/{orderId}")
    public List<RDETestDTO> UpdateRDELabOrder(@PathVariable int orderId, @RequestBody List<RDETestDTO> labOrders){
        return labTestService.UpdateRDELabTests(orderId, labOrders);
    }
    @PutMapping("/rde-orders/tests/{id}")
    public RDETestDTO UpdateRDETest(@PathVariable int id, @RequestBody RDETestDTO rdeTestDTO){
        return labTestService.UpdateRDELabTest(id, rdeTestDTO);
    }

    @DeleteMapping("/rde-orders/tests/{id}")
    public String DeleteRDETest(@PathVariable int id){
        return labTestService.DeleteRDELabTest(id);
    }

    @GetMapping("/rde-orders/{orderId}")
    public List<RDETestResponseDTO> GetRDEOrderById(@PathVariable int orderId){
        return labTestService.GetRDEOrderById(orderId);
    }

    @GetMapping("/rde-orders/patients/{patientId}")
    public List<RDETestResponseDTO> GetRDEOrderByPatientId(@PathVariable int patientId){
        return labTestService.GetRDEOrderByPatientId(patientId);
    }

    @GetMapping("/rde-orders/latest-viral-loads/{patientId}")
    public RDETestResponseDTO GetLatestViralLoadByPatientId(@PathVariable int patientId){
        return labTestService.GetLatestVL(patientId);
    }
}
