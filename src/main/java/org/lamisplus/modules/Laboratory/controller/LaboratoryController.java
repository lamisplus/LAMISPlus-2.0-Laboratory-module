package org.lamisplus.modules.Laboratory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.entity.LabTest;
import org.lamisplus.modules.Laboratory.domain.entity.LabTestGroup;
import org.lamisplus.modules.Laboratory.domain.entity.Order;
import org.lamisplus.modules.Laboratory.service.LaboratoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/laboratory")
public class LaboratoryController {
    private final LaboratoryService service;

    @PostMapping("/orders")
    public Order Save(@RequestBody Order order){
        return service.Save(order);
    }

    @PutMapping("/orders/{id}")
    public Order Update(@PathVariable int id, @RequestBody Order order){
        return service.Update(id, order);
    }

    @GetMapping("/orders/{patient_id}")
    public List<Order> GetLabOrdersByPatientId(@PathVariable int patient_id){
        return service.GetAllOrdersByPatientId(patient_id);
    }

    @DeleteMapping("/orders/{id}")
    public String Delete(@PathVariable Integer id){
        return service.Delete(id);
    }

    @PostMapping("/labtestgroups")
    public LabTestGroup SaveLabTestGroup(@RequestBody LabTestGroup labTestGroup){
        return service.SaveLabTestGroup(labTestGroup);
    }

    @PostMapping("/labtestgroups/{id}")
    public LabTest SaveLabTest(@RequestBody LabTest test, @PathVariable int id){
        return service.SaveLabTest(test, id);
    }

    @GetMapping("/labtestgroups")
    public List<LabTestGroup> GetAllLabTestGroups(){
        return service.GetAllLabTests();
    }
}
