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
}
