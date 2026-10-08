package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "KetQuaSuaChua")
public class KetQuaSuaChua {

    @Id
    private String maPhanCong;

    @OneToOne
    @JoinColumn(name = "maYeuCau")
    private YeuCauSuaChua yeuCauSuaChua;

    @ManyToMany
    @JoinTable(
        name = "KetQua_NhanVien",
        joinColumns = @JoinColumn(name = "maPhanCong"),
        inverseJoinColumns = @JoinColumn(name = "maNVSC")
    )
    private List<NguoiDung> danhSachNhanVien = new java.util.ArrayList<>();

    private java.time.LocalDate ngayTiepNhan;

    private java.time.LocalDate ngayHoanThanh;

    private String noiDungXuLy;

    private String ketQua;

    private String ghiChu;

    private String hinhAnhMinhChung;

    public KetQuaSuaChua() {}

    public String getMaPhanCong() {
        return maPhanCong;
    }

    public void setMaPhanCong(String maPhanCong) {
        this.maPhanCong = maPhanCong;
    }

    public YeuCauSuaChua getYeuCauSuaChua() {
        return yeuCauSuaChua;
    }

    public void setYeuCauSuaChua(YeuCauSuaChua yeuCauSuaChua) {
        this.yeuCauSuaChua = yeuCauSuaChua;
    }

    public List<NguoiDung> getDanhSachNhanVien() {
        return danhSachNhanVien;
    }

    public void setDanhSachNhanVien(List<NguoiDung> danhSachNhanVien) {
        this.danhSachNhanVien = danhSachNhanVien;
    }

    public java.time.LocalDate getNgayTiepNhan() {
        return ngayTiepNhan;
    }

    public void setNgayTiepNhan(java.time.LocalDate ngayTiepNhan) {
        this.ngayTiepNhan = ngayTiepNhan;
    }

    public java.time.LocalDate getNgayHoanThanh() {
        return ngayHoanThanh;
    }

    public void setNgayHoanThanh(java.time.LocalDate ngayHoanThanh) {
        this.ngayHoanThanh = ngayHoanThanh;
    }

    public String getNoiDungXuLy() {
        return noiDungXuLy;
    }

    public void setNoiDungXuLy(String noiDungXuLy) {
        this.noiDungXuLy = noiDungXuLy;
    }

    public String getKetQua() {
        return ketQua;
    }

    public void setKetQua(String ketQua) {
        this.ketQua = ketQua;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getHinhAnhMinhChung() {
        return hinhAnhMinhChung;
    }

    public void setHinhAnhMinhChung(String hinhAnhMinhChung) {
        this.hinhAnhMinhChung = hinhAnhMinhChung;
    }
}
