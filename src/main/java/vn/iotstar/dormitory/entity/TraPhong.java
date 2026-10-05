package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "TraPhong")
public class TraPhong {

    @Id
    private String maTraPhong;

    @ManyToOne
    @JoinColumn(name = "maPhanPhong")
    private PhanPhong phanPhong;

    private String lyDo;

    private java.time.LocalDate ngayYeuCau;

    private java.time.LocalDate ngayTra;

    private String ketQuaKiemKe;

    private String trangThai;

    public TraPhong() {}

    public String getMaTraPhong() {
        return maTraPhong;
    }

    public void setMaTraPhong(String maTraPhong) {
        this.maTraPhong = maTraPhong;
    }

    public PhanPhong getPhanPhong() {
        return phanPhong;
    }

    public void setPhanPhong(PhanPhong phanPhong) {
        this.phanPhong = phanPhong;
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

    public java.time.LocalDate getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(java.time.LocalDate ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getKetQuaKiemKe() {
        return ketQuaKiemKe;
    }

    public void setKetQuaKiemKe(String ketQuaKiemKe) {
        this.ketQuaKiemKe = ketQuaKiemKe;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

}
