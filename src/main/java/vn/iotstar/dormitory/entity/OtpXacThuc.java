package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "OtpXacThuc")
public class OtpXacThuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 10)
    private String otpCode;

    @Column(nullable = false, length = 30)
    private String loaiOtp; // "DANG_KY" hoặc "QUEN_MAT_KHAU"

    @Column(nullable = false)
    private LocalDateTime thoiGianHetHan;

    private boolean daSuDung = false;

    private LocalDateTime ngayTao = LocalDateTime.now();

    public OtpXacThuc() {}

    public OtpXacThuc(String email, String otpCode, String loaiOtp, LocalDateTime thoiGianHetHan) {
        this.email = email;
        this.otpCode = otpCode;
        this.loaiOtp = loaiOtp;
        this.thoiGianHetHan = thoiGianHetHan;
        this.daSuDung = false;
        this.ngayTao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getLoaiOtp() {
        return loaiOtp;
    }

    public void setLoaiOtp(String loaiOtp) {
        this.loaiOtp = loaiOtp;
    }

    public LocalDateTime getThoiGianHetHan() {
        return thoiGianHetHan;
    }

    public void setThoiGianHetHan(LocalDateTime thoiGianHetHan) {
        this.thoiGianHetHan = thoiGianHetHan;
    }

    public boolean isDaSuDung() {
        return daSuDung;
    }

    public void setDaSuDung(boolean daSuDung) {
        this.daSuDung = daSuDung;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }
}
