package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "BaoCaoThongKe")
public class BaoCaoThongKe {

    @Id
    private String maBaoCao;

    @ManyToOne
    @JoinColumn(name = "maKhu")
    private Khu khu;

    private Integer thang;

    private Integer nam;

    private Integer soLuongSV;

    private Integer soLuongViPham;

    private Integer soLuongSuaChua;

    private java.time.LocalDate ngayLap;

    @ManyToOne
    @JoinColumn(name = "maNguoiLap")
    private NguoiDung nguoiLap;

    private String ghiChu;

    public BaoCaoThongKe() {}

    public String getMaBaoCao() {
        return maBaoCao;
    }

    public void setMaBaoCao(String maBaoCao) {
        this.maBaoCao = maBaoCao;
    }

    public Khu getKhu() {
        return khu;
    }

    public void setKhu(Khu khu) {
        this.khu = khu;
    }

    public Integer getThang() {
        return thang;
    }

    public void setThang(Integer thang) {
        this.thang = thang;
    }

    public Integer getNam() {
        return nam;
    }

    public void setNam(Integer nam) {
        this.nam = nam;
    }

    public Integer getSoLuongSV() {
        return soLuongSV;
    }

    public void setSoLuongSV(Integer soLuongSV) {
        this.soLuongSV = soLuongSV;
    }

    public Integer getSoLuongViPham() {
        return soLuongViPham;
    }

    public void setSoLuongViPham(Integer soLuongViPham) {
        this.soLuongViPham = soLuongViPham;
    }

    public Integer getSoLuongSuaChua() {
        return soLuongSuaChua;
    }

    public void setSoLuongSuaChua(Integer soLuongSuaChua) {
        this.soLuongSuaChua = soLuongSuaChua;
    }

    public java.time.LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(java.time.LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public NguoiDung getNguoiLap() {
        return nguoiLap;
    }

    public void setNguoiLap(NguoiDung nguoiLap) {
        this.nguoiLap = nguoiLap;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

}
