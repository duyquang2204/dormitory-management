package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "DangKyKTX")
public class DangKyKTX {

    @Id
    private String maDangKy;

    @ManyToOne
    @JoinColumn(name = "maSV", nullable = true)
    private SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "maLoaiPhong")
    private LoaiPhong loaiPhong;

    private LocalDate ngayDangKy;

    private String trangThai; // "Chờ duyệt", "Đã duyệt", "Từ chối"

    @Column(columnDefinition = "TEXT")
    private String ghiChu;

    // Các trường hồ sơ sinh viên nộp online
    private String hoTen;

    private LocalDate ngaySinh;

    private String gioiTinh;

    private String cccd;

    private String sdt;

    private String email;

    private String queQuan;

    private String truongDaiHoc; // Tên trường ĐH đang học

    private String khoa; // Khoa / Chuyên ngành

    private Integer namHoc; // Năm thứ mấy

    private String dienUuTien; // Không ưu tiên, Con TB-LS, Hộ nghèo, Vùng sâu xa...

    private String anhTheSinhVien; // URL Cloudinary ảnh thẻ SV hoặc giấy báo trúng tuyển

    private String anhCccd; // URL Cloudinary ảnh CCCD

    private String anhMinhChungUuTien; // URL Cloudinary giấy tờ chứng minh ưu tiên

    @Column(columnDefinition = "TEXT")
    private String lyDoTuChoi; // Lý do từ chối nếu BQL không duyệt

    public DangKyKTX() {}

    public String getMaDangKy() {
        return maDangKy;
    }

    public void setMaDangKy(String maDangKy) {
        this.maDangKy = maDangKy;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public LoaiPhong getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(LoaiPhong loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public LocalDate getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(LocalDate ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public String getTruongDaiHoc() {
        return truongDaiHoc;
    }

    public void setTruongDaiHoc(String truongDaiHoc) {
        this.truongDaiHoc = truongDaiHoc;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public Integer getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(Integer namHoc) {
        this.namHoc = namHoc;
    }

    public String getDienUuTien() {
        return dienUuTien;
    }

    public void setDienUuTien(String dienUuTien) {
        this.dienUuTien = dienUuTien;
    }

    public String getAnhTheSinhVien() {
        return anhTheSinhVien;
    }

    public void setAnhTheSinhVien(String anhTheSinhVien) {
        this.anhTheSinhVien = anhTheSinhVien;
    }

    public String getAnhCccd() {
        return anhCccd;
    }

    public void setAnhCccd(String anhCccd) {
        this.anhCccd = anhCccd;
    }

    public String getAnhMinhChungUuTien() {
        return anhMinhChungUuTien;
    }

    public void setAnhMinhChungUuTien(String anhMinhChungUuTien) {
        this.anhMinhChungUuTien = anhMinhChungUuTien;
    }

    public String getLyDoTuChoi() {
        return lyDoTuChoi;
    }

    public void setLyDoTuChoi(String lyDoTuChoi) {
        this.lyDoTuChoi = lyDoTuChoi;
    }
}
