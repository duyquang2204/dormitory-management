package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "GiaHanHopDong")
public class GiaHanHopDong {

    @Id
    private String maGiaHan;

    @ManyToOne
    @JoinColumn(name = "maHopDong")
    private HopDong hopDong;

    private java.time.LocalDate ngayYeuCau;

    private java.time.LocalDate ngayBatDauMoi;

    private java.time.LocalDate ngayKetThucMoi;

    private String trangThai;

    public GiaHanHopDong() {}

    public String getMaGiaHan() {
        return maGiaHan;
    }

    public void setMaGiaHan(String maGiaHan) {
        this.maGiaHan = maGiaHan;
    }

    public HopDong getHopDong() {
        return hopDong;
    }

    public void setHopDong(HopDong hopDong) {
        this.hopDong = hopDong;
    }

    public java.time.LocalDate getNgayYeuCau() {
        return ngayYeuCau;
    }

    public void setNgayYeuCau(java.time.LocalDate ngayYeuCau) {
        this.ngayYeuCau = ngayYeuCau;
    }

    public java.time.LocalDate getNgayBatDauMoi() {
        return ngayBatDauMoi;
    }

    public void setNgayBatDauMoi(java.time.LocalDate ngayBatDauMoi) {
        this.ngayBatDauMoi = ngayBatDauMoi;
    }

    public java.time.LocalDate getNgayKetThucMoi() {
        return ngayKetThucMoi;
    }

    public void setNgayKetThucMoi(java.time.LocalDate ngayKetThucMoi) {
        this.ngayKetThucMoi = ngayKetThucMoi;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

}
