package vn.iotstar.dormitory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.iotstar.dormitory.entity.NguoiDung;
import vn.iotstar.dormitory.repository.NguoiDungRepository;

import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (nguoiDungRepository.count() == 0) {
            // 1. Admin
            NguoiDung admin = new NguoiDung();
            admin.setMaNguoiDung(UUID.randomUUID().toString());
            admin.setTenDangNhap("admin");
            admin.setMatKhau(passwordEncoder.encode("123456"));
            admin.setHoTen("Quản trị viên hệ thống");
            admin.setChucVu("Quản trị viên");
            admin.setTrangThai("Hoạt động");
            nguoiDungRepository.save(admin);
            
            // 2. Quản Sinh
            NguoiDung qs = new NguoiDung();
            qs.setMaNguoiDung(UUID.randomUUID().toString());
            qs.setTenDangNhap("quansinh");
            qs.setMatKhau(passwordEncoder.encode("123456"));
            qs.setHoTen("Nguyễn Quản Sinh");
            qs.setChucVu("Quản sinh");
            qs.setTrangThai("Hoạt động");
            nguoiDungRepository.save(qs);

            // 3. Nhân viên sửa chữa
            NguoiDung nv = new NguoiDung();
            nv.setMaNguoiDung(UUID.randomUUID().toString());
            nv.setTenDangNhap("nhanvien");
            nv.setMatKhau(passwordEncoder.encode("123456"));
            nv.setHoTen("Trần Thợ Sửa");
            nv.setChucVu("Nhân viên sửa chữa");
            nv.setTrangThai("Hoạt động");
            nguoiDungRepository.save(nv);
            
            System.out.println("====== TẠO TÀI KHOẢN MẶC ĐỊNH THÀNH CÔNG ======");
            System.out.println("Admin: admin / 123456");
            System.out.println("Quản sinh: quansinh / 123456");
            System.out.println("Nhân viên: nhanvien / 123456");
            System.out.println("================================================");
        }
    }
}
