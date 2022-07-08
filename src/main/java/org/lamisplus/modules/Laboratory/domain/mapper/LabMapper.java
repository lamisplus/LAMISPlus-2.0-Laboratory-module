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
    Sample toVerifiedSample(VerifiedSampleDTO verifiedSampleDTO);

    LabOrderDTO toLabOrderDto(LabOrder labOrder);
    LabTestDTO toLabTestDto(LabTest labTest);
    LabTestGroupDTO toLabTestGroupDto(LabTestGroup labTestGroup);
    ResultDTO toResultDto(Result result);
    SampleDTO tosSampleDto(Sample sample);
    TestDTO toTestDto(Test test);
    VerifiedSampleDTO toVerifiedSampleDto(Sample sample);
    SampleResponseDTO toSampleResponseDto(Sample sample);

    List<LabOrderDTO> toLabOrderDtoList(List<LabOrder> labOrders);
    List<LabTestDTO> toLabTestDtoList(List<LabTest> labTests);
    List<LabTestGroupDTO> toLabTestGroupDtoList(List<LabTestGroup> labTestGroups);
    List<ResultDTO> toResultDtoList(List<Result> results);
    List<SampleDTO> tosSampleDtoList(List<Sample> samples);
    List<TestDTO> toTestDtoList(List<Test> tests);
    List<VerifiedSampleDTO> toVerifiedSampleDtoList(List<Sample> samples);
    List<SampleResponseDTO> toSampleResponseDtoList(List<Sample> samples);
}
