package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TinNhan")
public class TinNhan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String maSV;

    @Column(nullable = false)
    private String nguoiGui;

    private String tenNguoiGui;

    @Column(nullable = false)
    private String vaiTroGui; // "SINH_VIEN" hoặc "QUAN_SINH"

    @Column(columnDefinition = "TEXT", nullable = false)
    private String noiDung;

    private LocalDateTime thoiGian;

    private Boolean daDoc = false;

    public TinNhan() {
        this.thoiGian = LocalDateTime.now();
        this.daDoc = false;
    }

    public TinNhan(String maSV, String nguoiGui, String tenNguoiGui, String vaiTroGui, String noiDung) {
        this.maSV = maSV;
        this.nguoiGui = nguoiGui;
        this.tenNguoiGui = tenNguoiGui;
        this.vaiTroGui = vaiTroGui;
        this.noiDung = noiDung;
        this.thoiGian = LocalDateTime.now();
        this.daDoc = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getNguoiGui() {
        return nguoiGui;
    }

    public void setNguoiGui(String nguoiGui) {
        this.nguoiGui = nguoiGui;
    }

    public String getTenNguoiGui() {
        return tenNguoiGui;
    }

    public void setTenNguoiGui(String tenNguoiGui) {
        this.tenNguoiGui = tenNguoiGui;
    }

    public String getVaiTroGui() {
        return vaiTroGui;
    }

    public void setVaiTroGui(String vaiTroGui) {
        this.vaiTroGui = vaiTroGui;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public LocalDateTime getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(LocalDateTime thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Boolean getDaDoc() {
        return daDoc;
    }

    public void setDaDoc(Boolean daDoc) {
        this.daDoc = daDoc;
    }
}
