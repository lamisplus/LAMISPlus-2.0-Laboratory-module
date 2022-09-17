
alter table laboratory_order add patient_uuid Character varying(100);
alter table laboratory_order add facility_id int;

alter table laboratory_test add patient_uuid Character varying(100);
alter table laboratory_test add facility_id int;

alter table laboratory_sample add patient_uuid Character varying(100);
alter table laboratory_sample add facility_id Character varying(100);
alter table laboratory_sample add patient_id int;

alter table laboratory_result add patient_uuid Character varying(100);
alter table laboratory_result add facility_id Character varying(100);
alter table laboratory_result add patient_id int;

alter table laboratory_result add pcr_lab_sample_number Character varying(100);
alter table laboratory_result add date_sample_received_at_pcr_lab Date;
