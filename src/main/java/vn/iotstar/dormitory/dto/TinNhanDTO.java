package vn.iotstar.dormitory.dto;

import java.time.LocalDateTime;

public class TinNhanDTO {

    private Long id;
    private String maSV;
    private String nguoiGui;
    private String tenNguoiGui;
    private String vaiTroGui; // "SINH_VIEN" hoặc "QUAN_SINH"
    private String noiDung;
    private LocalDateTime thoiGian;
    private String thoiGianFormatted;
    private Boolean daDoc;

    public TinNhanDTO() {}

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

    public String getThoiGianFormatted() {
        return thoiGianFormatted;
    }

    public void setThoiGianFormatted(String thoiGianFormatted) {
        this.thoiGianFormatted = thoiGianFormatted;
    }

    public Boolean getDaDoc() {
        return daDoc;
    }

    public void setDaDoc(Boolean daDoc) {
        this.daDoc = daDoc;
    }
}
