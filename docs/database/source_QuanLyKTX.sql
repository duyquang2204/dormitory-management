-- Create Database
CREATE DATABASE QuanLyKyTucXa;
GO

USE QuanLyKyTucXa;
GO


CREATE TABLE SinhVien (
    MaSV VARCHAR(20) NOT NULL PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    NgaySinh DATE,
    GioiTinh NVARCHAR(10) CHECK (GioiTinh IN (N'Nam', N'Nữ', N'Khác')),
    QueQuan NVARCHAR(200),
    CCCD VARCHAR(20) NOT NULL UNIQUE,
    SDT VARCHAR(15),
    Khoa NVARCHAR(100),
    NamHoc INT CHECK (NamHoc >= 1),
    DienUuTien NVARCHAR(100)
);
GO


CREATE TABLE Khu (
    MaKhu VARCHAR(20) NOT NULL PRIMARY KEY,
    TenKhu NVARCHAR(100) NOT NULL UNIQUE,
    MoTa NVARCHAR(MAX)
);
GO


CREATE TABLE LoaiPhong (
    MaLoaiPhong VARCHAR(20) NOT NULL PRIMARY KEY,
    TenLoaiPhong NVARCHAR(100) NOT NULL UNIQUE,
    SoNguoiToiDa INT CHECK (SoNguoiToiDa > 0),
    DonGia DECIMAL(18, 2) CHECK (DonGia >= 0),
    MoTa NVARCHAR(MAX)
);
GO


CREATE TABLE NguoiDung (
    MaNguoiDung VARCHAR(20) NOT NULL PRIMARY KEY,
    TenDangNhap VARCHAR(50) NOT NULL UNIQUE,
    MatKhau VARCHAR(255) NOT NULL,
    HoTen NVARCHAR(100) NOT NULL,
    SDT VARCHAR(15),
    ChucVu NVARCHAR(50) CHECK (ChucVu IN (N'Quản sinh', N'Nhân viên sửa chữa', N'Quản trị viên')),
    TrangThai NVARCHAR(20) CHECK (TrangThai IN (N'Hoạt động', N'Bị khóa'))
);
GO


CREATE TABLE Phong (
    MaPhong VARCHAR(20) NOT NULL PRIMARY KEY,
    SoPhong VARCHAR(20) NOT NULL,
    MaKhu VARCHAR(20) NOT NULL,
    MaLoaiPhong VARCHAR(20) NOT NULL,
    TrangThai NVARCHAR(20) CHECK (TrangThai IN (N'Hoạt động', N'Bảo trì', N'Đóng')),
    FOREIGN KEY (MaKhu) REFERENCES Khu(MaKhu) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (MaLoaiPhong) REFERENCES LoaiPhong(MaLoaiPhong) ON DELETE CASCADE ON UPDATE CASCADE
);
GO


CREATE TABLE DangKyKTX (
    MaDangKy VARCHAR(20) NOT NULL PRIMARY KEY,
    MaSV VARCHAR(20) NOT NULL,
    MaLoaiPhong VARCHAR(20) NOT NULL,
    NgayDangKy DATE,
    TrangThai NVARCHAR(30) CHECK (TrangThai IN (N'Chờ duyệt', N'Đã duyệt', N'Từ chối', N'Đã phân phòng')),
    GhiChu NVARCHAR(MAX),
    FOREIGN KEY (MaSV) REFERENCES SinhVien(MaSV) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (MaLoaiPhong) REFERENCES LoaiPhong(MaLoaiPhong) ON DELETE CASCADE ON UPDATE CASCADE
);
GO


CREATE TABLE ViPham (
    MaViPham VARCHAR(20) NOT NULL PRIMARY KEY,
    MaSV VARCHAR(20) NOT NULL,
    NgayLapBienBan DATE,
    NgayViPham DATE,
    NoiDung NVARCHAR(MAX),
    DiaDiem NVARCHAR(200),
    HinhThucXuLy NVARCHAR(50) CHECK (HinhThucXuLy IN (N'Nhắc nhở', N'Cảnh cáo', N'Buộc rời KTX')),
    TrangThaiXuLy NVARCHAR(20) CHECK (TrangThaiXuLy IN (N'Chưa xử lý', N'Đã xử lý')),
    CONSTRAINT CHK_NgayViPham CHECK (NgayViPham <= NgayLapBienBan),
    FOREIGN KEY (MaSV) REFERENCES SinhVien(MaSV) ON DELETE CASCADE ON UPDATE CASCADE
);
GO


CREATE TABLE PhanQuyen (
    MaPhanQuyen VARCHAR(20) NOT NULL PRIMARY KEY,
    MaNguoiDung VARCHAR(20) NOT NULL,
    QuyenHan NVARCHAR(100) NOT NULL,
    NgayCap DATE,
    FOREIGN KEY (MaNguoiDung) REFERENCES NguoiDung(MaNguoiDung) ON DELETE CASCADE ON UPDATE CASCADE
);
GO


CREATE TABLE BaoCaoThongKe (
    MaBaoCao VARCHAR(20) NOT NULL PRIMARY KEY,
    MaKhu VARCHAR(20) NOT NULL,
    Thang INT CHECK (Thang BETWEEN 1 AND 12),
    Nam INT CHECK (Nam >= 2000),
    SoLuongSV INT CHECK (SoLuongSV >= 0),
    SoLuongViPham INT CHECK (SoLuongViPham >= 0),
    SoLuongSuaChua INT CHECK (SoLuongSuaChua >= 0),
    NgayLap DATE,
    MaNguoiLap VARCHAR(20) NOT NULL,
    GhiChu NVARCHAR(MAX),
    FOREIGN KEY (MaKhu) REFERENCES Khu(MaKhu) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (MaNguoiLap) REFERENCES NguoiDung(MaNguoiDung) ON DELETE CASCADE ON UPDATE CASCADE
);
GO


CREATE TABLE PhanPhong (
    MaPhanPhong VARCHAR(20) NOT NULL PRIMARY KEY,
    MaSV VARCHAR(20) NOT NULL,
    MaPhong VARCHAR(20) NOT NULL,
    MaDangKy VARCHAR(20) NOT NULL UNIQUE,
    NgayBatDau DATE,
    NgayKetThuc DATE,
    TrangThai NVARCHAR(30) CHECK (TrangThai IN (N'Đang ở', N'Đã chuyển phòng', N'Đã trả phòng')),
    CONSTRAINT CHK_ThoiGianPhanPhong CHECK (NgayKetThuc >= NgayBatDau),
    FOREIGN KEY (MaSV) REFERENCES SinhVien(MaSV) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (MaDangKy) REFERENCES DangKyKTX(MaDangKy)
);
GO


CREATE TABLE HopDong (
    MaHopDong VARCHAR(20) NOT NULL PRIMARY KEY,
    MaPhanPhong VARCHAR(20) NOT NULL,
    DieuKhoan NVARCHAR(MAX),
    TrangThai NVARCHAR(30) CHECK (TrangThai IN (N'Có hiệu lực', N'Hết hạn', N'Đã thanh lý', N'Đã hủy')),
    FOREIGN KEY (MaPhanPhong) REFERENCES PhanPhong(MaPhanPhong) ON DELETE CASCADE ON UPDATE CASCADE
);
GO


CREATE TABLE GiaHanHopDong (
    MaGiaHan VARCHAR(20) NOT NULL PRIMARY KEY,
    MaHopDong VARCHAR(20) NOT NULL,
    NgayYeuCau DATE,
    NgayBatDauMoi DATE,
    NgayKetThucMoi DATE,
    TrangThai NVARCHAR(20) CHECK (TrangThai IN (N'Chờ duyệt', N'Đã duyệt', N'Từ chối')),
    CONSTRAINT CHK_ThoiGianGiaHan CHECK (NgayKetThucMoi > NgayBatDauMoi),
    FOREIGN KEY (MaHopDong) REFERENCES HopDong(MaHopDong) ON DELETE CASCADE ON UPDATE CASCADE
);
GO


CREATE TABLE ChuyenPhong (
    MaChuyenPhong VARCHAR(20) NOT NULL PRIMARY KEY,
    MaPhanPhong VARCHAR(20) NOT NULL,
    MaPhongMoi VARCHAR(20) NOT NULL,
    LyDo NVARCHAR(MAX),
    NgayYeuCau DATE,
    NgayXuLy DATE,
    TrangThai NVARCHAR(20) CHECK (TrangThai IN (N'Chờ duyệt', N'Đã duyệt', N'Từ chối', N'Hoàn thành')),
    CONSTRAINT CHK_NgayChuyenPhong CHECK (NgayXuLy >= NgayYeuCau),
    FOREIGN KEY (MaPhanPhong) REFERENCES PhanPhong(MaPhanPhong) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (MaPhongMoi) REFERENCES Phong(MaPhong)
);
GO


CREATE TABLE TraPhong (
    MaTraPhong VARCHAR(20) NOT NULL PRIMARY KEY,
    MaPhanPhong VARCHAR(20) NOT NULL,
    LyDo NVARCHAR(MAX),
    NgayYeuCau DATE,
    NgayTra DATE,
    KetQuaKiemKe NVARCHAR(MAX),
    TrangThai NVARCHAR(20) CHECK (TrangThai IN (N'Chờ duyệt', N'Đã duyệt', N'Từ chối', N'Hoàn thành')),
    CONSTRAINT CHK_NgayTraPhong CHECK (NgayTra >= NgayYeuCau),
    FOREIGN KEY (MaPhanPhong) REFERENCES PhanPhong(MaPhanPhong) ON DELETE CASCADE ON UPDATE CASCADE
);
GO


CREATE TABLE YeuCauSuaChua (
    MaYeuCau VARCHAR(20) NOT NULL PRIMARY KEY,
    MaPhong VARCHAR(20) NOT NULL,
    MaSV VARCHAR(20) NOT NULL,
    NgayGui DATETIME,
    NoiDungSuCo NVARCHAR(MAX),
    HinhAnh NVARCHAR(255),
    TrangThai NVARCHAR(30) CHECK (TrangThai IN (N'Chờ tiếp nhận', N'Đã phân công', N'Đang xử lý', N'Hoàn thành', N'Từ chối')),
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (MaSV) REFERENCES SinhVien(MaSV)
);
GO


CREATE TABLE KetQuaSuaChua (
    MaPhanCong VARCHAR(20) NOT NULL PRIMARY KEY,
    MaYeuCau VARCHAR(20) NOT NULL UNIQUE,
    MaNVSC VARCHAR(20) NOT NULL,
    NgayTiepNhan DATE,
    NgayHoanThanh DATE,
    NoiDungXuLy NVARCHAR(MAX),
    KetQua NVARCHAR(50) CHECK (KetQua IN (N'Đã sửa chữa hoàn thành', N'Chưa hoàn thành', N'Không thể sửa chữa')),
    GhiChu NVARCHAR(MAX),
    CONSTRAINT CHK_ThoiGianSuaChua CHECK (NgayHoanThanh >= NgayTiepNhan),
    FOREIGN KEY (MaYeuCau) REFERENCES YeuCauSuaChua(MaYeuCau) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (MaNVSC) REFERENCES NguoiDung(MaNguoiDung)
);
GO


CREATE TABLE TamVang (
    MaTamVang VARCHAR(20) NOT NULL PRIMARY KEY,
    MaSV VARCHAR(20) NOT NULL,
    MaPhong VARCHAR(20) NOT NULL,
    TuNgay DATE,
    DenNgay DATE,
    LyDo NVARCHAR(MAX),
    NgayDangKy DATE,
    TrangThai NVARCHAR(20) CHECK (TrangThai IN (N'Chờ duyệt', N'Đã duyệt', N'Từ chối')),
    CONSTRAINT CHK_ThoiGianTamVang CHECK (DenNgay >= TuNgay),
    FOREIGN KEY (MaSV) REFERENCES SinhVien(MaSV) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong)
);
GO