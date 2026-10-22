package vn.iotstar.dormitory.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public class HoaDonDTO {
    private String maHoaDon;
    
    @NotNull(message = "Mã phòng không được để trống")
    private String maPhong;
    
    @NotNull(message = "Tháng không được để trống")
    @Min(value = 1, message = "Tháng không hợp lệ")
    private Integer thang;
    
    @NotNull(message = "Năm không được để trống")
    private Integer nam;
    
    private Double chiSoDienCu;
    
    @NotNull(message = "Chỉ số điện mới không được để trống")
    @Min(value = 0, message = "Chỉ số không hợp lệ")
    private Double chiSoDienMoi;
    
    private Double chiSoNuocCu;
    
    @NotNull(message = "Chỉ số nước mới không được để trống")
    @Min(value = 0, message = "Chỉ số không hợp lệ")
    private Double chiSoNuocMoi;
    
    private String trangThai;

    private Double donGiaDien;
    private Double donGiaNuoc;
    private String loaiHoaDon;
    private java.time.LocalDate hanThanhToan;
    private Double tienPhong;
    private String ghiChu;

    public HoaDonDTO() {}

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
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

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Double getDonGiaDien() {
        return donGiaDien;
    }

    public void setDonGiaDien(Double donGiaDien) {
        this.donGiaDien = donGiaDien;
    }

    public Double getDonGiaNuoc() {
        return donGiaNuoc;
    }

    public void setDonGiaNuoc(Double donGiaNuoc) {
        this.donGiaNuoc = donGiaNuoc;
    }

    public String getLoaiHoaDon() {
        return loaiHoaDon;
    }

    public void setLoaiHoaDon(String loaiHoaDon) {
        this.loaiHoaDon = loaiHoaDon;
    }

    public java.time.LocalDate getHanThanhToan() {
        return hanThanhToan;
    }

    public void setHanThanhToan(java.time.LocalDate hanThanhToan) {
        this.hanThanhToan = hanThanhToan;
    }

    public Double getTienPhong() {
        return tienPhong;
    }

    public void setTienPhong(Double tienPhong) {
        this.tienPhong = tienPhong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
