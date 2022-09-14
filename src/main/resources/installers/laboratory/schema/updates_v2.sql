CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
update laboratory_labtest set uuid=uuid_generate_v4() where uuid is null;
update laboratory_labtestgroup set uuid=uuid_generate_v4() where uuid is null;
update laboratory_order set uuid=uuid_generate_v4() where uuid is null;
update laboratory_sample set uuid=uuid_generate_v4() where uuid is null;
update laboratory_test set uuid=uuid_generate_v4() where uuid is null;
update laboratory_result set uuid=uuid_generate_v4() where uuid is null;
