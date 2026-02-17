INSERT INTO estates (id, code, name, address, remark) VALUES
(1, 'ESTATE-11', 'Estate 11', 'Sample Road No. 11', 'Simplified sample migrated from the legacy schema'),
(2, 'ESTATE-22', 'Estate 22', 'Sample Road No. 22', 'Dataset for Spring Boot beginner demos');

INSERT INTO buildings (id, estate_id, code, name) VALUES
(1, 1, 'B1', 'Building 1'),
(2, 1, 'B2', 'Building 2'),
(3, 2, 'B3', 'Building 3');

INSERT INTO units (id, building_id, code, name) VALUES
(1, 1, 'U1', 'Unit 1'),
(2, 1, 'U2', 'Unit 2'),
(3, 2, 'U1', 'Unit 1'),
(4, 3, 'U1', 'Unit 1');
