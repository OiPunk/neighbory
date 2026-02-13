INSERT INTO billing_charges (id, estate_code, unit_code, amount, due_date, paid, remark) VALUES
(1, 'ESTATE-11', 'B1-U1', 220.50, DATE '2026-03-01', FALSE, '物业费'),
(2, 'ESTATE-22', 'B3-U1', 180.00, DATE '2026-03-05', TRUE, '已缴费样例');

INSERT INTO work_orders (id, title, description, priority, status, created_at) VALUES
(1, '电梯异响', '2号楼电梯夜间异响', 'HIGH', 'OPEN', CURRENT_TIMESTAMP),
(2, '公共照明维修', '地下车库照明损坏', 'MEDIUM', 'IN_PROGRESS', CURRENT_TIMESTAMP);

INSERT INTO parking_spaces (id, code, occupied, owner_name) VALUES
(1, 'P-001', TRUE, '张三'),
(2, 'P-002', FALSE, NULL);
