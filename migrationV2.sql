-- Migration script for PTTKHT database
-- Tạo các bảng cơ bản, không có index và ràng buộc phức tạp

-- Bảng customer
CREATE TABLE IF NOT EXISTS customer (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(255),
    password VARCHAR(255),
    appointment_id VARCHAR(255),
    sales_invoice_id VARCHAR(255),
    member_id VARCHAR(255)
);

-- Bảng member
CREATE TABLE IF NOT EXISTS member (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    staff_id VARCHAR(255)
);

-- Bảng staff
CREATE TABLE IF NOT EXISTS staff (
    id VARCHAR(255) PRIMARY KEY,
    position VARCHAR(255),
    member_id VARCHAR(255)
);

-- Bảng vehical
CREATE TABLE IF NOT EXISTS vehical (
    id VARCHAR(255) PRIMARY KEY,
    model VARCHAR(255),
    license_plate VARCHAR(255),
    sales_invoice_id VARCHAR(255)
);

-- Bảng supplier
CREATE TABLE IF NOT EXISTS supplier (
    id VARCHAR(255) PRIMARY KEY,
    address VARCHAR(255),
    name VARCHAR(255)
);

-- Bảng part
CREATE TABLE IF NOT EXISTS part (
    id VARCHAR(255) PRIMARY KEY,
    unit VARCHAR(255),
    name VARCHAR(255)
);

-- Bảng service
CREATE TABLE IF NOT EXISTS service (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255)
);

-- Bảng appointment
CREATE TABLE IF NOT EXISTS appointment (
    id VARCHAR(255) PRIMARY KEY,
    status INTEGER,
    scheduled_time DATE,
    location VARCHAR(255),
    appointment_date DATE,
    customer_id VARCHAR(255)
);

-- Bảng assignment
CREATE TABLE IF NOT EXISTS assignment (
    id VARCHAR(255) PRIMARY KEY,
    delivery_date DATE,
    deadline DATE,
    sales_invoice_id VARCHAR(255)
);

-- Bảng purchase_invoice
CREATE TABLE IF NOT EXISTS purchase_invoice (
    id VARCHAR(255) PRIMARY KEY,
    purchase_date DATE,
    status VARCHAR(255),
    staff_id VARCHAR(255)
);

-- Bảng sales_invoice
CREATE TABLE IF NOT EXISTS sales_invoice (
    id VARCHAR(255) PRIMARY KEY,
    sale_date DATE,
    total_amount FLOAT,
    payment_status VARCHAR(255),
    vehicle_id VARCHAR(255)
);

-- Bảng supplier_part
CREATE TABLE IF NOT EXISTS supplier_part (
    id VARCHAR(255) PRIMARY KEY,
    supplier_id VARCHAR(255),
    part_id VARCHAR(255)
);

-- Bảng purchaseinvoice_supplierpart
CREATE TABLE IF NOT EXISTS purchaseinvoice_supplierpart (
    id VARCHAR(255) PRIMARY KEY,
    quantity INTEGER,
    price FLOAT,
    supplier_part_id VARCHAR(255),
    purchase_invoice_id VARCHAR(255)
);

-- Bảng saleinvoice_supplierpart
CREATE TABLE IF NOT EXISTS saleinvoice_purchaseinvoice_supplierpart (
    id VARCHAR(255) PRIMARY KEY,
    quantity INTEGER,
    price FLOAT,
    purchase_invoice_supplier_part_id VARCHAR(255)
);

-- Bảng service_sales_invoice
CREATE TABLE IF NOT EXISTS service_sales_invoice (
    id VARCHAR(255) PRIMARY KEY,
    service_id VARCHAR(255),
    sales_invoice_id VARCHAR(255)
);

-- Bảng staff_assignment
CREATE TABLE IF NOT EXISTS staff_assignment (
    id VARCHAR(255) PRIMARY KEY,
    staff_id VARCHAR(255),
    assignment_id VARCHAR(255),
    sales_invoice_id VARCHAR(255)
);




INSERT INTO service (id, name)
VALUES
('srv001', 'Rửa xe toàn bộ'),
('srv002', 'Thay dầu động cơ'),
('srv003', 'Kiểm tra phanh và má phanh'),
('srv004', 'Cân chỉnh thước lái'),
('srv005', 'Vệ sinh khoang máy'),
('srv006', 'Thay nước làm mát'),
('srv007', 'Bảo dưỡng điều hòa'),
('srv008', 'Thay lọc gió động cơ'),
('srv009', 'Đánh bóng sơn xe'),
('srv010', 'Kiểm tra ắc quy và hệ thống điện');


INSERT INTO supplier (id, address, name) VALUES
('SUP001', '12 Nguyễn Văn Cừ, Hà Nội', 'Công ty TNHH AutoParts Việt'),
('SUP002', '45 Lê Lợi, TP. Hồ Chí Minh', 'Phụ Tùng An Phát'),
('SUP003', '89 Trần Phú, Đà Nẵng', 'Garage Minh Tâm'),
('SUP004', '22 Lý Thường Kiệt, Hải Phòng', 'Công ty TNHH Cơ Khí Nam Sơn'),
('SUP005', '35 Nguyễn Trãi, Hà Nội', 'AutoPro Việt Nam'),
('SUP006', '101 Võ Văn Kiệt, Cần Thơ', 'Phụ Tùng Nam Hòa'),
('SUP007', '77 Hùng Vương, Huế', 'Trung Tâm Thiết Bị Ô Tô Huế'),
('SUP008', '5A Phạm Văn Đồng, Bình Dương', 'Công ty TNHH Sài Gòn Auto'),
('SUP009', '48 Điện Biên Phủ, Đà Lạt', 'Đại Lý Ô Tô Lâm Đồng'),
('SUP010', '19A Nguyễn Văn Linh, Quảng Ninh', 'Phụ Tùng Tuấn Hưng');


INSERT INTO part (id, unit, name) VALUES
('P001', 'cái', 'Mỏ lết'),
('P002', 'bộ', 'Bộ phanh đĩa trước'),
('P003', 'cái', 'Lọc gió động cơ'),
('P004', 'cái', 'Bugi đánh lửa'),
('P005', 'bộ', 'Dây curoa cam'),
('P006', 'lít', 'Dầu động cơ 10W40'),
('P007', 'cái', 'Kính chiếu hậu'),
('P008', 'cái', 'Lốp xe Michelin 205/55R16'),
('P009', 'bộ', 'Đèn pha LED'),
('P010', 'cái', 'Ắc quy GS 12V-45Ah');

INSERT INTO member (id, name, username, password, email, staff_id) VALUES
('MB001', 'Trân Canh Hung ', 'tran.hung', '412414', 'myemail@gmail.com', 'ST001');

INSERT INTO staff (id, position, member_id) VALUES
('ST001', 'warehousestaff', 'MB001');

INSERT INTO customer (id, name, email, phone_number, password, member_id) VALUES
('TT1', 'TranCanhHung', 'myemailwork@gmail.com', '21412', '32121', 'MB002');



INSERT INTO supplier_part (id, supplier_id, part_id) VALUES
('SP001', 'SUP001', 'P001'),
('SP002', 'SUP001', 'P002'),
('SP003', 'SUP001', 'P003'),
('SP004', 'SUP001', 'P004'),
('SP005', 'SUP001', 'P005'),
('SP006', 'SUP001', 'P006'),
('SP007', 'SUP001', 'P007'),
('SP008', 'SUP001', 'P008'),
('SP009', 'SUP001', 'P009'),
('SP010', 'SUP001', 'P010'),

('SP011', 'SUP002', 'P001'),
('SP012', 'SUP002', 'P003'),
('SP013', 'SUP002', 'P005'),
('SP014', 'SUP002', 'P007'),
('SP015', 'SUP002', 'P009'),
('SP016', 'SUP002', 'P002'),
('SP017', 'SUP002', 'P004'),
('SP018', 'SUP002', 'P006'),
('SP019', 'SUP002', 'P008'),
('SP020', 'SUP002', 'P010'),

('SP021', 'SUP003', 'P002'),
('SP022', 'SUP003', 'P004'),
('SP023', 'SUP003', 'P006'),
('SP024', 'SUP003', 'P008'),
('SP025', 'SUP003', 'P010'),
('SP026', 'SUP003', 'P001'),
('SP027', 'SUP003', 'P003'),
('SP028', 'SUP003', 'P005'),
('SP029', 'SUP003', 'P007'),
('SP030', 'SUP003', 'P009'),

('SP031', 'SUP004', 'P001'),
('SP032', 'SUP004', 'P004'),
('SP033', 'SUP004', 'P007'),
('SP034', 'SUP004', 'P010'),
('SP035', 'SUP004', 'P002'),
('SP036', 'SUP004', 'P005'),
('SP037', 'SUP004', 'P008'),
('SP038', 'SUP004', 'P003'),
('SP039', 'SUP004', 'P006'),
('SP040', 'SUP004', 'P009'),

('SP041', 'SUP005', 'P003'),
('SP042', 'SUP005', 'P006'),
('SP043', 'SUP005', 'P009'),
('SP044', 'SUP005', 'P002'),
('SP045', 'SUP005', 'P005'),
('SP046', 'SUP005', 'P008'),
('SP047', 'SUP005', 'P001'),
('SP048', 'SUP005', 'P004'),
('SP049', 'SUP005', 'P007'),
('SP050', 'SUP005', 'P010'),

('SP051', 'SUP006', 'P001'),
('SP052', 'SUP006', 'P002'),
('SP053', 'SUP006', 'P003'),
('SP054', 'SUP006', 'P004'),
('SP055', 'SUP006', 'P005'),
('SP056', 'SUP006', 'P006'),
('SP057', 'SUP006', 'P007'),
('SP058', 'SUP006', 'P008'),
('SP059', 'SUP006', 'P009'),
('SP060', 'SUP006', 'P010'),

('SP061', 'SUP007', 'P001'),
('SP062', 'SUP007', 'P003'),
('SP063', 'SUP007', 'P005'),
('SP064', 'SUP007', 'P007'),
('SP065', 'SUP007', 'P009'),
('SP066', 'SUP007', 'P002'),
('SP067', 'SUP007', 'P004'),
('SP068', 'SUP007', 'P006'),
('SP069', 'SUP007', 'P008'),
('SP070', 'SUP007', 'P010'),

('SP071', 'SUP008', 'P002'),
('SP072', 'SUP008', 'P004'),
('SP073', 'SUP008', 'P006'),
('SP074', 'SUP008', 'P008'),
('SP075', 'SUP008', 'P010'),
('SP076', 'SUP008', 'P001'),
('SP077', 'SUP008', 'P003'),
('SP078', 'SUP008', 'P005'),
('SP079', 'SUP008', 'P007'),
('SP080', 'SUP008', 'P009'),

('SP081', 'SUP009', 'P001'),
('SP082', 'SUP009', 'P004'),
('SP083', 'SUP009', 'P007'),
('SP084', 'SUP009', 'P010'),
('SP085', 'SUP009', 'P002'),
('SP086', 'SUP009', 'P005'),
('SP087', 'SUP009', 'P008'),
('SP088', 'SUP009', 'P003'),
('SP089', 'SUP009', 'P006'),
('SP090', 'SUP009', 'P009'),

('SP091', 'SUP010', 'P003'),
('SP092', 'SUP010', 'P006'),
('SP093', 'SUP010', 'P009'),
('SP094', 'SUP010', 'P002'),
('SP095', 'SUP010', 'P005'),
('SP096', 'SUP010', 'P008'),
('SP097', 'SUP010', 'P001'),
('SP098', 'SUP010', 'P004'),
('SP099', 'SUP010', 'P007'),
('SP100', 'SUP010', 'P010');
