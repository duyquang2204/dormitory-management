package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "DangKyKTX")
public class DangKyKTX {

    @Id
    private String maDangKy;

    @ManyToOne
    @JoinColumn(name = "maSV")
    private SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "maLoaiPhong")
    private LoaiPhong loaiPhong;

    private java.time.LocalDate ngayDangKy;

    private String trangThai;

    private String ghiChu;

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

    public java.time.LocalDate getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(java.time.LocalDate ngayDangKy) {
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

}
