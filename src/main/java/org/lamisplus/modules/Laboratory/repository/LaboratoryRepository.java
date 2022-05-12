package org.lamisplus.modules.Laboratory.repository;

import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.Laboratory.domain.entity.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class LaboratoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public Order Save(Order order) {
        String uuid = UUID.randomUUID().toString();

        jdbcTemplate.update("INSERT INTO laboratory_order (uuid, visit_id, patient_id, userid, order_date) " +
                        "VALUES (?, ?, ?, ?, ?)",
                uuid,
                order.getVisit_id(),
                order.getPatient_id(),
                order.getUser_id(),
                order.getOrder_date()
        );

        Order saved_order = findOrderByUUID(uuid).orElse(null);

        for(Test labTest : order.getLabTests()) {
            SaveTest(labTest, saved_order.getId());
        }

        List<Test> labTestList = findTestsByOrderId(saved_order.getId());
        saved_order.setLabTests(labTestList);

        return saved_order;
    }

    public Test SaveTest(Test labTest, int order_id){
        String uuid = UUID.randomUUID().toString();

        jdbcTemplate.update("INSERT INTO public.laboratory_test(" +
                        "uuid, patient_id, laboratory_order_id, description, lab_number, lab_test_group_id, " +
                        "order_priority, unit_measurement, lab_test_order_status, viral_load_indication)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                uuid,
                labTest.getPatient_id(),
                order_id,
                labTest.getDescription(),
                labTest.getLab_number(),
                labTest.getLab_test_group_id(),
                labTest.getOrder_priority(),
                labTest.getUnit_measurement(),
                labTest.getLab_test_order_status(),
                labTest.getViral_load_indication()
        );

        Test saved_test = findTestByUUID(uuid).orElse(null);

        for(Result result : labTest.getReported_results()) {
            SaveResult(result, saved_test.getId());
        }

        List<Result> results = findResultsByTestId(saved_test.getId());
        saved_test.setReported_results(results);

        for(Sample sample : labTest.getSamples()) {
            SaveSample(sample, saved_test.getId());
        }

        List<Sample> samples = findSamplesByTestId(saved_test.getId());
        saved_test.setSamples(samples);

        return saved_test;
    }

    private Result SaveResult(Result result, int test_id) {
        String uuid = UUID.randomUUID().toString();

        jdbcTemplate.update("INSERT INTO public.laboratory_result(" +
                        "laboratory_test_id, uuid, date_assayed, date_result_reported, result_reported, result_reported_by)" +
                        "VALUES (?, ?, ?, ?, ?, ?);",
                test_id,
                uuid,
                result.getDate_assayed(),
                result.getDate_result_reported(),
                result.getResult_reported(),
                result.getResult_reported_by()
        );

        return findResultByUUID(uuid).orElse(null);
    }

    private Sample SaveSample(Sample sample, int test_id) {
        String uuid = UUID.randomUUID().toString();

        jdbcTemplate.update("INSERT INTO public.laboratory_sample(" +
                        "laboratory_test_id, uuid, sample_type_id, sample_order_date, sample_collection_mode, date_sample_collected, " +
                        "comment_sample_collected, sample_collected_by, date_sample_verified, comment_sample_verified, sample_verified_by)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                test_id,
                uuid,
                sample.getSample_type_id(),
                sample.getSample_order_date(),
                sample.getSample_collection_mode(),
                sample.getDate_sample_collected(),
                sample.getComment_sample_collected(),
                sample.getSample_collected_by(),
                sample.getComment_sample_verified(),
                sample.getComment_sample_verified(),
                sample.getSample_verified_by()
        );

        return findSampleByUUID(uuid).orElse(null);
    }

    public Order Update(int order_id, Order order) {
        jdbcTemplate.update("UPDATE public.laboratory_order" +
                        "SET patient_id=?, visit_id=?, order_date=?, userid=?" +
                        "WHERE id=?;",
                order.getPatient_id(),
                order.getVisit_id(),
                order.getOrder_date(),
                order.getUser_id(),
                order.getId()
        );

        jdbcTemplate.update("delete from laboratory_test where laboratory_order_id=? ", order_id);

        for(Test labTest : order.getLabTests()) {
            SaveTest(labTest, order_id);
        }

        Order updated_order = findOrderByUUID(order.getUuid()).orElse(null);
        assert updated_order != null;

        List<Test> labTestList = findTestsByOrderId(updated_order.getId());

        updated_order.setLabTests(labTestList);

        return updated_order;
    }

    public String Delete(int id){
        jdbcTemplate.update("delete from laboratory_order where id=? ", id);
        return id + " deleted successfully";
    }

    public List<Order> findOrdersByPatientId(int patient_id) {
        List<Order> laborders =  jdbcTemplate.query("SELECT * FROM laboratory_order where patient_id = ? ",
                new BeanPropertyRowMapper<Order>(Order.class), patient_id);

        for(Order order : laborders) {
            List<Test> labTestList = findTestsByOrderId(order.getId());
            order.setLabTests(labTestList);
        }

        return laborders;
    }

    public Optional<Order> findOrderByUUID(String uuid) {
        return jdbcTemplate.query("SELECT * FROM laboratory_order where order_id = ? ",
                new BeanPropertyRowMapper<Order>(Order.class), uuid).stream().findFirst();
    }

    public List<Test> findTestsByOrderId(int order_id) {
        return jdbcTemplate.query("SELECT * FROM laboratory_test where encounter_id = ? ",
                new BeanPropertyRowMapper<Test>(Test.class), order_id);
    }

    public Optional<Test> findTestByUUID(String uuid) {
        return jdbcTemplate.query("SELECT * FROM laboratory_test where uuid = ? ",
                new BeanPropertyRowMapper<Test>(Test.class), uuid).stream().findFirst();
    }

    public List<Result> findResultsByTestId(Integer test_id) {
        return jdbcTemplate.query("SELECT * FROM laboratory_result where laboratory_test_id = ? ",
                new BeanPropertyRowMapper<Result>(Result.class), test_id);
    }

    public Optional<Result> findResultByUUID(String uuid) {
        return jdbcTemplate.query("SELECT * FROM laboratory_result where uuid = ? ",
                new BeanPropertyRowMapper<Result>(Result.class), uuid).stream().findFirst();
    }

    public List<Sample> findSamplesByTestId(Integer test_id) {
        return jdbcTemplate.query("SELECT * FROM laboratory_sample where laboratory_test_id = ? ",
                new BeanPropertyRowMapper<Sample>(Sample.class), test_id);
    }

    public Optional<Sample> findSampleByUUID(String uuid) {
        return jdbcTemplate.query("SELECT * FROM laboratory_sample where uuid = ? ",
                new BeanPropertyRowMapper<Sample>(Sample.class), uuid).stream().findFirst();
    }

    public LabTest saveLabTest(LabTest labTest, int group_id){
        String uuid = UUID.randomUUID().toString();

        jdbcTemplate.update("INSERT INTO public.laboratory_labtest(" +
                        "group_id, uuid, lab_test_name, unit)" +
                        "VALUES (?, ?, ?, ?);",
                group_id,
                uuid,
                labTest.getLab_test_name(),
                labTest.getUnit()
        );

        return findLabTestByUUID(uuid).orElse(null);
    }

    public Optional<LabTest> findLabTestByUUID(String uuid) {
        return jdbcTemplate.query("SELECT * FROM laboratory_labtest where uuid = ? ",
                new BeanPropertyRowMapper<LabTest>(LabTest.class), uuid).stream().findFirst();
    }

    public List<LabTest> findLabTestsByGroupId(int group_id) {
        return jdbcTemplate.query("SELECT * FROM laboratory_labtest where group_id = ? ",
                new BeanPropertyRowMapper<LabTest>(LabTest.class), group_id);
    }

    public Optional<LabTestGroup> findLabTestGroupByUUID(String uuid) {
        return jdbcTemplate.query("SELECT * FROM laboratory_labtestgroup where uuid = ? ",
                new BeanPropertyRowMapper<LabTestGroup>(LabTestGroup.class), uuid).stream().findFirst();
    }

    public LabTestGroup saveLabTestGroup(LabTestGroup labTestGroup){
        String uuid = UUID.randomUUID().toString();

        jdbcTemplate.update("INSERT INTO public.laboratory_labtestgroup(uuid, group_name)" +
                        "VALUES (?, ?);",
                uuid,
                labTestGroup.getGroup_name()
        );

        LabTestGroup saved_group = findLabTestGroupByUUID(uuid).orElse(null);

        for(LabTest labTest : labTestGroup.getLabTests()) {
            saveLabTest(labTest, saved_group.getId());
        }

        List<LabTest> labTestList = findLabTestsByGroupId(saved_group.getId());
        saved_group.setLabTests(labTestList);

        return saved_group;
    }

    public List<LabTestGroup> GetLabTests(){
        return jdbcTemplate.query("SELECT * FROM laboratory_labtestgroup",
                new BeanPropertyRowMapper<LabTestGroup>(LabTestGroup.class));
    }
}
