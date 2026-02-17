INSERT INTO billing_charges (id, estate_code, unit_code, amount, due_date, paid, remark) VALUES
(1, 'ESTATE-11', 'B1-U1', 220.50, DATE '2026-03-01', FALSE, 'Property fee'),
(2, 'ESTATE-22', 'B3-U1', 180.00, DATE '2026-03-05', TRUE, 'Paid sample');

INSERT INTO work_orders (id, title, description, priority, status, created_at) VALUES
(1, 'Elevator noise', 'Night-time noise reported in Building 2 elevator', 'HIGH', 'OPEN', CURRENT_TIMESTAMP),
(2, 'Public lighting repair', 'Lighting issue in underground garage', 'MEDIUM', 'IN_PROGRESS', CURRENT_TIMESTAMP);

INSERT INTO parking_spaces (id, code, occupied, owner_name) VALUES
(1, 'P-001', TRUE, 'Alex'),
(2, 'P-002', FALSE, NULL);
