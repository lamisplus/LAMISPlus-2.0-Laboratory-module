--laboratory_order
CREATE SEQUENCE laboratory_order_id_seq;
CREATE TABLE public.laboratory_order
(
    id bigint NOT NULL DEFAULT nextval('laboratory_order_id_seq'),
    uuid character varying(100),
    patient_id integer,
	visit_id INTEGER,
	order_date date, 
	order_time TIME,
	userid character varying(100),
    PRIMARY KEY (id)
);
ALTER TABLE IF EXISTS public.laboratory_order OWNER to postgres;
ALTER SEQUENCE laboratory_order_id_seq OWNED BY laboratory_order.id;


--laboratory_test
CREATE SEQUENCE laboratory_test_id_seq;
CREATE TABLE public.laboratory_test
(
    id bigint NOT NULL DEFAULT nextval('laboratory_test_id_seq'),
    uuid character varying(100),
	patient_id INTEGER,
	lab_test_id INTEGER,
    description character varying(300),
	lab_number character varying(300),
	lab_test_group_id INTEGER,
	order_priority INTEGER,
	unit_measurement character varying(300),
	lab_test_order_status INTEGER,
	viral_load_indication INTEGER,
	lab_order_id INTEGER,
    PRIMARY KEY (id)
);
ALTER TABLE IF EXISTS public.laboratory_test OWNER to postgres;
ALTER SEQUENCE laboratory_test_id_seq OWNED BY laboratory_test.id;


--laboratory_sample
CREATE SEQUENCE laboratory_sample_id_seq;
CREATE TABLE public.laboratory_sample
(
    id bigint NOT NULL DEFAULT nextval('laboratory_sample_id_seq'),
	sample_type_id INTEGER,
    uuid character varying(100),
    sample_order_date DATE,
	sample_order_time TIME,
    sample_collection_mode INTEGER,
    date_sample_collected DATE,
	time_sample_collected TIME,
    comment_sample_collected character varying(500),
    sample_collected_by character varying(100),
    date_sample_verified DATE,
	time_sample_verified TIME,
    comment_sample_verified character varying(500),
    sample_verified_by character varying(100),
	test_id INTEGER,
    PRIMARY KEY (id)
);
ALTER TABLE IF EXISTS public.laboratory_sample OWNER to postgres;
ALTER SEQUENCE laboratory_sample_id_seq OWNED BY laboratory_sample.id;


--laboratory_result
CREATE SEQUENCE laboratory_result_id_seq;
CREATE TABLE public.laboratory_result
(
    id bigint NOT NULL DEFAULT nextval('laboratory_result_id_seq'),
    uuid character varying(100),
    date_assayed DATE,
	time_assayed TIME,
    date_result_reported DATE,
	time_result_reported TIME,
    result_reported character varying(500),
    result_reported_by character varying(100),
	test_id INTEGER,
    PRIMARY KEY (id)
);
ALTER TABLE IF EXISTS public.laboratory_result OWNER to postgres;
ALTER SEQUENCE laboratory_result_id_seq OWNED BY laboratory_result.id;


--laboratory_labtest
CREATE SEQUENCE laboratory_labtest_id_seq;
CREATE TABLE public.laboratory_labtest
(
    id bigint NOT NULL DEFAULT nextval('laboratory_labtest_id_seq'),
    uuid character varying(100),
    lab_test_name character varying(500),
	unit character varying(500),
	labtestgroup_id INTEGER,
    PRIMARY KEY (id)
);
ALTER TABLE IF EXISTS public.laboratory_labtest OWNER to postgres;
ALTER SEQUENCE laboratory_labtest_id_seq OWNED BY laboratory_labtest.id;


--laboratory_labtestgroup
CREATE SEQUENCE laboratory_labtestgroup_id_seq;
CREATE TABLE public.laboratory_labtestgroup
(
    id bigint NOT NULL DEFAULT nextval('laboratory_labtestgroup_id_seq'),
    uuid character varying(100),
    group_name character varying(500),
    PRIMARY KEY (id)
);
ALTER TABLE IF EXISTS public.laboratory_labtestgroup OWNER to postgres;
ALTER SEQUENCE laboratory_labtestgroup_id_seq OWNED BY laboratory_labtestgroup.id;

insert into laboratory_labtestgroup(id, group_name)values(1, 'Chemistry');
insert into laboratory_labtestgroup(id, group_name)values(2, 'Haematology');
insert into laboratory_labtestgroup(id, group_name)values(3, 'Microbiology');
insert into laboratory_labtestgroup(id, group_name)values(4, 'Others');

insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(1, 'CD4', 'cells/ul', 4);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(2, 'WBC', 'x10^9 c/l', 2);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(3, 'Lymphocytes', '/mm3', 2);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(4, 'Monocytes', '/mm3', 2);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(5, 'Polymorphs', '/mm3', 2);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(6, 'Eosinophils', '/mm3', 2);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(7, 'Basophils', '/mm3', 2);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(8, 'Haemoglobin (HB)', 'g/dl', 2);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(10, 'Platelets', 'x10^9 c/l', 2);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(11, 'K+', 'umol/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(12, 'Creatinine', 'umol/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(13, 'GLUCOSE', 'mmol/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(14, 'AST/SGOT', 'u/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(15, 'ALT/SGPT', 'u/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(16, 'Viral Load', 'copies/ml', 4);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(17, 'Na+', 'mmol/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(18, 'Cl-', 'mmol/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(19, 'HCO3', 'mmol/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(20, 'BUN', 'mmol/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(21, 'Total Bilirubin', 'umol/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(22, 'Amylase', 'u/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(23, 'Total Cholesterol', 'mmol/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(24, 'LDL', 'mmol/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(25, 'HDL', 'mmol/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(26, 'Triglyceride', 'mmol/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(27, 'HBsAg', '+/-', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(28, 'Pregnancy', '+/-', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(29, 'Malaria Parasite', '+/-', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(30, 'VIA/Pap Smear', '+/-', 3);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(31, 'Fasting Glucose (FBS)', 'mmol/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(32, 'VDRL', '+/-', 4);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(33, 'Alk. Phosphatase', 'u/l', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(34, 'PROTEIN', 'g/dl', 3);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(35, 'Sputum Smear', '+/-', 3);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(36, 'HCV', '+/-', 1);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(37, 'Stool microscopy', '+/-', 3);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(9, 'PCV', '%', 2);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(50, 'Visitect CD4', 'cells/ul', 4);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(51, 'TB-LAM', '+/-', 4);
insert into laboratory_labtest(id, lab_test_name, unit, labtestgroup_id) values(52, 'Cryptococcal Antigen', '+/-', 4);