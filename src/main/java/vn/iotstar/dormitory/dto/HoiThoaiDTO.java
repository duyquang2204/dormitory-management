package vn.iotstar.dormitory.dto;

public class HoiThoaiDTO {

    private String maSV;
    private String tenSinhVien;
    private String phong;
    private String tinNhanCuoi;
    private String thoiGianCuoi;
    private long soTinChuaDoc;

    public HoiThoaiDTO() {}

    public HoiThoaiDTO(String maSV, String tenSinhVien, String phong, String tinNhanCuoi, String thoiGianCuoi, long soTinChuaDoc) {
        this.maSV = maSV;
        this.tenSinhVien = tenSinhVien;
        this.phong = phong;
        this.tinNhanCuoi = tinNhanCuoi;
        this.thoiGianCuoi = thoiGianCuoi;
        this.soTinChuaDoc = soTinChuaDoc;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getTenSinhVien() {
        return tenSinhVien;
    }

    public void setTenSinhVien(String tenSinhVien) {
        this.tenSinhVien = tenSinhVien;
    }

    public String getPhong() {
        return phong;
    }

    public void setPhong(String phong) {
        this.phong = phong;
    }

    public String getTinNhanCuoi() {
        return tinNhanCuoi;
    }

    public void setTinNhanCuoi(String tinNhanCuoi) {
        this.tinNhanCuoi = tinNhanCuoi;
    }

    public String getThoiGianCuoi() {
        return thoiGianCuoi;
    }

    public void setThoiGianCuoi(String thoiGianCuoi) {
        this.thoiGianCuoi = thoiGianCuoi;
    }

    public long getSoTinChuaDoc() {
        return soTinChuaDoc;
    }

    public void setSoTinChuaDoc(long soTinChuaDoc) {
        this.soTinChuaDoc = soTinChuaDoc;
    }
}
