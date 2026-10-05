package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Phong")
public class Phong {

    @Id
    private String maPhong;

    private String soPhong;

    @ManyToOne
    @JoinColumn(name = "maKhu")
    private Khu khu;

    @ManyToOne
    @JoinColumn(name = "maLoaiPhong")
    private LoaiPhong loaiPhong;

    private String trangThai;

    public Phong() {}

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
    }

    public Khu getKhu() {
        return khu;
    }

    public void setKhu(Khu khu) {
        this.khu = khu;
    }

    public LoaiPhong getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(LoaiPhong loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

}
