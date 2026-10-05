package vn.iotstar.dormitory.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class SinhVienDTO {
    
    @NotBlank(message = "Mã sinh viên không được để trống")
    private String maSV;
    
    @NotBlank(message = "Họ tên không được để trống")
    private String hoTen;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ngaySinh;
    
    private String gioiTinh;
    private String queQuan;
    
    @NotBlank(message = "CCCD không được để trống")
    private String cccd;
    
    private String sdt;
    
    private String email;
    
    private String khoa;
    private Integer namHoc;
    private String dienUuTien;
    
    private String matKhau;

    // Getters and Setters
    public String getMaSV() { return maSV; }
    public void setMaSV(String maSV) { this.maSV = maSV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public LocalDate getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getQueQuan() { return queQuan; }
    public void setQueQuan(String queQuan) { this.queQuan = queQuan; }

    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getKhoa() { return khoa; }
    public void setKhoa(String khoa) { this.khoa = khoa; }

    public Integer getNamHoc() { return namHoc; }
    public void setNamHoc(Integer namHoc) { this.namHoc = namHoc; }

    public String getDienUuTien() { return dienUuTien; }
    public void setDienUuTien(String dienUuTien) { this.dienUuTien = dienUuTien; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
}
