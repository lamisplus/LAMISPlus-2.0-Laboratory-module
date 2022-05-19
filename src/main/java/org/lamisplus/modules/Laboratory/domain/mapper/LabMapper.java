package org.lamisplus.modules.Laboratory.domain.mapper;

import org.lamisplus.modules.Laboratory.domain.dto.*;
import org.lamisplus.modules.Laboratory.domain.entity.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LabMapper {
    LabOrder toLabOrder(LabOrderDTO labOrderDTO);
    LabTest toLabTest(LabTestDTO labTestDTO);
    LabTestGroup toLabTestGroup(LabTestGroupDTO labTestGroupDTO);
    Result toResult(ResultDTO resultDTO);
    Sample tosSample(SampleDTO sampleDTO);
    Test toTest(TestDTO testDTO);

    LabOrderDTO toLabOrderDto(LabOrder labOrder);
    LabTestDTO toLabTestDto(LabTest labTest);
    LabTestGroupDTO toLabTestGroupDto(LabTestGroup labTestGroup);
    ResultDTO toResultDto(Result result);
    SampleDTO tosSampleDto(Sample sample);
    TestDTO toTestDto(Test test);

    List<LabOrderDTO> toLabOrderDtoList(List<LabOrder> drugs);
    List<LabTestDTO> toLabTestDtoList(List<LabTest> labTest);
    List<LabTestGroupDTO> toLabTestGroupDtoList(List<LabTestGroup> labTestGroup);
    List<ResultDTO> toResultDtoList(List<Result> result);
    List<SampleDTO> tosSampleDtoList(List<Sample> sample);
    List<TestDTO> toTestDtoList(List<Test> test);
}
