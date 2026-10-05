package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "HopDong")
public class HopDong {

    @Id
    private String maHopDong;

    @ManyToOne
    @JoinColumn(name = "maPhanPhong")
    private PhanPhong phanPhong;

    private String dieuKhoan;

    private String trangThai;

    private String yeuCauCuaSV;

    private java.time.LocalDate ngayLap;
    private java.time.LocalDate ngayBatDau;
    private java.time.LocalDate ngayKetThuc;
    private Double giaTien;

    @ManyToOne
    @JoinColumn(name = "maNhanVienLap")
    private NguoiDung nhanVienLap;

    public HopDong() {}

    public java.time.LocalDate getNgayLap() { return ngayLap; }
    public void setNgayLap(java.time.LocalDate ngayLap) { this.ngayLap = ngayLap; }

    public java.time.LocalDate getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(java.time.LocalDate ngayBatDau) { this.ngayBatDau = ngayBatDau; }

    public java.time.LocalDate getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(java.time.LocalDate ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }

    public Double getGiaTien() { return giaTien; }
    public void setGiaTien(Double giaTien) { this.giaTien = giaTien; }

    public NguoiDung getNhanVienLap() { return nhanVienLap; }
    public void setNhanVienLap(NguoiDung nhanVienLap) { this.nhanVienLap = nhanVienLap; }

    public String getMaHopDong() {
        return maHopDong;
    }

    public void setMaHopDong(String maHopDong) {
        this.maHopDong = maHopDong;
    }

    public PhanPhong getPhanPhong() {
        return phanPhong;
    }

    public void setPhanPhong(PhanPhong phanPhong) {
        this.phanPhong = phanPhong;
    }

    public String getDieuKhoan() {
        return dieuKhoan;
    }

    public void setDieuKhoan(String dieuKhoan) {
        this.dieuKhoan = dieuKhoan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getYeuCauCuaSV() {
        return yeuCauCuaSV;
    }

    public void setYeuCauCuaSV(String yeuCauCuaSV) {
        this.yeuCauCuaSV = yeuCauCuaSV;
    }

    @Transient
    public boolean isSapHetHan() {
        if ("Còn hạn".equals(this.trangThai) && this.ngayKetThuc != null) {
            long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(java.time.LocalDate.now(), this.ngayKetThuc);
            return daysBetween >= 0 && daysBetween <= 30;
        }
        return false;
    }
}
