INSERT INTO estates (id, code, name, address, remark) VALUES
(1, 'ESTATE-11', '社区 11', '示例路 11 号', '来自原始 SQL 的简化示例'),
(2, 'ESTATE-22', '社区 22', '示例路 22 号', '用于 Spring Boot 新手演示');

INSERT INTO buildings (id, estate_id, code, name) VALUES
(1, 1, 'B1', '1 号楼'),
(2, 1, 'B2', '2 号楼'),
(3, 2, 'B3', '3 号楼');

INSERT INTO units (id, building_id, code, name) VALUES
(1, 1, 'U1', '1 单元'),
(2, 1, 'U2', '2 单元'),
(3, 2, 'U1', '1 单元'),
(4, 3, 'U1', '1 单元');
