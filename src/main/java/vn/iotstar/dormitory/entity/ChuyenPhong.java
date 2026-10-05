package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ChuyenPhong")
public class ChuyenPhong {

    @Id
    private String maChuyenPhong;

    @ManyToOne
    @JoinColumn(name = "maPhanPhong")
    private PhanPhong phanPhong;

    @ManyToOne
    @JoinColumn(name = "maPhongMoi")
    private Phong phongMoi;

    private String lyDo;

    private java.time.LocalDate ngayYeuCau;

    private java.time.LocalDate ngayXuLy;

    private String trangThai;

    public ChuyenPhong() {}

    public String getMaChuyenPhong() {
        return maChuyenPhong;
    }

    public void setMaChuyenPhong(String maChuyenPhong) {
        this.maChuyenPhong = maChuyenPhong;
    }

    public PhanPhong getPhanPhong() {
        return phanPhong;
    }

    public void setPhanPhong(PhanPhong phanPhong) {
        this.phanPhong = phanPhong;
    }

    public Phong getPhongMoi() {
        return phongMoi;
    }

    public void setPhongMoi(Phong phongMoi) {
        this.phongMoi = phongMoi;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public java.time.LocalDate getNgayYeuCau() {
        return ngayYeuCau;
    }

    public void setNgayYeuCau(java.time.LocalDate ngayYeuCau) {
        this.ngayYeuCau = ngayYeuCau;
    }

    public java.time.LocalDate getNgayXuLy() {
        return ngayXuLy;
    }

    public void setNgayXuLy(java.time.LocalDate ngayXuLy) {
        this.ngayXuLy = ngayXuLy;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

}
