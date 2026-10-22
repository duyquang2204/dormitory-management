package vn.iotstar.dormitory.dto;

import java.util.List;

public class HoaDonMassInputDTO {
    private String maKhu;
    private int thang;
    private int nam;
    private Double donGiaDien;
    private Double donGiaNuoc;
    private List<HoaDonRoomInputDTO> rooms;

    // Getters and setters
    public String getMaKhu() { return maKhu; }
    public void setMaKhu(String maKhu) { this.maKhu = maKhu; }
    
    public int getThang() { return thang; }
    public void setThang(int thang) { this.thang = thang; }
    
    public int getNam() { return nam; }
    public void setNam(int nam) { this.nam = nam; }
    
    public Double getDonGiaDien() { return donGiaDien; }
    public void setDonGiaDien(Double donGiaDien) { this.donGiaDien = donGiaDien; }

    public Double getDonGiaNuoc() { return donGiaNuoc; }
    public void setDonGiaNuoc(Double donGiaNuoc) { this.donGiaNuoc = donGiaNuoc; }

    public List<HoaDonRoomInputDTO> getRooms() { return rooms; }
    public void setRooms(List<HoaDonRoomInputDTO> rooms) { this.rooms = rooms; }
}
