package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Khu")
public class Khu {

    @Id
    private String maKhu;

    private String tenKhu;

    private String moTa;

    public Khu() {}

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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

}
