INSERT INTO permission (id, description, name, date_created, created_by, date_modified, modified_by, archived)
VALUES ((SELECT MAX(id) + 1 FROM permission), 'Create Lab Order', 'create_lab_order', current_timestamp, 'Kennedy', current_timestamp, 'Kennedy', 0);
		
INSERT INTO permission (id, description, name, date_created, created_by, date_modified, modified_by, archived)
VALUES ((SELECT MAX(id) + 1 FROM permission), 'Edit Lab Order', 'edit_lab_order', current_timestamp, 'Kennedy', current_timestamp, 'Kennedy', 0);
		
INSERT INTO permission (id, description, name, date_created, created_by, date_modified, modified_by, archived)
VALUES ((SELECT MAX(id) + 1 FROM permission), 'View Lab Order', 'view_lab_order', current_timestamp, 'Kennedy', current_timestamp, 'Kennedy', 0);
		
INSERT INTO permission (id, description, name, date_created, created_by, date_modified, modified_by, archived)
VALUES ((SELECT MAX(id) + 1 FROM permission), 'Delete Lab Order', 'delete_lab_order', current_timestamp, 'Kennedy', current_timestamp, 'Kennedy', 0);


INSERT INTO permission (id, description, name, date_created, created_by, date_modified, modified_by, archived)
VALUES ((SELECT MAX(id) + 1 FROM permission), 'Record Sample collection', 'create_sample_collection', current_timestamp, 'Kennedy', current_timestamp, 'Kennedy', 0);
		
INSERT INTO permission (id, description, name, date_created, created_by, date_modified, modified_by, archived)
VALUES ((SELECT MAX(id) + 1 FROM permission), 'Edit Sample Collection', 'edit_sample_collection', current_timestamp, 'Kennedy', current_timestamp, 'Kennedy', 0);
		
INSERT INTO permission (id, description, name, date_created, created_by, date_modified, modified_by, archived)
VALUES ((SELECT MAX(id) + 1 FROM permission), 'View Sample Collection', 'view_sample_collection', current_timestamp, 'Kennedy', current_timestamp, 'Kennedy', 0);
		
INSERT INTO permission (id, description, name, date_created, created_by, date_modified, modified_by, archived)
VALUES ((SELECT MAX(id) + 1 FROM permission), 'Delete Sample Collection', 'delete_sample_collection', current_timestamp, 'Kennedy', current_timestamp, 'Kennedy', 0);


INSERT INTO permission (id, description, name, date_created, created_by, date_modified, modified_by, archived)
VALUES ((SELECT MAX(id) + 1 FROM permission), 'Record Sample Verification', 'create_sample_verification', current_timestamp, 'Kennedy', current_timestamp, 'Kennedy', 0);
		
INSERT INTO permission (id, description, name, date_created, created_by, date_modified, modified_by, archived)
VALUES ((SELECT MAX(id) + 1 FROM permission), 'Edit Sample Verification', 'edit_sample_verification', current_timestamp, 'Kennedy', current_timestamp, 'Kennedy', 0);
		
INSERT INTO permission (id, description, name, date_created, created_by, date_modified, modified_by, archived)
VALUES ((SELECT MAX(id) + 1 FROM permission), 'View Sample Verification', 'view_sample_verification', current_timestamp, 'Kennedy', current_timestamp, 'Kennedy', 0);
		
INSERT INTO permission (id, description, name, date_created, created_by, date_modified, modified_by, archived)
VALUES ((SELECT MAX(id) + 1 FROM permission), 'Delete Sample Verification', 'delete_sample_verification', current_timestamp, 'Kennedy', current_timestamp, 'Kennedy', 0);


INSERT INTO permission (id, description, name, date_created, created_by, date_modified, modified_by, archived)
VALUES ((SELECT MAX(id) + 1 FROM permission), 'Record Test Results', 'create_test_results', current_timestamp, 'Kennedy', current_timestamp, 'Kennedy', 0);
		
INSERT INTO permission (id, description, name, date_created, created_by, date_modified, modified_by, archived)
VALUES ((SELECT MAX(id) + 1 FROM permission), 'Edit Test Results', 'edit_test_results', current_timestamp, 'Kennedy', current_timestamp, 'Kennedy', 0);
		
INSERT INTO permission (id, description, name, date_created, created_by, date_modified, modified_by, archived)
VALUES ((SELECT MAX(id) + 1 FROM permission), 'View Test Results', 'view_test_results', current_timestamp, 'Kennedy', current_timestamp, 'Kennedy', 0);
		
INSERT INTO permission (id, description, name, date_created, created_by, date_modified, modified_by, archived)
VALUES ((SELECT MAX(id) + 1 FROM permission), 'Delete Test Results', 'delete_test_results', current_timestamp, 'Kennedy', current_timestamp, 'Kennedy', 0);
		

