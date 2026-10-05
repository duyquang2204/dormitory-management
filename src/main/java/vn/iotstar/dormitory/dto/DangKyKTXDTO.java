package vn.iotstar.dormitory.dto;

import jakarta.validation.constraints.NotBlank;

public class DangKyKTXDTO {
    
    private String maDangKy;
    
    @NotBlank(message = "Loại phòng không được để trống")
    private String maLoaiPhong;
    
    private String ghiChu;
    
    // For view representation
    private String tenLoaiPhong;
    private String trangThai;
    private String ngayDangKy;

    // Getters and Setters
    public String getMaDangKy() { return maDangKy; }
    public void setMaDangKy(String maDangKy) { this.maDangKy = maDangKy; }

    public String getMaLoaiPhong() { return maLoaiPhong; }
    public void setMaLoaiPhong(String maLoaiPhong) { this.maLoaiPhong = maLoaiPhong; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public String getTenLoaiPhong() { return tenLoaiPhong; }
    public void setTenLoaiPhong(String tenLoaiPhong) { this.tenLoaiPhong = tenLoaiPhong; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    
    public String getNgayDangKy() { return ngayDangKy; }
    public void setNgayDangKy(String ngayDangKy) { this.ngayDangKy = ngayDangKy; }
}
