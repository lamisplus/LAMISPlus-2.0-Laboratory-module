package org.lamisplus.modules.Laboratory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.domain.dto.*;
import org.lamisplus.modules.Laboratory.domain.entity.*;
import org.lamisplus.modules.Laboratory.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/laboratory")
public class LaboratoryController {
    private final LabTestGroupService labTestGroupService;
    private final LabOrderService labOrderService;
    private final TestService testService;
    private final SampleService sampleService;
    private final ResultService resultService;


    @PostMapping("/orders")
    public LabOrderDTO SaveLabOrder(@RequestBody LabOrderDTO labOrder){
        return labOrderService.Save(labOrder);
    }

    @PutMapping("/orders/{id}")
    public LabOrderDTO UpdateLabOrder(@PathVariable int id, @RequestBody LabOrderDTO labOrder){
        return labOrderService.Update(id, labOrder);
    }

    @GetMapping("/orders/{patient_id}")
    public List<LabOrderDTO> GetLabOrdersByPatientId(@PathVariable int patient_id){
        return labOrderService.GetAllOrdersByPatientId(patient_id);
    }

    @GetMapping("/orders")
    public List<PatientLabOrderDTO> GetAllLabOrders(){
        return labOrderService.GetAllLabOrders();
    }

    @DeleteMapping("/orders/{id}")
    public String DeleteLabOrder(@PathVariable Integer id){
        return labOrderService.Delete(id);
    }




    @PostMapping("/tests")
    public TestDTO SaveTest(@RequestBody TestDTO test){
        return testService.Save(test);
    }

    @PutMapping("/tests/{id}")
    public TestDTO UpdateTest(@PathVariable int id, @RequestBody TestDTO test){
        return testService.Update(id, test);
    }

    @DeleteMapping("/tests/{id}")
    public String DeleteTest(@PathVariable Integer id){
        return testService.Delete(id);
    }

    @GetMapping("/tests/pending-sample-collection")
    public List<PatientTestDTO> GetTestPendingSampleCollection(){
        return testService.GetTestsPendingSampleCollection();
    }

    @GetMapping("/tests/pending-sample-verification")
    public List<PatientTestDTO> GetTestPendingSampleVerification(){
        return testService.GetTestsPendingSampleVerification();
    }

    @GetMapping("/tests/pending-results")
    public List<PatientTestDTO> GetTestPendingResults(){
        return testService.GetTestsPendingResults();
    }



    @PostMapping("/samples")
    public SampleDTO SaveSample(@RequestBody SampleDTO sample){
        return sampleService.Save(sample);
    }

    @PutMapping("/samples/{id}")
    public SampleDTO UpdateSample(@PathVariable int id, @RequestBody SampleDTO sampleDTO){
        return sampleService.Update(id, sampleDTO);
    }

    @DeleteMapping("/samples/{id}")
    public String DeleteSample(@PathVariable Integer id){
        return sampleService.Delete(id);
    }



    @PostMapping("/results")
    public ResultDTO SaveResult(@RequestBody ResultDTO result){
        return resultService.Save(result);
    }

    @PutMapping("/results/{id}")
    public ResultDTO UpdateResult(@PathVariable int id, @RequestBody ResultDTO result){
        return resultService.Update(id, result);
    }

    @DeleteMapping("/results/{id}")
    public String DeleteResult(@PathVariable Integer id){
        return resultService.Delete(id);
    }



    @GetMapping("/labtestgroups")
    public List<LabTestGroupDTO> GetAllLabTestGroups(){
        return labTestGroupService.GetAllLabTestGroups();
    }
}
