package vn.iotstar.dormitory.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import vn.iotstar.dormitory.entity.Khu;
import vn.iotstar.dormitory.entity.LoaiPhong;
import vn.iotstar.dormitory.entity.Phong;
import vn.iotstar.dormitory.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private KhuRepository khuRepository;

    @Autowired
    private LoaiPhongRepository loaiPhongRepository;

    @Autowired
    private PhongRepository phongRepository;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private PhanPhongRepository phanPhongRepository;

    @Autowired
    private DangKyKTXRepository dangKyKTXRepository;

    @Autowired
    private HopDongRepository hopDongRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private ViPhamRepository viPhamRepository;

    @Autowired
    private TamVangRepository tamVangRepository;

    @Autowired
    private TraPhongRepository traPhongRepository;

    @Autowired
    private ChuyenPhongRepository chuyenPhongRepository;

    @Autowired
    private GiaHanHopDongRepository giaHanHopDongRepository;

    @Autowired
    private YeuCauSuaChuaRepository yeuCauSuaChuaRepository;

    @Autowired
    private KetQuaSuaChuaRepository ketQuaSuaChuaRepository;

    @Autowired
    private ThongBaoRepository thongBaoRepository;

    @Autowired
    private TinNhanRepository tinNhanRepository;

    @Autowired
    private OtpXacThucRepository otpXacThucRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. Đảm bảo tài khoản cán bộ mặc định (admin, quansinh, nhanvien)
        seedAccounts();

        // 2. Khởi tạo / Đồng bộ chính xác 144 phòng KTX (4 lầu x 12 phòng/lầu cho 3 khu A, B, C; xóa sạch các phòng tầng 5 dư)
        syncRoomsData();
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
    }

    private void cleanStudentData() {
        System.out.println("Dọn dẹp sạch toàn bộ dữ liệu sinh viên để sẵn sàng kiểm thử mới...");
        try { hoaDonRepository.deleteAll(); } catch (Exception ignored) {}
        try { giaHanHopDongRepository.deleteAll(); } catch (Exception ignored) {}
        try { hopDongRepository.deleteAll(); } catch (Exception ignored) {}
        try { traPhongRepository.deleteAll(); } catch (Exception ignored) {}
        try { chuyenPhongRepository.deleteAll(); } catch (Exception ignored) {}
        try { viPhamRepository.deleteAll(); } catch (Exception ignored) {}
        try { tamVangRepository.deleteAll(); } catch (Exception ignored) {}
        try { ketQuaSuaChuaRepository.deleteAll(); } catch (Exception ignored) {}
        try { yeuCauSuaChuaRepository.deleteAll(); } catch (Exception ignored) {}
        try { tinNhanRepository.deleteAll(); } catch (Exception ignored) {}
        try { thongBaoRepository.deleteAll(); } catch (Exception ignored) {}
        try { otpXacThucRepository.deleteAll(); } catch (Exception ignored) {}
        try { phanPhongRepository.deleteAll(); } catch (Exception ignored) {}
        try { dangKyKTXRepository.deleteAll(); } catch (Exception ignored) {}
        try { sinhVienRepository.deleteAll(); } catch (Exception ignored) {}
        System.out.println("Hoàn tất: Đã xóa toàn bộ dữ liệu sinh viên. Hệ thống hiện có 0 sinh viên.");
    }

    private void syncRoomsData() {
        if (phongRepository.count() >= 144 && khuRepository.count() >= 3 && loaiPhongRepository.count() >= 3) {
            // Dữ liệu 144 phòng và khu/loại phòng đã đầy đủ, bỏ qua để ứng dụng khởi động ngay lập tức (<1s)
            return;
        }
        System.out.println("Bắt đầu đồng bộ 144 phòng KTX (4 lầu/khu)...");

        // 1. Tạo hoặc cập nhật Loại Phòng
        LoaiPhong lp4 = loaiPhongRepository.findById("LP04").orElseGet(LoaiPhong::new);
        lp4.setMaLoaiPhong("LP04");
        lp4.setTenLoaiPhong("Phòng 4 người");
        lp4.setSoNguoiToiDa(4);
        lp4.setDonGia(1500000.0);
        lp4.setMoTa("Phòng 4 người cao cấp: Điều hòa Inverter 24/24, quạt trần, bình nóng lạnh, wifi riêng, nệm êm ái");
        loaiPhongRepository.save(lp4);

        LoaiPhong lp6 = loaiPhongRepository.findById("LP06").orElseGet(LoaiPhong::new);
        lp6.setMaLoaiPhong("LP06");
        lp6.setTenLoaiPhong("Phòng 6 người");
        lp6.setSoNguoiToiDa(6);
        lp6.setDonGia(1000000.0);
        lp6.setMoTa("Phòng 6 người tiện nghi: Có quạt trần, bình nóng lạnh, wifi tốc độ cao, tủ cá nhân (không có máy điều hòa)");
        loaiPhongRepository.save(lp6);

        LoaiPhong lp8 = loaiPhongRepository.findById("LP08").orElseGet(LoaiPhong::new);
        lp8.setMaLoaiPhong("LP08");
        lp8.setTenLoaiPhong("Phòng 8 người");
        lp8.setSoNguoiToiDa(8);
        lp8.setDonGia(800000.0);
        lp8.setMoTa("Phòng 8 người tiết kiệm: Có quạt trần công suất lớn, wifi tốc độ cao, tủ cá nhân (không điều hòa, không nóng lạnh)");
        loaiPhongRepository.save(lp8);

        // 2. Tạo hoặc cập nhật Khu (Khu A: 8 người, Khu B: 6 người, Khu C: 4 người)
        Khu khuA = khuRepository.findById("A").orElseGet(Khu::new);
        khuA.setMaKhu("A");
        khuA.setTenKhu("Khu A");
        khuA.setMoTa("Khu A - Tòa 4 tầng (Tầng 1-2: Nữ | Tầng 3-4: Nam) - Phòng 8 người");
        khuRepository.save(khuA);

        Khu khuB = khuRepository.findById("B").orElseGet(Khu::new);
        khuB.setMaKhu("B");
        khuB.setTenKhu("Khu B");
        khuB.setMoTa("Khu B - Tòa 4 tầng (Tầng 1-2: Nữ | Tầng 3-4: Nam) - Phòng 6 người");
        khuRepository.save(khuB);

        Khu khuC = khuRepository.findById("C").orElseGet(Khu::new);
        khuC.setMaKhu("C");
        khuC.setTenKhu("Khu C");
        khuC.setMoTa("Khu C - Tòa 4 tầng (Tầng 1-2: Nữ | Tầng 3-4: Nam) - Phòng 4 người cao cấp");
        khuRepository.save(khuC);

        // 3. Chuẩn bị danh sách chính xác 144 phòng (3 khu x 4 tầng x 12 phòng/tầng)
        int soLau = 4;
        int soPhongMoiLau = 12;

        List<Phong> targetRooms = new ArrayList<>();
        targetRooms.addAll(generateRooms(khuA, lp8, soLau, soPhongMoiLau));
        targetRooms.addAll(generateRooms(khuB, lp6, soLau, soPhongMoiLau));
        targetRooms.addAll(generateRooms(khuC, lp4, soLau, soPhongMoiLau));

        // 4. Xóa triệt để mọi phòng dư (thuộc tầng 5 hoặc dữ liệu cũ ngoài 144 phòng chuẩn)
        Set<String> validRoomIds = targetRooms.stream().map(Phong::getMaPhong).collect(Collectors.toSet());
        List<Phong> extraRooms = phongRepository.findAll().stream()
                .filter(p -> !validRoomIds.contains(p.getMaPhong()))
                .toList();
        if (!extraRooms.isEmpty()) {
            phongRepository.deleteAll(extraRooms);
            System.out.println("Đã xóa " + extraRooms.size() + " phòng dư (tầng 5 hoặc phòng thừa) thành công!");
        }

        for (Phong r : targetRooms) {
            Phong existing = phongRepository.findById(r.getMaPhong()).orElse(null);
            if (existing != null) {
                existing.setKhu(r.getKhu());
                existing.setLoaiPhong(r.getLoaiPhong());
                existing.setTang(r.getTang());
                existing.setGioiTinh(r.getGioiTinh());
                existing.setSoPhong(r.getSoPhong()); // Cập nhật số phòng định dạng B-301
                if (existing.getTrangThai() == null || existing.getTrangThai().isBlank()) {
                    existing.setTrangThai("Trống");
                }
                phongRepository.save(existing);
            } else {
                phongRepository.save(r);
            }
        }

        // Đảm bảo tất cả phòng trong database có soPhong định dạng [Khu]-[Số] (ví dụ: B-301, A-101)
        List<Phong> allRooms = phongRepository.findAll();
        for (Phong p : allRooms) {
            if (p.getKhu() != null && p.getKhu().getMaKhu() != null && p.getSoPhong() != null) {
                String prefix = p.getKhu().getMaKhu().trim() + "-";
                if (!p.getSoPhong().startsWith(prefix)) {
                    String cleanNum = p.getSoPhong().replace(p.getKhu().getMaKhu().trim(), "").replace("-", "").trim();
                    p.setSoPhong(prefix + cleanNum);
                    phongRepository.save(p);
                }
            }
        }
        System.out.println("Đã đồng bộ toàn bộ " + phongRepository.count() + " phòng theo định dạng mã phòng [Khu]-[Số] (ví dụ: B-301)!");
    }

    private List<Phong> generateRooms(Khu khu, LoaiPhong loaiPhong, int soLau, int soPhongMoiLau) {
        List<Phong> rooms = new ArrayList<>();
        for (int lau = 1; lau <= soLau; lau++) {
            String gioiTinh = (lau <= 2) ? "Nữ" : "Nam";
            for (int p = 1; p <= soPhongMoiLau; p++) {
                String numStr = String.format("%d%02d", lau, p); // vd: 101, 102... 412
                String soPhong = khu.getMaKhu() + "-" + numStr;   // vd: A-101, B-301, C-412
                String maPhong = khu.getMaKhu() + numStr;         // vd: A101, B301

                Phong phong = new Phong();
                phong.setMaPhong(maPhong);
                phong.setSoPhong(soPhong);
                phong.setKhu(khu);
                phong.setLoaiPhong(loaiPhong);
                phong.setTang(lau);
                phong.setGioiTinh(gioiTinh);
                phong.setTrangThai("Trống");
                
                rooms.add(phong);
            }
        }
        return rooms;
    }
}
