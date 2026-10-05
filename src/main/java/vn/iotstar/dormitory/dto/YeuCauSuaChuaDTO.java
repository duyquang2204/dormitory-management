package vn.iotstar.dormitory.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class YeuCauSuaChuaDTO {
    
    private String maYeuCau;
    
    @NotBlank(message = "Mã phòng không được để trống")
    private String maPhong;
    
    @NotBlank(message = "Nội dung sự cố không được để trống")
    private String noiDungSuCo;
    
    private MultipartFile hinhAnhFile;
    private String hinhAnh;
    private String trangThai;

    // Getters and Setters
    public String getMaYeuCau() { return maYeuCau; }
    public void setMaYeuCau(String maYeuCau) { this.maYeuCau = maYeuCau; }

    public String getMaPhong() { return maPhong; }
    public void setMaPhong(String maPhong) { this.maPhong = maPhong; }

    public String getNoiDungSuCo() { return noiDungSuCo; }
    public void setNoiDungSuCo(String noiDungSuCo) { this.noiDungSuCo = noiDungSuCo; }

    public MultipartFile getHinhAnhFile() { return hinhAnhFile; }
    public void setHinhAnhFile(MultipartFile hinhAnhFile) { this.hinhAnhFile = hinhAnhFile; }

    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
