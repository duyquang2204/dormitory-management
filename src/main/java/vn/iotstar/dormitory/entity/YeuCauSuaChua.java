package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "YeuCauSuaChua")
public class YeuCauSuaChua {

    @Id
    private String maYeuCau;

    @ManyToOne
    @JoinColumn(name = "maPhong")
    private Phong phong;

    @ManyToOne
    @JoinColumn(name = "maSV")
    private SinhVien sinhVien;

    private java.time.LocalDateTime ngayGui;

    private String noiDungSuCo;

    private String hinhAnh;

    private String trangThai;

    @OneToOne(mappedBy = "yeuCauSuaChua")
    private KetQuaSuaChua ketQuaSuaChua;

    public YeuCauSuaChua() {}

    public String getMaYeuCau() {
        return maYeuCau;
    }

    public void setMaYeuCau(String maYeuCau) {
        this.maYeuCau = maYeuCau;
    }

    public Phong getPhong() {
        return phong;
    }

    public void setPhong(Phong phong) {
        this.phong = phong;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public java.time.LocalDateTime getNgayGui() {
        return ngayGui;
    }

    public void setNgayGui(java.time.LocalDateTime ngayGui) {
        this.ngayGui = ngayGui;
    }

    public String getNoiDungSuCo() {
        return noiDungSuCo;
    }

    public void setNoiDungSuCo(String noiDungSuCo) {
        this.noiDungSuCo = noiDungSuCo;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public KetQuaSuaChua getKetQuaSuaChua() {
        return ketQuaSuaChua;
    }

    public void setKetQuaSuaChua(KetQuaSuaChua ketQuaSuaChua) {
        this.ketQuaSuaChua = ketQuaSuaChua;
    }
}
