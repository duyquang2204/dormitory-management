package vn.iotstar.dormitory.dto;

public class HoaDonRoomInputDTO {
    private String maPhong;
    private String tenPhong;
    private Double chiSoDienCu;
    private Double chiSoNuocCu;
    private Double chiSoDienMoi;
    private Double chiSoNuocMoi;
    private boolean daTonTai;

    // Getters and setters
    public String getMaPhong() { return maPhong; }
    public void setMaPhong(String maPhong) { this.maPhong = maPhong; }
    
    public String getTenPhong() { return tenPhong; }
    public void setTenPhong(String tenPhong) { this.tenPhong = tenPhong; }
    
    public Double getChiSoDienCu() { return chiSoDienCu; }
    public void setChiSoDienCu(Double chiSoDienCu) { this.chiSoDienCu = chiSoDienCu; }
    
    public Double getChiSoNuocCu() { return chiSoNuocCu; }
    public void setChiSoNuocCu(Double chiSoNuocCu) { this.chiSoNuocCu = chiSoNuocCu; }
    
    public Double getChiSoDienMoi() { return chiSoDienMoi; }
    public void setChiSoDienMoi(Double chiSoDienMoi) { this.chiSoDienMoi = chiSoDienMoi; }
    
    public Double getChiSoNuocMoi() { return chiSoNuocMoi; }
    public void setChiSoNuocMoi(Double chiSoNuocMoi) { this.chiSoNuocMoi = chiSoNuocMoi; }

    public boolean isDaTonTai() { return daTonTai; }
    public void setDaTonTai(boolean daTonTai) { this.daTonTai = daTonTai; }
}
