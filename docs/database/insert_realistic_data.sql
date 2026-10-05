-- Dữ liệu thực tế cho Quản lý Ký Túc Xá
-- Chạy file này sau khi đã tạo bảng

-- 1. Khu
INSERT INTO Khu (MaKhu, TenKhu, MoTa) VALUES ('K1', N'Khu A', N'Khu KTX Nam, 5 tầng');
INSERT INTO Khu (MaKhu, TenKhu, MoTa) VALUES ('K2', N'Khu B', N'Khu KTX Nữ, 5 tầng');
INSERT INTO Khu (MaKhu, TenKhu, MoTa) VALUES ('K3', N'Khu C', N'Khu KTX Cao cấp (Nam/Nữ), 3 tầng');
GO

-- 2. LoaiPhong
INSERT INTO LoaiPhong (MaLoaiPhong, TenLoaiPhong, SoNguoiToiDa, DonGia, MoTa) VALUES ('LP1', N'Phòng Thường 8 người', 8, 200000, N'Phòng quạt, giường tầng');
INSERT INTO LoaiPhong (MaLoaiPhong, TenLoaiPhong, SoNguoiToiDa, DonGia, MoTa) VALUES ('LP2', N'Phòng Thường 6 người', 6, 250000, N'Phòng quạt, giường tầng, rộng rãi');
INSERT INTO LoaiPhong (MaLoaiPhong, TenLoaiPhong, SoNguoiToiDa, DonGia, MoTa) VALUES ('LP3', N'Phòng Dịch vụ 4 người', 4, 800000, N'Máy lạnh, tủ lạnh, máy nước nóng');
GO

-- 3. Phong
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PA101', '101', 'K1', 'LP1', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PB101', '101', 'K2', 'LP2', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PC101', '101', 'K3', 'LP3', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PA102', '102', 'K1', 'LP1', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PB102', '102', 'K2', 'LP2', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PC102', '102', 'K3', 'LP3', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PA103', '103', 'K1', 'LP1', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PB103', '103', 'K2', 'LP2', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PC103', '103', 'K3', 'LP3', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PA104', '104', 'K1', 'LP1', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PB104', '104', 'K2', 'LP2', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PC104', '104', 'K3', 'LP3', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PA105', '105', 'K1', 'LP1', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PB105', '105', 'K2', 'LP2', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PC105', '105', 'K3', 'LP3', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PA201', '201', 'K1', 'LP1', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PB201', '201', 'K2', 'LP2', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PC201', '201', 'K3', 'LP3', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PA202', '202', 'K1', 'LP1', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PB202', '202', 'K2', 'LP2', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PC202', '202', 'K3', 'LP3', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PA203', '203', 'K1', 'LP1', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PB203', '203', 'K2', 'LP2', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PC203', '203', 'K3', 'LP3', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PA204', '204', 'K1', 'LP1', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PB204', '204', 'K2', 'LP2', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PC204', '204', 'K3', 'LP3', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PA205', '205', 'K1', 'LP1', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PB205', '205', 'K2', 'LP2', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PC205', '205', 'K3', 'LP3', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PA301', '301', 'K1', 'LP1', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PB301', '301', 'K2', 'LP2', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PC301', '301', 'K3', 'LP3', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PA302', '302', 'K1', 'LP1', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PB302', '302', 'K2', 'LP2', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PC302', '302', 'K3', 'LP3', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PA303', '303', 'K1', 'LP1', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PB303', '303', 'K2', 'LP2', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PC303', '303', 'K3', 'LP3', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PA304', '304', 'K1', 'LP1', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PB304', '304', 'K2', 'LP2', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PC304', '304', 'K3', 'LP3', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PA305', '305', 'K1', 'LP1', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PB305', '305', 'K2', 'LP2', N'Hoạt động');
INSERT INTO Phong (MaPhong, SoPhong, MaKhu, MaLoaiPhong, TrangThai) VALUES ('PC305', '305', 'K3', 'LP3', N'Hoạt động');
GO

-- 4. NguoiDung
INSERT INTO NguoiDung (MaNguoiDung, TenDangNhap, MatKhau, HoTen, SDT, ChucVu, TrangThai) VALUES ('ADMIN', 'admin', '123456', N'Nguyễn Quản Trị', '0900111222', N'Quản trị viên', N'Hoạt động');
INSERT INTO NguoiDung (MaNguoiDung, TenDangNhap, MatKhau, HoTen, SDT, ChucVu, TrangThai) VALUES ('QS01', 'quansinhA', '123456', N'Lê Trọng Quản', '0901222333', N'Quản sinh', N'Hoạt động');
INSERT INTO NguoiDung (MaNguoiDung, TenDangNhap, MatKhau, HoTen, SDT, ChucVu, TrangThai) VALUES ('QS02', 'quansinhB', '123456', N'Phạm Thị Sinh', '0902333444', N'Quản sinh', N'Hoạt động');
INSERT INTO NguoiDung (MaNguoiDung, TenDangNhap, MatKhau, HoTen, SDT, ChucVu, TrangThai) VALUES ('NV01', 'suachua1', '123456', N'Trần Thợ Điện', '0903444555', N'Nhân viên sửa chữa', N'Hoạt động');
INSERT INTO NguoiDung (MaNguoiDung, TenDangNhap, MatKhau, HoTen, SDT, ChucVu, TrangThai) VALUES ('NV02', 'suachua2', '123456', N'Hoàng Thợ Nước', '0904555666', N'Nhân viên sửa chữa', N'Hoạt động');
GO

-- 5. PhanQuyen
INSERT INTO PhanQuyen (MaPhanQuyen, MaNguoiDung, QuyenHan, NgayCap) VALUES ('PQ1', 'ADMIN', N'Toàn quyền', '2024-01-01');
INSERT INTO PhanQuyen (MaPhanQuyen, MaNguoiDung, QuyenHan, NgayCap) VALUES ('PQ2', 'QS01', N'Quản lý sinh viên', '2024-01-01');
INSERT INTO PhanQuyen (MaPhanQuyen, MaNguoiDung, QuyenHan, NgayCap) VALUES ('PQ3', 'QS02', N'Quản lý sinh viên', '2024-01-01');
INSERT INTO PhanQuyen (MaPhanQuyen, MaNguoiDung, QuyenHan, NgayCap) VALUES ('PQ4', 'NV01', N'Nhân viên sửa chữa', '2024-01-01');
INSERT INTO PhanQuyen (MaPhanQuyen, MaNguoiDung, QuyenHan, NgayCap) VALUES ('PQ5', 'NV02', N'Nhân viên sửa chữa', '2024-01-01');
GO

-- 6. SinhVien (Sinh ra ngẫu nhiên 50 sinh viên)
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024001', N'Phan Ngọc Trang', '2003-12-20', N'Nữ', N'Đồng Nai', '0282003433887', '0934905215', N'Luật', 1, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024002', N'Bùi Trọng Tài', '2003-10-01', N'Nam', N'Bình Dương', '0152004927892', '0967613820', N'Ngoại Ngữ', 3, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024003', N'Phan Diễm Hoa', '2003-06-06', N'Nữ', N'Nghệ An', '0522000470801', '0918745289', N'Y Dược', 1, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024004', N'Nguyễn Thị My', '2005-09-15', N'Nữ', N'Bình Dương', '0922005304915', '0972108984', N'Ngoại Ngữ', 2, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024005', N'Lê Bích Mai', '2003-02-04', N'Nữ', N'Huế', '0392005348147', '0973170567', N'Sư Phạm', 1, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024006', N'Nguyễn Quang Phúc', '2004-03-15', N'Nam', N'Bình Dương', '0622000371336', '0940825755', N'Y Dược', 4, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024007', N'Huỳnh Diễm Trang', '2002-02-11', N'Nữ', N'Cần Thơ', '0582005152146', '0969721039', N'CNTT', 4, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024008', N'Đặng Ngọc Mai', '2002-05-26', N'Nữ', N'Cần Thơ', '0742002379317', '0917147283', N'Y Dược', 3, N'Con thương binh');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024009', N'Võ Văn Anh', '2002-09-10', N'Nam', N'Cần Thơ', '0632001818707', '0920011808', N'Luật', 2, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024010', N'Đặng Văn Tài', '2003-01-28', N'Nam', N'Đà Nẵng', '0512000930978', '0935618225', N'Luật', 4, N'Hộ nghèo');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024011', N'Nguyễn Đức Tài', '2002-09-28', N'Nam', N'Huế', '0532002213822', '0900685164', N'Y Dược', 3, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024012', N'Bùi Thị Thảo', '2004-11-03', N'Nữ', N'Bình Dương', '0172003391662', '0900908906', N'Ngoại Ngữ', 3, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024013', N'Trần Thị Linh', '2002-05-17', N'Nữ', N'Thanh Hóa', '0982002992953', '0946395041', N'Ngoại Ngữ', 2, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024014', N'Trần Phương Trang', '2004-01-10', N'Nữ', N'TP.HCM', '0872000426724', '0906492552', N'Sư Phạm', 1, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024015', N'Võ Thị Thảo', '2002-08-24', N'Nữ', N'Bình Dương', '0352003313792', '0930462767', N'Ngoại Ngữ', 4, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024016', N'Nguyễn Thị Hà', '2002-08-04', N'Nữ', N'TP.HCM', '0612003149359', '0946264000', N'Kinh Tế', 3, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024017', N'Hoàng Hữu Anh', '2005-04-01', N'Nam', N'Thanh Hóa', '0332002547084', '0951593285', N'Kinh Tế', 1, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024018', N'Vũ Ngọc Nhung', '2003-01-02', N'Nữ', N'Nghệ An', '0482004424333', '0995919455', N'Y Dược', 4, N'Hộ nghèo');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024019', N'Trần Diễm Hoa', '2003-12-02', N'Nữ', N'Cần Thơ', '0422004544487', '0942578872', N'Kinh Tế', 3, N'Con thương binh');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024020', N'Phạm Quang Hùng', '2003-01-07', N'Nam', N'Huế', '0912002161267', '0965136255', N'Ngoại Ngữ', 3, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024021', N'Trần Thị Nhung', '2002-01-12', N'Nữ', N'Thanh Hóa', '0582000527599', '0930790436', N'Xây Dựng', 2, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024022', N'Võ Bích Vy', '2003-09-10', N'Nữ', N'Hà Nội', '0542005528526', '0918495608', N'Xây Dựng', 1, N'Hộ nghèo');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024023', N'Nguyễn Hữu Hùng', '2003-04-25', N'Nam', N'TP.HCM', '0222004533287', '0926309830', N'Sư Phạm', 1, N'Con thương binh');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024024', N'Bùi Đức Dũng', '2005-12-28', N'Nam', N'Nghệ An', '0202005884427', '0907090895', N'Luật', 1, N'Hộ nghèo');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024025', N'Võ Bích Hoa', '2005-08-08', N'Nữ', N'Bình Dương', '0722000613073', '0990924590', N'Sư Phạm', 3, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024026', N'Bùi Bích Hoa', '2003-07-07', N'Nữ', N'Đà Nẵng', '0172004663694', '0949971271', N'Sư Phạm', 1, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024027', N'Trần Bá Phát', '2003-06-21', N'Nam', N'Nghệ An', '0832000253581', '0938319282', N'Luật', 3, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024028', N'Võ Phương Linh', '2002-03-17', N'Nữ', N'Đồng Nai', '0402001890127', '0984339459', N'Y Dược', 4, N'Hộ nghèo');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024029', N'Võ Đức Phúc', '2002-09-13', N'Nam', N'Thanh Hóa', '0732003522905', '0927579178', N'Y Dược', 1, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024030', N'Phạm Bá Tuấn', '2003-06-13', N'Nam', N'Cần Thơ', '0572001383379', '0969480280', N'Xây Dựng', 2, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024031', N'Đặng Ngọc Thảo', '2003-12-27', N'Nữ', N'Thanh Hóa', '0792001605012', '0970148727', N'Kinh Tế', 2, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024032', N'Phạm Hữu Phát', '2002-01-18', N'Nam', N'TP.HCM', '0402003270886', '0954804686', N'Kinh Tế', 2, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024033', N'Võ Phương Thảo', '2002-12-07', N'Nữ', N'Đồng Nai', '0452003555577', '0969509671', N'Y Dược', 2, N'Con thương binh');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024034', N'Võ Phương Hà', '2003-09-16', N'Nữ', N'Thanh Hóa', '0642004794488', '0994376523', N'Luật', 2, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024035', N'Lê Phương Thủy', '2002-08-13', N'Nữ', N'Nghệ An', '0862000870728', '0927868129', N'Y Dược', 2, N'Con thương binh');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024036', N'Đặng Trọng Anh', '2003-10-01', N'Nam', N'TP.HCM', '0182000445943', '0929026867', N'Luật', 3, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024037', N'Võ Bích Thủy', '2003-04-02', N'Nữ', N'Hải Phòng', '0762000911853', '0978615003', N'Sư Phạm', 4, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024038', N'Võ Hữu Tuấn', '2005-01-21', N'Nam', N'Hải Phòng', '0742000357360', '0905211257', N'Luật', 3, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024039', N'Phạm Đức Bình', '2004-11-05', N'Nam', N'TP.HCM', '0922001857348', '0994598466', N'Xây Dựng', 4, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024040', N'Đặng Bích Trang', '2002-11-17', N'Nữ', N'Bình Dương', '0912000741370', '0949650991', N'Ngoại Ngữ', 3, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024041', N'Phạm Diễm Hà', '2004-02-20', N'Nữ', N'TP.HCM', '0532001497658', '0970641832', N'CNTT', 3, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024042', N'Lê Bích Nhung', '2002-04-01', N'Nữ', N'Nghệ An', '0812000331956', '0936947884', N'Ngoại Ngữ', 1, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024043', N'Trần Trọng Tuấn', '2005-09-28', N'Nam', N'Bình Dương', '0792002334286', '0978125431', N'Y Dược', 1, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024044', N'Đặng Văn Bình', '2004-02-28', N'Nam', N'Huế', '0222001856302', '0986454642', N'Y Dược', 4, N'Con thương binh');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024045', N'Vũ Thị Trang', '2003-11-03', N'Nữ', N'Đồng Nai', '0622002519516', '0942679833', N'Kinh Tế', 2, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024046', N'Huỳnh Diễm Vy', '2002-01-06', N'Nữ', N'Cần Thơ', '0532003253671', '0962483375', N'Luật', 2, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024047', N'Phan Trọng Tài', '2005-10-25', N'Nam', N'Hà Nội', '0582003940397', '0968196297', N'Xây Dựng', 1, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024048', N'Nguyễn Diễm Vy', '2005-01-10', N'Nữ', N'Hà Nội', '0302002303677', '0987311411', N'Xây Dựng', 3, N'Không');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024049', N'Lê Bích Vy', '2002-08-15', N'Nữ', N'Thanh Hóa', '0972000435098', '0989154653', N'Y Dược', 4, N'Con thương binh');
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, QueQuan, CCCD, SDT, Khoa, NamHoc, DienUuTien) VALUES ('SV2024050', N'Hoàng Thị Lan', '2005-05-25', N'Nữ', N'Cần Thơ', '0502004278324', '0961250121', N'Ngoại Ngữ', 2, N'Không');
GO

