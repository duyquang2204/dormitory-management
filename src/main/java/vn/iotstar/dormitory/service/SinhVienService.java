package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.dto.RegisterDTO;
import vn.iotstar.dormitory.dto.ResetPasswordDTO;
import vn.iotstar.dormitory.dto.SinhVienDTO;
import vn.iotstar.dormitory.entity.SinhVien;
import vn.iotstar.dormitory.repository.SinhVienRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.transaction.annotation.Transactional;
import vn.iotstar.dormitory.repository.*;

@Service
public class SinhVienService {

    @Autowired
    private SinhVienRepository sinhVienRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private DangKyKTXRepository dangKyKTXRepository;

    @Autowired
    private PhanPhongRepository phanPhongRepository;

    @Autowired
    private HopDongRepository hopDongRepository;

    @Autowired
    private GiaHanHopDongRepository giaHanHopDongRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private ViPhamRepository viPhamRepository;

    @Autowired
    private YeuCauSuaChuaRepository yeuCauSuaChuaRepository;

    @Autowired
    private KetQuaSuaChuaRepository ketQuaSuaChuaRepository;

    @Autowired
    private TamVangRepository tamVangRepository;

    @Autowired
    private TraPhongRepository traPhongRepository;

    @Autowired
    private ChuyenPhongRepository chuyenPhongRepository;

    @Autowired
    private TinNhanRepository tinNhanRepository;

    @Autowired
    private ThongBaoRepository thongBaoRepository;

    @Autowired
    private OtpXacThucRepository otpXacThucRepository;

    public List<SinhVien> findAll() {
        return sinhVienRepository.findAll();
    }
    
    public Page<SinhVien> findAll(Pageable pageable) {
        return sinhVienRepository.findAll(pageable);
    }
    
    public Page<SinhVien> searchByKeyword(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            String trimmed = keyword.trim();
            return sinhVienRepository.findByHoTenContainingIgnoreCaseOrMaSVContainingIgnoreCase(trimmed, trimmed, pageable);
        }
        return sinhVienRepository.findAll(pageable);
    }

    public Optional<SinhVien> findById(String id) {
        return sinhVienRepository.findById(id);
    }

    public Optional<SinhVien> findByEmail(String email) {
        return sinhVienRepository.findByEmail(email);
    }
    
    @Transactional
    public void delete(String id) {
        Optional<SinhVien> svOpt = sinhVienRepository.findById(id);
        if (svOpt.isEmpty()) {
            return;
        }
        SinhVien sv = svOpt.get();

        // 1. Gửi email thông báo cho sinh viên trước khi xóa
        if (sv.getEmail() != null && !sv.getEmail().trim().isEmpty()) {
            String subject = "Thông báo: Xóa hồ sơ tài khoản Ký túc xá";
            String htmlContent = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; border: 1px solid #e0e0e0; border-radius: 8px; padding: 24px;'>"
                + "<h2 style='color: #dc3545; margin-top: 0;'>Thông Báo Xóa Hồ Sơ Cư Dân Ký Túc Xá</h2>"
                + "<p>Chào <strong>" + sv.getHoTen() + "</strong> (MSSV: <strong>" + sv.getMaSV() + "</strong>),</p>"
                + "<p>Ban Quản lý Ký túc xá xin thông báo: Hồ sơ và tài khoản cư dân của bạn đã được Quản sinh xóa khỏi hệ thống Ký túc xá.</p>"
                + "<div style='background-color: #f8f9fa; border-left: 4px solid #dc3545; padding: 12px; margin: 16px 0;'>"
                + "<p style='margin: 0;'><strong>Thông tin chi tiết:</strong></p>"
                + "<ul style='margin: 8px 0 0 0; padding-left: 20px;'>"
                + "<li>Họ và tên: " + sv.getHoTen() + "</li>"
                + "<li>Mã sinh viên: " + sv.getMaSV() + "</li>"
                + "<li>Trạng thái: Đã xóa khỏi hệ thống</li>"
                + "</ul>"
                + "</div>"
                + "<p>Nếu bạn có bất kỳ thắc mắc hoặc khiếu nại nào, vui lòng liên hệ trực tiếp với Văn phòng Ban Quản lý Ký túc xá để được giải đáp.</p>"
                + "<p style='margin-bottom: 0;'>Trân trọng,<br><strong>Ban Quản lý Ký túc xá</strong></p>"
                + "</div>";
            try {
                emailService.sendHtmlEmail(sv.getEmail(), subject, htmlContent);
            } catch (Exception e) {
                emailService.sendEmail(sv.getEmail(), subject, "Chào " + sv.getHoTen() + ",\nHồ sơ của bạn (Mã SV: " + sv.getMaSV() + ") đã được xóa khỏi hệ thống Ký túc xá.\nTrân trọng,\nBan Quản lý KTX.");
            }
        }

        // 2. Xóa các bản ghi liên quan để không bị lỗi Foreign Key Constraint:
        
        // 2.1 Yêu cầu sửa chữa & Kết quả sửa chữa
        List<vn.iotstar.dormitory.entity.YeuCauSuaChua> yeuCaus = yeuCauSuaChuaRepository.findBySinhVien_MaSV(id);
        for (vn.iotstar.dormitory.entity.YeuCauSuaChua yc : yeuCaus) {
            if (yc.getKetQuaSuaChua() != null) {
                vn.iotstar.dormitory.entity.KetQuaSuaChua kq = yc.getKetQuaSuaChua();
                if (kq.getDanhSachNhanVien() != null) {
                    kq.getDanhSachNhanVien().clear();
                }
                ketQuaSuaChuaRepository.delete(kq);
            }
            yeuCauSuaChuaRepository.delete(yc);
        }

        // 2.2 Phân phòng và các dữ liệu liên kết (Hợp đồng, Gia hạn hợp đồng, Chuyển phòng, Trả phòng)
        List<vn.iotstar.dormitory.entity.PhanPhong> phanPhongs = phanPhongRepository.findBySinhVien_MaSV(id);
        for (vn.iotstar.dormitory.entity.PhanPhong pp : phanPhongs) {
            // Chuyển phòng
            List<vn.iotstar.dormitory.entity.ChuyenPhong> chuyenPhongs = chuyenPhongRepository.findByPhanPhong_MaPhanPhong(pp.getMaPhanPhong());
            chuyenPhongRepository.deleteAll(chuyenPhongs);

            // Trả phòng
            List<vn.iotstar.dormitory.entity.TraPhong> traPhongs = traPhongRepository.findByPhanPhong_MaPhanPhong(pp.getMaPhanPhong());
            traPhongRepository.deleteAll(traPhongs);

            // Hợp đồng & Gia hạn hợp đồng
            List<vn.iotstar.dormitory.entity.HopDong> hopDongs = hopDongRepository.findByPhanPhong_MaPhanPhong(pp.getMaPhanPhong());
            for (vn.iotstar.dormitory.entity.HopDong hd : hopDongs) {
                List<vn.iotstar.dormitory.entity.GiaHanHopDong> giaHans = giaHanHopDongRepository.findByHopDong_MaHopDong(hd.getMaHopDong());
                giaHanHopDongRepository.deleteAll(giaHans);
                hopDongRepository.delete(hd);
            }

            phanPhongRepository.delete(pp);
        }

        // 2.3 Hóa đơn
        List<vn.iotstar.dormitory.entity.HoaDon> hoaDons = hoaDonRepository.findAllBySinhVien_MaSV(id);
        hoaDonRepository.deleteAll(hoaDons);

        // 2.4 Vi phạm
        List<vn.iotstar.dormitory.entity.ViPham> viPhams = viPhamRepository.findBySinhVien_MaSV(id);
        viPhamRepository.deleteAll(viPhams);

        // 2.5 Tạm vắng
        List<vn.iotstar.dormitory.entity.TamVang> tamVangs = tamVangRepository.findBySinhVien_MaSV(id);
        tamVangRepository.deleteAll(tamVangs);

        // 2.6 Đăng ký KTX
        List<vn.iotstar.dormitory.entity.DangKyKTX> dangKys = dangKyKTXRepository.findBySinhVien_MaSV(id);
        dangKyKTXRepository.deleteAll(dangKys);

        // 2.7 Tin nhắn
        List<vn.iotstar.dormitory.entity.TinNhan> tinNhans = tinNhanRepository.findByMaSVOrderByThoiGianAsc(id);
        tinNhanRepository.deleteAll(tinNhans);

        // 2.8 Thông báo
        List<vn.iotstar.dormitory.entity.ThongBao> thongBaos = thongBaoRepository.findByNguoiNhan(id);
        thongBaoRepository.deleteAll(thongBaos);
        if (sv.getEmail() != null) {
            List<vn.iotstar.dormitory.entity.ThongBao> thongBaosEmail = thongBaoRepository.findByNguoiNhan(sv.getEmail());
            thongBaoRepository.deleteAll(thongBaosEmail);
            
            // 2.9 OTP xác thực
            List<vn.iotstar.dormitory.entity.OtpXacThuc> otps = otpXacThucRepository.findByEmail(sv.getEmail());
            otpXacThucRepository.deleteAll(otps);
        }

        // 3. Cuối cùng, xóa sinh viên
        sinhVienRepository.delete(sv);
    }

    public synchronized String generateNextMaSV() {
        long count = sinhVienRepository.count();
        String generatedId = String.format("SV%04d", count + 1);
        while (sinhVienRepository.existsById(generatedId)) {
            count++;
            generatedId = String.format("SV%04d", count + 1);
        }
        return generatedId;
    }

    public SinhVien register(RegisterDTO dto) {
        // Kiểm tra mật khẩu khớp nhau
        if (!dto.getMatKhau().equals(dto.getXacNhanMatKhau())) {
            throw new RuntimeException("Mật khẩu xác nhận không khớp.");
        }

        // Kiểm tra xem email đã có tài khoản hoạt động chưa
        Optional<SinhVien> existingOpt = sinhVienRepository.findByEmail(dto.getEmail());
        SinhVien sv;
        if (existingOpt.isPresent()) {
            sv = existingOpt.get();
            if ("Hoạt động".equalsIgnoreCase(sv.getTrangThai())) {
                throw new RuntimeException("Email này đã được đăng ký tài khoản trên hệ thống.");
            }
            // Nếu tài khoản cũ chưa kích hoạt, cập nhật lại thông tin
            sv.setHoTen(dto.getHoTen());
            sv.setSdt(dto.getSdt());
            sv.setTruongDaiHoc(dto.getTruongDaiHoc());
            sv.setMatKhau(passwordEncoder.encode(dto.getMatKhau()));
        } else {
            sv = new SinhVien();
            sv.setMaSV(generateNextMaSV());
            sv.setHoTen(dto.getHoTen());
            sv.setEmail(dto.getEmail());
            sv.setSdt(dto.getSdt());
            sv.setTruongDaiHoc(dto.getTruongDaiHoc());
            sv.setMatKhau(passwordEncoder.encode(dto.getMatKhau()));
            sv.setTrangThai("Chưa kích hoạt");
        }

        sinhVienRepository.save(sv);

        // Gửi OTP kích hoạt
        otpService.generateAndSendOtp(dto.getEmail(), "DANG_KY", "Kích hoạt tài khoản Ký túc xá");

        return sv;
    }

    public SinhVien activateAccount(String email, String otpCode) {
        boolean valid = otpService.verifyOtp(email, otpCode, "DANG_KY");
        if (!valid) {
            throw new RuntimeException("Mã OTP không chính xác hoặc đã hết hiệu lực (5 phút).");
        }

        Optional<SinhVien> svOpt = sinhVienRepository.findByEmail(email);
        if (svOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy thông tin tài khoản cần kích hoạt.");
        }

        SinhVien sv = svOpt.get();
        sv.setTrangThai("Hoạt động");
        return sinhVienRepository.save(sv);
    }

    public String sendForgotPasswordOtp(String emailOrMaSV) {
        SinhVien sv = sinhVienRepository.findByMaSV(emailOrMaSV);
        if (sv == null) {
            Optional<SinhVien> svOpt = sinhVienRepository.findByEmail(emailOrMaSV);
            if (svOpt.isPresent()) {
                sv = svOpt.get();
            }
        }

        if (sv == null) {
            throw new RuntimeException("Không tìm thấy tài khoản sinh viên với thông tin đã nhập.");
        }

        if (sv.getEmail() == null || sv.getEmail().isEmpty()) {
            throw new RuntimeException("Tài khoản chưa được cập nhật Email, vui lòng liên hệ BQL KTX để được hỗ trợ.");
        }

        otpService.generateAndSendOtp(sv.getEmail(), "QUEN_MAT_KHAU", "Đặt lại mật khẩu đăng nhập");
        return sv.getEmail();
    }

    public void resetPassword(ResetPasswordDTO dto) {
        if (!dto.getMatKhauMoi().equals(dto.getXacNhanMatKhau())) {
            throw new RuntimeException("Mật khẩu xác nhận không khớp.");
        }

        boolean valid = otpService.verifyOtp(dto.getEmail(), dto.getOtpCode(), "QUEN_MAT_KHAU");
        if (!valid) {
            throw new RuntimeException("Mã OTP không chính xác hoặc đã hết hiệu lực.");
        }

        Optional<SinhVien> svOpt = sinhVienRepository.findByEmail(dto.getEmail());
        if (svOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy tài khoản sinh viên.");
        }

        SinhVien sv = svOpt.get();
        sv.setMatKhau(passwordEncoder.encode(dto.getMatKhauMoi()));
        sinhVienRepository.save(sv);
    }

    public void save(SinhVienDTO dto) {
        boolean isNew = false;
        SinhVien sv = sinhVienRepository.findById(dto.getMaSV()).orElseGet(() -> new SinhVien());
        if (sv.getMaSV() == null) {
            isNew = true;
        }
        
        sv.setMaSV(dto.getMaSV());
        sv.setHoTen(dto.getHoTen());
        sv.setNgaySinh(dto.getNgaySinh());
        sv.setGioiTinh(dto.getGioiTinh());
        sv.setQueQuan(dto.getQueQuan());
        sv.setCccd(dto.getCccd());
        sv.setSdt(dto.getSdt());
        sv.setEmail(dto.getEmail());
        sv.setKhoa(dto.getKhoa());
        sv.setTruongDaiHoc(dto.getTruongDaiHoc());
        sv.setNamHoc(dto.getNamHoc());
        sv.setDienUuTien(dto.getDienUuTien());
        sv.setTrangThai("Hoạt động");
        
        // Cấp mật khẩu mặc định nếu chưa có
        String plainPassword = null;
        if (dto.getMatKhau() != null && !dto.getMatKhau().isEmpty()) {
            plainPassword = dto.getMatKhau();
            sv.setMatKhau(passwordEncoder.encode(plainPassword));
        } else if (sv.getMatKhau() == null) {
            plainPassword = "123456";
            sv.setMatKhau(passwordEncoder.encode(plainPassword));
        }
        
        sinhVienRepository.save(sv);
        
        // Gửi email thông báo tài khoản nếu là sinh viên mới tạo và có email
        if (isNew && sv.getEmail() != null && !sv.getEmail().isEmpty()) {
            String subject = "Thông báo: Tài khoản cư dân Ký túc xá";
            String body = "Chào " + sv.getHoTen() + ",\n\n" +
                          "Hồ sơ của bạn đã được khởi tạo tài khoản trên hệ thống Ký túc xá.\n" +
                          "Dưới đây là thông tin đăng nhập hệ thống:\n" +
                          "- Tên đăng nhập (Mã SV): " + sv.getMaSV() + "\n" +
                          "- Mật khẩu: " + (plainPassword != null ? plainPassword : "Đã được mã hóa") + "\n\n" +
                          "Vui lòng đăng nhập vào hệ thống để kiểm tra thông tin và các hóa đơn hàng tháng.\n" +
                          "Trân trọng,\nBan quản lý KTX.";
            emailService.sendEmail(sv.getEmail(), subject, body);
        }
    }
}
