package vn.iotstar.dormitory.dto;

public class KhuVucChoTrongDTO {

    private String maKhu;
    private String tenKhu;
    private String gioiTinh;
    private String tangText;
    private String maLoaiPhong;
    private String tenLoaiPhong;
    private int soNguoiPhong;
    private double donGia;
    private String dacDiemNoiBat;
    private int tongSoPhong;
    private long tongSoCho;
    private long soNguoiDangO;
    private long soChoConTrong;
    private int tyLeLapDay;
    private boolean conNhanDon;

    public KhuVucChoTrongDTO() {}

    public KhuVucChoTrongDTO(String maKhu, String tenKhu, String gioiTinh, String tangText,
                             String maLoaiPhong, String tenLoaiPhong, int soNguoiPhong, double donGia,
                             String dacDiemNoiBat, int tongSoPhong, long tongSoCho, long soNguoiDangO,
                             long soChoConTrong, int tyLeLapDay, boolean conNhanDon) {
        this.maKhu = maKhu;
        this.tenKhu = tenKhu;
        this.gioiTinh = gioiTinh;
        this.tangText = tangText;
        this.maLoaiPhong = maLoaiPhong;
        this.tenLoaiPhong = tenLoaiPhong;
        this.soNguoiPhong = soNguoiPhong;
        this.donGia = donGia;
        this.dacDiemNoiBat = dacDiemNoiBat;
        this.tongSoPhong = tongSoPhong;
        this.tongSoCho = tongSoCho;
        this.soNguoiDangO = soNguoiDangO;
        this.soChoConTrong = soChoConTrong;
        this.tyLeLapDay = tyLeLapDay;
        this.conNhanDon = conNhanDon;
    }

    public String getMaKhu() {
        return maKhu;
    }

    public void setMaKhu(String maKhu) {
        this.maKhu = maKhu;
    }

    public String getTenKhu() {
        return tenKhu;
    }

    public void setTenKhu(String tenKhu) {
        this.tenKhu = tenKhu;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getTangText() {
        return tangText;
    }

    public void setTangText(String tangText) {
        this.tangText = tangText;
    }

    public String getMaLoaiPhong() {
        return maLoaiPhong;
    }

    public void setMaLoaiPhong(String maLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
    }

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public void setTenLoaiPhong(String tenLoaiPhong) {
        this.tenLoaiPhong = tenLoaiPhong;
    }

    public int getSoNguoiPhong() {
        return soNguoiPhong;
    }

    public void setSoNguoiPhong(int soNguoiPhong) {
        this.soNguoiPhong = soNguoiPhong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public String getDacDiemNoiBat() {
        return dacDiemNoiBat;
    }

    public void setDacDiemNoiBat(String dacDiemNoiBat) {
        this.dacDiemNoiBat = dacDiemNoiBat;
    }

    public int getTongSoPhong() {
        return tongSoPhong;
    }

    public void setTongSoPhong(int tongSoPhong) {
        this.tongSoPhong = tongSoPhong;
    }

    public long getTongSoCho() {
        return tongSoCho;
    }

    public void setTongSoCho(long tongSoCho) {
        this.tongSoCho = tongSoCho;
    }

    public long getSoNguoiDangO() {
        return soNguoiDangO;
    }

    public void setSoNguoiDangO(long soNguoiDangO) {
        this.soNguoiDangO = soNguoiDangO;
    }

    public long getSoChoConTrong() {
        return soChoConTrong;
    }

    public void setSoChoConTrong(long soChoConTrong) {
        this.soChoConTrong = soChoConTrong;
    }

    public int getTyLeLapDay() {
        return tyLeLapDay;
    }

    public void setTyLeLapDay(int tyLeLapDay) {
        this.tyLeLapDay = tyLeLapDay;
    }

    public boolean isConNhanDon() {
        return conNhanDon;
    }

    public void setConNhanDon(boolean conNhanDon) {
        this.conNhanDon = conNhanDon;
    }
}
