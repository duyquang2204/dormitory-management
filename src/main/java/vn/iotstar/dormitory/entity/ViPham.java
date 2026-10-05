package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ViPham")
public class ViPham {

    @Id
    private String maViPham;

    @ManyToOne
    @JoinColumn(name = "maSV")
    private SinhVien sinhVien;

    private java.time.LocalDate ngayLapBienBan;

    private java.time.LocalDate ngayViPham;

    private String noiDung;

    private String diaDiem;

    private String hinhThucXuLy;

    private String trangThaiXuLy;

    public ViPham() {}

    public String getMaViPham() {
        return maViPham;
    }

    public void setMaViPham(String maViPham) {
        this.maViPham = maViPham;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public java.time.LocalDate getNgayLapBienBan() {
        return ngayLapBienBan;
    }

    public void setNgayLapBienBan(java.time.LocalDate ngayLapBienBan) {
        this.ngayLapBienBan = ngayLapBienBan;
    }

    public java.time.LocalDate getNgayViPham() {
        return ngayViPham;
    }

    public void setNgayViPham(java.time.LocalDate ngayViPham) {
        this.ngayViPham = ngayViPham;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public String getHinhThucXuLy() {
        return hinhThucXuLy;
    }

    public void setHinhThucXuLy(String hinhThucXuLy) {
        this.hinhThucXuLy = hinhThucXuLy;
    }

    public String getTrangThaiXuLy() {
        return trangThaiXuLy;
    }

    public void setTrangThaiXuLy(String trangThaiXuLy) {
        this.trangThaiXuLy = trangThaiXuLy;
    }

}
