package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;
import java.util.List;

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

}
