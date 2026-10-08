package vn.iotstar.dormitory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Phong")
public class Phong {

    @Id
    private String maPhong;

    private String soPhong;

    @ManyToOne
    @JoinColumn(name = "maKhu")
    private Khu khu;

    @ManyToOne
    @JoinColumn(name = "maLoaiPhong")
    private LoaiPhong loaiPhong;

    private String trangThai;

    private Integer tang;

    private String gioiTinh;

    @Transient
    private long soNguoiHienTai = 0;

    @Transient
    private long soChoTrong = 0;

    public Phong() {}

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getSoPhong() {
        if (soPhong != null && khu != null && khu.getMaKhu() != null && !khu.getMaKhu().isBlank()) {
            String prefix = khu.getMaKhu().trim() + "-";
            if (!soPhong.startsWith(prefix)) {
                String clean = soPhong.replace(khu.getMaKhu().trim(), "").replace("-", "").trim();
                return prefix + clean;
            }
        }
        return soPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
    }

    public Khu getKhu() {
        return khu;
    }

    public void setKhu(Khu khu) {
        this.khu = khu;
    }

    public LoaiPhong getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(LoaiPhong loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public long getSoNguoiHienTai() {
        return soNguoiHienTai;
    }

    public void setSoNguoiHienTai(long soNguoiHienTai) {
        this.soNguoiHienTai = soNguoiHienTai;
    }

    public long getSoChoTrong() {
        return soChoTrong;
    }

    public void setSoChoTrong(long soChoTrong) {
        this.soChoTrong = soChoTrong;
    }

    public Integer getTang() {
        if (tang != null) return tang;
        if (soPhong != null && !soPhong.isEmpty()) {
            try {
                for (int i = 0; i < soPhong.length(); i++) {
                    char c = soPhong.charAt(i);
                    if (Character.isDigit(c)) {
                        return Character.getNumericValue(c);
                    }
                }
            } catch (Exception ignored) {}
        }
        return 1;
    }

    public void setTang(Integer tang) {
        this.tang = tang;
    }

    public String getGioiTinh() {
        if (gioiTinh != null && !gioiTinh.isEmpty()) return gioiTinh;
        int t = getTang();
        return (t <= 2) ? "Nữ" : "Nam";
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
}
