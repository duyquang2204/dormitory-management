package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "HoaDon")
public class HoaDon {

    @Id
    private String maHoaDon;

    @ManyToOne
    @JoinColumn(name = "maPhong")
    private Phong phong;

    @ManyToOne
    @JoinColumn(name = "maSV")
    private SinhVien sinhVien;

    private Integer thang;
    private Integer nam;

    private Double chiSoDienCu;
    private Double chiSoDienMoi;
    
    private Double chiSoNuocCu;
    private Double chiSoNuocMoi;

    private Double tienDien;
    private Double tienNuoc;

    private Double tienPhong;
    private Double tongTien;

    // Trạng thái: "Chưa thanh toán", "Chờ duyệt", "Đã thanh toán"
    private String trangThai;

    private String minhChungThanhToan; // Hình ảnh biên lai
    
    private String phuongThucThanhToan; // "Tiền mặt" hoặc "Chuyển khoản"

    private LocalDate ngayTao;

    public HoaDon() {
    }
    
    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public Phong getPhong() {
        return phong;
    }

    public void setPhong(Phong phong) {
        this.phong = phong;
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

    public Double getChiSoDienCu() {
        return chiSoDienCu;
    }

    public void setChiSoDienCu(Double chiSoDienCu) {
        this.chiSoDienCu = chiSoDienCu;
    }

    public Double getChiSoDienMoi() {
        return chiSoDienMoi;
    }

    public void setChiSoDienMoi(Double chiSoDienMoi) {
        this.chiSoDienMoi = chiSoDienMoi;
    }

    public Double getChiSoNuocCu() {
        return chiSoNuocCu;
    }

    public void setChiSoNuocCu(Double chiSoNuocCu) {
        this.chiSoNuocCu = chiSoNuocCu;
    }

    public Double getChiSoNuocMoi() {
        return chiSoNuocMoi;
    }

    public void setChiSoNuocMoi(Double chiSoNuocMoi) {
        this.chiSoNuocMoi = chiSoNuocMoi;
    }

    public Double getTienPhong() {
        return tienPhong;
    }

    public void setTienPhong(Double tienPhong) {
        this.tienPhong = tienPhong;
    }

    public Double getTongTien() {
        return tongTien;
    }

    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getMinhChungThanhToan() {
        return minhChungThanhToan;
    }

    public void setMinhChungThanhToan(String minhChungThanhToan) {
        this.minhChungThanhToan = minhChungThanhToan;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public Double getTienDien() {
        return tienDien;
    }

    public void setTienDien(Double tienDien) {
        this.tienDien = tienDien;
    }

    public Double getTienNuoc() {
        return tienNuoc;
    }

    public void setTienNuoc(Double tienNuoc) {
        this.tienNuoc = tienNuoc;
    }
}
