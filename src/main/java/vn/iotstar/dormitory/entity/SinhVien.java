package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SinhVien")
public class SinhVien {

    @Id
    private String maSV;

    private String hoTen;

    private java.time.LocalDate ngaySinh;

    private String gioiTinh;

    private String queQuan;

    private String cccd;

    private String sdt;

    private String email;

    private String khoa;

    private Integer namHoc;

    private String dienUuTien;

    private String truongDaiHoc;

    private String anhTheSinhVien;

    private String trangThai = "Hoạt động"; // Hoạt động, Chưa kích hoạt

    private String diaChi;

    private String sdtPhuHuynh;

    private String hoTenPhuHuynh;

    private String matKhau;

    public SinhVien() {}

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public java.time.LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(java.time.LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
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

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTruongDaiHoc() {
        return truongDaiHoc;
    }

    public void setTruongDaiHoc(String truongDaiHoc) {
        this.truongDaiHoc = truongDaiHoc;
    }

    public String getAnhTheSinhVien() {
        return anhTheSinhVien;
    }

    public void setAnhTheSinhVien(String anhTheSinhVien) {
        this.anhTheSinhVien = anhTheSinhVien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdtPhuHuynh() {
        return sdtPhuHuynh;
    }

    public void setSdtPhuHuynh(String sdtPhuHuynh) {
        this.sdtPhuHuynh = sdtPhuHuynh;
    }

    public String getHoTenPhuHuynh() {
        return hoTenPhuHuynh;
    }

    public void setHoTenPhuHuynh(String hoTenPhuHuynh) {
        this.hoTenPhuHuynh = hoTenPhuHuynh;
    }

}
