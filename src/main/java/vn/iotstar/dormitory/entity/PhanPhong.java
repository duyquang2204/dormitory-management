package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PhanPhong")
public class PhanPhong {

    @Id
    private String maPhanPhong;

    @ManyToOne
    @JoinColumn(name = "maSV")
    private SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "maPhong")
    private Phong phong;

    @OneToOne
    @JoinColumn(name = "maDangKy")
    private DangKyKTX dangKyKTX;

    private java.time.LocalDate ngayBatDau;

    private java.time.LocalDate ngayKetThuc;

    private String trangThai;

    public PhanPhong() {}

    public String getMaPhanPhong() {
        return maPhanPhong;
    }

    public void setMaPhanPhong(String maPhanPhong) {
        this.maPhanPhong = maPhanPhong;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public Phong getPhong() {
        return phong;
    }

    public void setPhong(Phong phong) {
        this.phong = phong;
    }

    public DangKyKTX getDangKyKTX() {
        return dangKyKTX;
    }

    public void setDangKyKTX(DangKyKTX dangKyKTX) {
        this.dangKyKTX = dangKyKTX;
    }

    public java.time.LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(java.time.LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public java.time.LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(java.time.LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

}
