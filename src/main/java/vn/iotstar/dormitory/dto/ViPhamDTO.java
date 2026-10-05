package vn.iotstar.dormitory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ViPhamDTO {
    private String maViPham;
    
    @NotBlank(message = "Mã sinh viên không được để trống")
    private String maSV;
    
    @NotNull(message = "Ngày vi phạm không được để trống")
    private LocalDate ngayViPham;
    
    @NotBlank(message = "Nội dung không được để trống")
    private String noiDung;
    
    private String diaDiem;
    private String hinhThucXuLy;
    private String trangThaiXuLy;

    public ViPhamDTO() {}

    public String getMaViPham() {
        return maViPham;
    }

    public void setMaViPham(String maViPham) {
        this.maViPham = maViPham;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public LocalDate getNgayViPham() {
        return ngayViPham;
    }

    public void setNgayViPham(LocalDate ngayViPham) {
        this.ngayViPham = ngayViPham;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public String getHinhThucXuLy() {
        return hinhThucXuLy;
    }

    public void setHinhThucXuLy(String hinhThucXuLy) {
        this.hinhThucXuLy = hinhThucXuLy;
    }

    public String getTrangThaiXuLy() {
        return trangThaiXuLy;
    }

    public void setTrangThaiXuLy(String trangThaiXuLy) {
        this.trangThaiXuLy = trangThaiXuLy;
    }
}
