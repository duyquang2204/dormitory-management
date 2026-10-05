package vn.iotstar.dormitory.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import vn.iotstar.dormitory.entity.Khu;
import vn.iotstar.dormitory.entity.LoaiPhong;
import vn.iotstar.dormitory.entity.Phong;
import vn.iotstar.dormitory.repository.KhuRepository;
import vn.iotstar.dormitory.repository.LoaiPhongRepository;
import vn.iotstar.dormitory.repository.PhongRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private KhuRepository khuRepository;

    @Autowired
    private LoaiPhongRepository loaiPhongRepository;

    @Autowired
    private PhongRepository phongRepository;

    @Autowired
    private vn.iotstar.dormitory.repository.NguoiDungRepository nguoiDungRepository;

    @Autowired
    private vn.iotstar.dormitory.repository.SinhVienRepository sinhVienRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. Khởi tạo tài khoản mẫu nếu chưa có
        seedAccounts();

        // 2. Kiểm tra xem dữ liệu khu và phòng đã tồn tại chưa
        if (khuRepository.count() == 0 && loaiPhongRepository.count() == 0) {
            seedData();
        }
    }

    private void seedAccounts() {
        if (nguoiDungRepository.count() == 0) {
            System.out.println("Khởi tạo tài khoản cán bộ mặc định (admin, quansinh, nhanvien)...");

            vn.iotstar.dormitory.entity.NguoiDung admin = new vn.iotstar.dormitory.entity.NguoiDung();
            admin.setMaNguoiDung(java.util.UUID.randomUUID().toString());
            admin.setTenDangNhap("admin");
            admin.setMatKhau(passwordEncoder.encode("123456"));
            admin.setHoTen("Quản Trị Viên");
            admin.setChucVu("Quản trị viên");
            admin.setSdt("0901234567");
            admin.setTrangThai("Hoạt động");
            nguoiDungRepository.save(admin);

            vn.iotstar.dormitory.entity.NguoiDung quansinh = new vn.iotstar.dormitory.entity.NguoiDung();
            quansinh.setMaNguoiDung(java.util.UUID.randomUUID().toString());
            quansinh.setTenDangNhap("quansinh");
            quansinh.setMatKhau(passwordEncoder.encode("123456"));
            quansinh.setHoTen("Nguyễn Quản Sinh");
            quansinh.setChucVu("Quản sinh");
            quansinh.setSdt("0907654321");
            quansinh.setTrangThai("Hoạt động");
            nguoiDungRepository.save(quansinh);

            vn.iotstar.dormitory.entity.NguoiDung nhanvien = new vn.iotstar.dormitory.entity.NguoiDung();
            nhanvien.setMaNguoiDung(java.util.UUID.randomUUID().toString());
            nhanvien.setTenDangNhap("nhanvien");
            nhanvien.setMatKhau(passwordEncoder.encode("123456"));
            nhanvien.setHoTen("Trần Thợ Sửa");
            nhanvien.setChucVu("Nhân viên sửa chữa");
            nhanvien.setSdt("0912345678");
            nhanvien.setTrangThai("Hoạt động");
            nguoiDungRepository.save(nhanvien);
        }

        if (sinhVienRepository.count() == 0) {
            System.out.println("Khởi tạo sinh viên mẫu SV001...");
            vn.iotstar.dormitory.entity.SinhVien sv = new vn.iotstar.dormitory.entity.SinhVien();
            sv.setMaSV("SV001");
            sv.setHoTen("Nguyễn Duy Quang");
            sv.setMatKhau(passwordEncoder.encode("123456"));
            sv.setEmail("duyquang22042005@gmail.com");
            sv.setSdt("0988776655");
            sv.setGioiTinh("Nam");
            sv.setQueQuan("TP. Hồ Chí Minh");
            sv.setCccd("079205001234");
            sv.setKhoa("Công nghệ Thông tin");
            sv.setNamHoc(2023);
            sv.setNgaySinh(java.time.LocalDate.of(2005, 4, 22));
            sinhVienRepository.save(sv);
        }
    }

    private void seedData() {
        System.out.println("Bắt đầu tạo dữ liệu mẫu cho Khu, Loại Phòng và Phòng...");

        // 1. Tạo dữ liệu Loại Phòng
        LoaiPhong lp4 = new LoaiPhong();
        lp4.setMaLoaiPhong("LP04");
        lp4.setTenLoaiPhong("Phòng 4 người");
        lp4.setSoNguoiToiDa(4);
        lp4.setDonGia(1500000.0);
        lp4.setMoTa("Phòng tiêu chuẩn 4 giường, chất lượng cao");
        loaiPhongRepository.save(lp4);

        LoaiPhong lp6 = new LoaiPhong();
        lp6.setMaLoaiPhong("LP06");
        lp6.setTenLoaiPhong("Phòng 6 người");
        lp6.setSoNguoiToiDa(6);
        lp6.setDonGia(1000000.0);
        lp6.setMoTa("Phòng 6 giường, có quạt, nhà vệ sinh chung");
        loaiPhongRepository.save(lp6);

        LoaiPhong lp8 = new LoaiPhong();
        lp8.setMaLoaiPhong("LP08");
        lp8.setTenLoaiPhong("Phòng 8 người");
        lp8.setSoNguoiToiDa(8);
        lp8.setDonGia(800000.0);
        lp8.setMoTa("Phòng 8 giường, tiết kiệm");
        loaiPhongRepository.save(lp8);

        // 2. Tạo dữ liệu Khu
        Khu khuA = new Khu();
        khuA.setMaKhu("A");
        khuA.setTenKhu("Khu A");
        khuA.setMoTa("Ký túc xá Nam - Sinh viên đại trà");
        khuRepository.save(khuA);

        Khu khuB = new Khu();
        khuB.setMaKhu("B");
        khuB.setTenKhu("Khu B");
        khuB.setMoTa("Ký túc xá Nữ - Sinh viên đại trà");
        khuRepository.save(khuB);

        Khu khuC = new Khu();
        khuC.setMaKhu("C");
        khuC.setTenKhu("Khu C");
        khuC.setMoTa("Khu chất lượng cao - Phòng 4 người");
        khuRepository.save(khuC);

        // 3. Tạo dữ liệu Phòng tự động (Cấu trúc: 5 lầu, mỗi lầu 12 phòng)
        List<Phong> danhSachPhong = new ArrayList<>();
        
        int soLau = 5;
        int soPhongMoiLau = 12;

        // Sinh phòng cho Khu A (Dùng phòng 8 người)
        danhSachPhong.addAll(generateRooms(khuA, lp8, soLau, soPhongMoiLau));

        // Sinh phòng cho Khu B (Dùng phòng 6 người)
        danhSachPhong.addAll(generateRooms(khuB, lp6, soLau, soPhongMoiLau));

        // Sinh phòng cho Khu C (Dùng phòng 4 người)
        danhSachPhong.addAll(generateRooms(khuC, lp4, soLau, soPhongMoiLau));

        phongRepository.saveAll(danhSachPhong);
        System.out.println("Đã tạo thành công " + danhSachPhong.size() + " phòng!");
    }

    private List<Phong> generateRooms(Khu khu, LoaiPhong loaiPhong, int soLau, int soPhongMoiLau) {
        List<Phong> rooms = new ArrayList<>();
        for (int lau = 1; lau <= soLau; lau++) {
            for (int p = 1; p <= soPhongMoiLau; p++) {
                String soPhong = String.format("%d%02d", lau, p); // vd: 101, 102... 512
                String maPhong = khu.getMaKhu() + soPhong;        // vd: A101, B205

                Phong phong = new Phong();
                phong.setMaPhong(maPhong);
                phong.setSoPhong(soPhong);
                phong.setKhu(khu);
                phong.setLoaiPhong(loaiPhong);
                phong.setTrangThai("Trống");
                
                rooms.add(phong);
            }
        }
        return rooms;
    }
}
