package org.lamisplus.modules.Laboratory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.*;
import org.lamisplus.modules.Laboratory.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/laboratory")
public class LaboratoryOrdersController {
    private final LabOrderService labOrderService;
    private final TestService testService;


    @PostMapping("/orders")
    public LabOrderDTO SaveLabOrder(@RequestBody LabOrderDTO labOrder){
        return labOrderService.Save(labOrder);
    }

    @PutMapping("/orders/{id}")
    public LabOrderDTO UpdateLabOrder(@PathVariable int id, @RequestBody LabOrderDTO labOrder){
        return labOrderService.Update(id, labOrder);
    }

    @DeleteMapping("/orders/{id}")
    public String DeleteLabOrder(@PathVariable int id){
        return labOrderService.Delete(id);
    }

    @GetMapping("/orders")
    public List<PatientLabOrderDTO> GetAllLabOrders(){
        return labOrderService.GetAllLabOrders();
    }

    @GetMapping("/orders-by-patient-id/{patient_id}")
    public List<LabOrderDTO> GetLabOrdersByPatientId(@PathVariable int patient_id){
        return labOrderService.GetAllOrdersByPatientId(patient_id);
    }

    @GetMapping("/orders-by-visit-id/{visit_id}")
    public List<LabOrderDTO> GetLabOrdersByVisitId(@PathVariable int visit_id){
        return labOrderService.GetAllOrdersByVisitId(visit_id);
    }

    @GetMapping("/orders/pending-sample-collection")
    public List<PatientLabOrderDTO> GetOrdersPendingSampleCollection(){
        return labOrderService.GetOrdersPendingSampleCollection();
    }
    @GetMapping("/orders/pending-sample-verification")
    public List<PatientLabOrderDTO> GetOrdersPendingSampleVerification(){
        return labOrderService.GetOrdersPendingSampleVerification();
    }
    @GetMapping("/orders/pending-results")
    public List<PatientLabOrderDTO> GetOrdersPendingResults(){
        return labOrderService.GetOrdersPendingResults();
    }

    @PostMapping("/orders/tests")
    public TestDTO SaveTest(@RequestBody TestDTO test){
        return testService.Save(test);
    }

    @PutMapping("/orders/tests/{id}")
    public TestDTO UpdateTest(@PathVariable int id, @RequestBody TestDTO test){
        return testService.Update(id, test);
    }

    @DeleteMapping("/orders/tests/{id}")
    public String DeleteTest(@PathVariable Integer id){
        return testService.Delete(id);
    }
}
