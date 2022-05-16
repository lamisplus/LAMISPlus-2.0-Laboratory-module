package org.lamisplus.modules.Laboratory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public LabOrder SaveLabOrder(@RequestBody LabOrder labOrder){
        return labOrderService.Save(labOrder);
    }

    @PutMapping("/orders/{id}")
    public LabOrder UpdateLabOrder(@PathVariable int id, @RequestBody LabOrder labOrder){
        return labOrderService.Update(id, labOrder);
    }

    @GetMapping("/orders/{patient_id}")
    public List<LabOrder> GetLabOrdersByPatientId(@PathVariable int patient_id){
        return labOrderService.GetAllOrdersByPatientId(patient_id);
    }

    @DeleteMapping("/orders/{id}")
    public String DeleteLabOrder(@PathVariable Integer id){
        return labOrderService.Delete(id);
    }




    @PostMapping("/tests")
    public Test SaveTest(@RequestBody Test test){
        return testService.Save(test);
    }

    @PutMapping("/tests/{id}")
    public Test UpdateTest(@PathVariable int id, @RequestBody Test test){
        return testService.Update(id, test);
    }

    @DeleteMapping("/tests/{id}")
    public String DeleteTest(@PathVariable Integer id){
        return testService.Delete(id);
    }

    @GetMapping("/tests/pending-samples")
    public List<Test> GetTestPendingSampleCollection(){
        return testService.GetTestPendingSampleCollection();
    }

    @GetMapping("/tests/pending-results")
    public List<Test> GetTestPendingResults(){
        return testService.GetTestPendingResults();
    }



    @PostMapping("/samples")
    public Sample SaveSample(@RequestBody Sample sample){
        return sampleService.Save(sample);
    }

    @PutMapping("/samples/{id}")
    public Sample UpdateSample(@PathVariable int id, @RequestBody Sample labOrder){
        return sampleService.Update(id, labOrder);
    }

    @DeleteMapping("/samples/{id}")
    public String DeleteSample(@PathVariable Integer id){
        return sampleService.Delete(id);
    }



    @PostMapping("/results")
    public Result SaveResult(@RequestBody Result result){
        return resultService.Save(result);
    }

    @PutMapping("/results/{id}")
    public Result UpdateResult(@PathVariable int id, @RequestBody Result result){
        return resultService.Update(id, result);
    }

    @DeleteMapping("/results/{id}")
    public String DeleteResult(@PathVariable Integer id){
        return resultService.Delete(id);
    }



    @GetMapping("/labtestgroups")
    public List<LabTestGroup> GetAllLabTestGroups(){
        return labTestGroupService.GetAllLabTestGroups();
    }
}
