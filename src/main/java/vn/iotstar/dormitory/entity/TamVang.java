package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "TamVang")
public class TamVang {

    @Id
    private String maTamVang;

    @ManyToOne
    @JoinColumn(name = "maSV")
    private SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "maPhong")
    private Phong phong;

    private java.time.LocalDate tuNgay;

    private java.time.LocalDate denNgay;

    private String lyDo;

    private java.time.LocalDate ngayDangKy;

    private String trangThai;

    public TamVang() {}

    public String getMaTamVang() {
        return maTamVang;
    }

    public void setMaTamVang(String maTamVang) {
        this.maTamVang = maTamVang;
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

    public java.time.LocalDate getTuNgay() {
        return tuNgay;
    }

    public void setTuNgay(java.time.LocalDate tuNgay) {
        this.tuNgay = tuNgay;
    }

    public java.time.LocalDate getDenNgay() {
        return denNgay;
    }

    public void setDenNgay(java.time.LocalDate denNgay) {
        this.denNgay = denNgay;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
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

}
