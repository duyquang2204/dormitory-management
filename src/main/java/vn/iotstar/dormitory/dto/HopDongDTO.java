package vn.iotstar.dormitory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class HopDongDTO {
    
    private String maHopDong;
    
    @NotBlank(message = "Mã phân phòng không được để trống")
    private String maPhanPhong;
    
    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate ngayBatDau;
    
    @NotNull(message = "Ngày kết thúc không được để trống")
    private LocalDate ngayKetThuc;
    
    @NotNull(message = "Giá tiền không được để trống")
    private Double giaTien;
    
    private String trangThai;

    // Getters and Setters
    public String getMaHopDong() { return maHopDong; }
    public void setMaHopDong(String maHopDong) { this.maHopDong = maHopDong; }

    public String getMaPhanPhong() { return maPhanPhong; }
    public void setMaPhanPhong(String maPhanPhong) { this.maPhanPhong = maPhanPhong; }

    public LocalDate getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(LocalDate ngayBatDau) { this.ngayBatDau = ngayBatDau; }

    public LocalDate getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(LocalDate ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }

    public Double getGiaTien() { return giaTien; }
    public void setGiaTien(Double giaTien) { this.giaTien = giaTien; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
