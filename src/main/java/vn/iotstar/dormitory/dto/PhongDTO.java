package vn.iotstar.dormitory.dto;

import jakarta.validation.constraints.NotBlank;

public class PhongDTO {
    
    private String maPhong;
    
    @NotBlank(message = "Số phòng không được để trống")
    private String soPhong;
    
    @NotBlank(message = "Khu không được để trống")
    private String maKhu;
    
    @NotBlank(message = "Loại phòng không được để trống")
    private String maLoaiPhong;
    
    private String trangThai;

    // Getters and Setters
    public String getMaPhong() { return maPhong; }
    public void setMaPhong(String maPhong) { this.maPhong = maPhong; }

    public String getSoPhong() { return soPhong; }
    public void setSoPhong(String soPhong) { this.soPhong = soPhong; }

    public String getMaKhu() { return maKhu; }
    public void setMaKhu(String maKhu) { this.maKhu = maKhu; }

    public String getMaLoaiPhong() { return maLoaiPhong; }
    public void setMaLoaiPhong(String maLoaiPhong) { this.maLoaiPhong = maLoaiPhong; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
