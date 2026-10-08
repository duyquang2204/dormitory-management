package vn.iotstar.dormitory.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class NopDonOnlineDTO {

    @NotBlank(message = "Họ và tên không được để trống")
    private String hoTen;

    @NotNull(message = "Ngày sinh không được để trống")
    private LocalDate ngaySinh;

    @NotBlank(message = "Vui lòng chọn giới tính")
    private String gioiTinh;

    @NotBlank(message = "Số CCCD/CMND không được để trống")
    private String cccd;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String sdt;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Định dạng email không hợp lệ")
    private String email;

    @NotBlank(message = "Quê quán không được để trống")
    private String queQuan;

    @NotBlank(message = "Tên trường Đại học/Cao đẳng đang học không được để trống")
    private String truongDaiHoc;

    private String khoa;

    private Integer namHoc = 1;

    private String dienUuTien = "Không ưu tiên";

    @NotBlank(message = "Vui lòng chọn loại phòng mong muốn")
    private String maLoaiPhong;

    private String ghiChu;

    private MultipartFile anhTheSinhVienFile;

    private MultipartFile anhCccdFile;

    private MultipartFile anhMinhChungUuTienFile;

    public NopDonOnlineDTO() {}

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public String getTruongDaiHoc() {
        return truongDaiHoc;
    }

    public void setTruongDaiHoc(String truongDaiHoc) {
        this.truongDaiHoc = truongDaiHoc;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public Integer getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(Integer namHoc) {
        this.namHoc = namHoc;
    }

    public String getDienUuTien() {
        return dienUuTien;
    }

    public void setDienUuTien(String dienUuTien) {
        this.dienUuTien = dienUuTien;
    }

    public String getMaLoaiPhong() {
        return maLoaiPhong;
    }

    public void setMaLoaiPhong(String maLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public MultipartFile getAnhTheSinhVienFile() {
        return anhTheSinhVienFile;
    }

    public void setAnhTheSinhVienFile(MultipartFile anhTheSinhVienFile) {
        this.anhTheSinhVienFile = anhTheSinhVienFile;
    }

    public MultipartFile getAnhCccdFile() {
        return anhCccdFile;
    }

    public void setAnhCccdFile(MultipartFile anhCccdFile) {
        this.anhCccdFile = anhCccdFile;
    }

    public MultipartFile getAnhMinhChungUuTienFile() {
        return anhMinhChungUuTienFile;
    }

    public void setAnhMinhChungUuTienFile(MultipartFile anhMinhChungUuTienFile) {
        this.anhMinhChungUuTienFile = anhMinhChungUuTienFile;
    }
}
