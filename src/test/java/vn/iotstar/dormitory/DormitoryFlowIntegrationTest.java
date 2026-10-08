package vn.iotstar.dormitory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import vn.iotstar.dormitory.dto.NopDonOnlineDTO;
import vn.iotstar.dormitory.dto.RegisterDTO;
import vn.iotstar.dormitory.dto.ResetPasswordDTO;
import vn.iotstar.dormitory.entity.DangKyKTX;
import vn.iotstar.dormitory.entity.Phong;
import vn.iotstar.dormitory.entity.SinhVien;
import vn.iotstar.dormitory.repository.*;
import vn.iotstar.dormitory.service.DangKyKTXService;
import vn.iotstar.dormitory.service.SinhVienService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
public class DormitoryFlowIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        if (sinhVienRepository.findById("SV001").isEmpty()) {
            SinhVien sv = new SinhVien();
            sv.setMaSV("SV001");
            sv.setHoTen("Nguyễn Duy Quang");
            sv.setEmail("duyquang22042005@gmail.com");
            sv.setSdt("0988776655");
            sv.setGioiTinh("Nam");
            sv.setQueQuan("TP. Hồ Chí Minh");
            sv.setCccd("079205001234");
            sv.setKhoa("Công nghệ Thông tin");
            sv.setNamHoc(2023);
            sv.setNgaySinh(LocalDate.of(2005, 4, 22));
            sv.setTruongDaiHoc("Đại học Sư phạm Kỹ thuật TP.HCM");
            sv.setTrangThai("Hoạt động");
            sinhVienRepository.save(sv);
        }
    }

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private DangKyKTXService dangKyKTXService;

    @Autowired
    private DangKyKTXRepository dangKyKTXRepository;

    @Autowired
    private PhongRepository phongRepository;

    @Autowired
    private LoaiPhongRepository loaiPhongRepository;

    @Autowired
    private OtpXacThucRepository otpRepository;

    // =========================================================================
    // 1. GUEST FLOW TESTS
    // =========================================================================

    @Test
    @DisplayName("Guest 1: Trang chủ KTX hiển thị thành công")
    void testGuestLandingPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("loaiPhongs"))
                .andExpect(model().attributeExists("khus"))
                .andExpect(model().attributeExists("totalPhong"))
                .andExpect(model().attributeExists("phongConCho"));
    }

    @Test
    @DisplayName("Guest 2: Tra cứu và lọc phòng công khai")
    void testTraCuuPhong() throws Exception {
        mockMvc.perform(get("/tra-cuu-phong"))
                .andExpect(status().isOk())
                .andExpect(view().name("guest/phong_search"))
                .andExpect(model().attributeExists("khuVucs"))
                .andExpect(model().attributeExists("tongChoToanKTX"))
                .andExpect(model().attributeExists("tongConTrong"));
    }

    @Test
    @DisplayName("Guest 3: Nộp đơn đăng ký ở ký túc xá online")
    void testNopDonOnlineFlow() {
        NopDonOnlineDTO dto = new NopDonOnlineDTO();
        dto.setHoTen("Lê Văn Test");
        dto.setEmail("levantest@test.edu.vn");
        dto.setSdt("0912345678");
        dto.setGioiTinh("Nam");
        dto.setCccd("079205999888");
        dto.setQueQuan("Bình Dương");
        dto.setTruongDaiHoc("Đại học Bách Khoa");
        dto.setKhoa("Khoa Điện - Điện tử");
        dto.setNamHoc(2024);
        dto.setNgaySinh(LocalDate.of(2005, 5, 15));
        dto.setMaLoaiPhong(loaiPhongRepository.findAll().getFirst().getMaLoaiPhong());

        DangKyKTX dk = dangKyKTXService.submitOnlineRegistration(dto);
        assertNotNull(dk.getMaDangKy());
        assertEquals("Chờ duyệt", dk.getTrangThai());
        assertEquals("Lê Văn Test", dk.getHoTen());

        // Kiểm tra tra cứu hồ sơ
        var searchResult = dangKyKTXService.search("079205999888", null, null);
        assertFalse(searchResult.isEmpty());
        assertEquals("Lê Văn Test", searchResult.getFirst().getHoTen());
    }

    @Test
    @DisplayName("Guest 4: Tra cứu hồ sơ qua web controller")
    void testTraCuuHoSoController() throws Exception {
        mockMvc.perform(get("/tra-cuu-ho-so").param("keyword", "079205001234"))
                .andExpect(status().isOk())
                .andExpect(view().name("guest/tra_cuu_ho_so"))
                .andExpect(model().attributeExists("danhSachHoSo"));
    }

    // =========================================================================
    // 2. AUTHENTICATION & OTP FLOW TESTS
    // =========================================================================

    @Test
    @DisplayName("Auth 1: Đăng ký tài khoản Sinh viên và sinh OTP")
    void testRegisterAndOtpGeneration() {
        RegisterDTO dto = new RegisterDTO();
        dto.setHoTen("Nguyễn Thị Test");
        dto.setEmail("nguyenthitest@gmail.com");
        dto.setMatKhau("123456");
        dto.setXacNhanMatKhau("123456");
        dto.setSdt("0909123456");
        dto.setTruongDaiHoc("Đại học Sư phạm Kỹ thuật");

        SinhVien sv = sinhVienService.register(dto);
        assertNotNull(sv.getMaSV());
        assertEquals("Chưa kích hoạt", sv.getTrangThai());

        // Kiểm tra OTP được tạo
        var otpOpt = otpRepository.findTopByEmailAndLoaiOtpAndDaSuDungFalseOrderByNgayTaoDesc("nguyenthitest@gmail.com", "DANG_KY");
        assertTrue(otpOpt.isPresent());
        String code = otpOpt.get().getOtpCode();
        assertEquals(6, code.length());

        // Kích hoạt tài khoản bằng OTP
        SinhVien activated = sinhVienService.activateAccount("nguyenthitest@gmail.com", code);
        assertNotNull(activated);
        assertEquals("Hoạt động", activated.getTrangThai());
    }

    @Test
    @DisplayName("Auth 2: Quên mật khẩu và đặt lại mật khẩu với OTP")
    void testForgotPasswordAndResetFlow() {
        // Tạo sinh viên có sẵn
        RegisterDTO dto = new RegisterDTO();
        dto.setHoTen("Trần Quên Pass");
        dto.setEmail("quenpass@test.com");
        dto.setMatKhau("OldPass123");
        dto.setXacNhanMatKhau("OldPass123");
        dto.setSdt("0933333333");
        dto.setTruongDaiHoc("Đại học Quốc gia");

        SinhVien sv = sinhVienService.register(dto);
        sv.setTrangThai("Hoạt động");
        sinhVienRepository.save(sv);

        // Yêu cầu OTP quên mật khẩu
        String sentEmail = sinhVienService.sendForgotPasswordOtp("quenpass@test.com");
        assertEquals("quenpass@test.com", sentEmail);

        var otpOpt = otpRepository.findTopByEmailAndLoaiOtpAndDaSuDungFalseOrderByNgayTaoDesc("quenpass@test.com", "QUEN_MAT_KHAU");
        assertTrue(otpOpt.isPresent());
        String otpCode = otpOpt.get().getOtpCode();

        // Đặt lại mật khẩu
        ResetPasswordDTO resetDto = new ResetPasswordDTO();
        resetDto.setEmail("quenpass@test.com");
        resetDto.setOtpCode(otpCode);
        resetDto.setMatKhauMoi("NewPass456");
        resetDto.setXacNhanMatKhau("NewPass456");

        assertDoesNotThrow(() -> sinhVienService.resetPassword(resetDto));
    }

    @Test
    @DisplayName("Auth 3: Trang đăng nhập hiển thị thành công")
    void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    // =========================================================================
    // 3. QUẢN SINH (MANAGER) FLOW TESTS
    // =========================================================================

    @Test
    @WithMockUser(username = "quansinh", roles = {"QUAN_SINH"})
    @DisplayName("Quản sinh 1: Xem danh sách đơn đăng ký online")
    void testQuanSinhViewApplications() throws Exception {
        mockMvc.perform(get("/quansinh/dang-ky"))
                .andExpect(status().isOk())
                .andExpect(view().name("quansinh/dangky_list"))
                .andExpect(model().attributeExists("dangKyList"))
                .andExpect(model().attributeExists("loaiPhongs"));
    }

    @Test
    @DisplayName("Quản sinh 2: Duyệt đơn và xếp phòng tự động")
    void testQuanSinhApproveApplication() {
        // Tạo đơn đăng ký
        NopDonOnlineDTO dto = new NopDonOnlineDTO();
        dto.setHoTen("Nguyễn Văn Duyệt");
        dto.setEmail("vanduyet@gmail.com");
        dto.setSdt("0988111222");
        dto.setGioiTinh("Nam");
        dto.setCccd("079203333444");
        dto.setQueQuan("Đồng Nai");
        dto.setTruongDaiHoc("Đại học Sư phạm Kỹ thuật");
        dto.setKhoa("Cơ khí");
        dto.setNamHoc(2023);
        dto.setNgaySinh(LocalDate.of(2005, 1, 1));
        dto.setMaLoaiPhong(loaiPhongRepository.findAll().getFirst().getMaLoaiPhong());

        DangKyKTX dk = dangKyKTXService.submitOnlineRegistration(dto);

        // Tìm 1 phòng trống phù hợp giới tính
        Phong phong = phongRepository.findAll().stream()
                .filter(p -> "Trống".equals(p.getTrangThai()) && (dto.getGioiTinh() == null || dto.getGioiTinh().equalsIgnoreCase(p.getGioiTinh())))
                .findFirst()
                .orElse(null);

        if (phong != null) {
            dangKyKTXService.approveRegistration(dk.getMaDangKy(), phong.getMaPhong(), LocalDate.now(), LocalDate.now().plusMonths(6));
            DangKyKTX updatedDk = dangKyKTXRepository.findById(dk.getMaDangKy()).orElseThrow();
            assertEquals("Đã duyệt", updatedDk.getTrangThai());
            assertNotNull(updatedDk.getSinhVien());
            assertEquals("vanduyet@gmail.com", updatedDk.getSinhVien().getEmail());
        }
    }

    @Test
    @DisplayName("Quản sinh 3: Từ chối đơn có lý do rõ ràng")
    void testQuanSinhRejectApplication() {
        NopDonOnlineDTO dto = new NopDonOnlineDTO();
        dto.setHoTen("Lê Thị Từ Chối");
        dto.setEmail("tuchoi@test.com");
        dto.setSdt("0977889900");
        dto.setGioiTinh("Nữ");
        dto.setCccd("079204444555");
        dto.setQueQuan("Vũng Tàu");
        dto.setTruongDaiHoc("Đại học Mở");
        dto.setKhoa("Kinh tế");
        dto.setNamHoc(2024);
        dto.setNgaySinh(LocalDate.of(2005, 3, 10));
        dto.setMaLoaiPhong(loaiPhongRepository.findAll().getFirst().getMaLoaiPhong());

        DangKyKTX dk = dangKyKTXService.submitOnlineRegistration(dto);
        dangKyKTXService.rejectRegistration(dk.getMaDangKy(), "Hình ảnh thẻ sinh viên bị mờ không rõ ràng");

        DangKyKTX rejected = dangKyKTXRepository.findById(dk.getMaDangKy()).orElseThrow();
        assertEquals("Từ chối", rejected.getTrangThai());
        assertEquals("Hình ảnh thẻ sinh viên bị mờ không rõ ràng", rejected.getLyDoTuChoi());
    }

    // =========================================================================
    // 4. STUDENT (SINH VIÊN) FLOW TESTS
    // =========================================================================

    @Test
    @WithMockUser(username = "SV001", roles = {"SINH_VIEN"})
    @DisplayName("Sinh viên 1: Xem thông tin cá nhân (Profile)")
    void testStudentProfileView() throws Exception {
        mockMvc.perform(get("/sinhvien/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("sinhvien/profile"))
                .andExpect(model().attributeExists("sinhVien"));
    }

    @Test
    @WithMockUser(username = "SV001", roles = {"SINH_VIEN"})
    @DisplayName("Sinh viên 2: Cập nhật thông tin cá nhân kèm bổ sung Trường & Khoa")
    void testStudentProfileUpdate() throws Exception {
        mockMvc.perform(post("/sinhvien/profile/cap-nhat")
                        .with(csrf())
                        .param("sdt", "0988776655")
                        .param("email", "duyquang22042005@gmail.com")
                        .param("queQuan", "TP. Hồ Chí Minh")
                        .param("diaChi", "123 Võ Văn Ngân, Thủ Đức")
                        .param("truongDaiHoc", "ĐH Sư Phạm Kỹ Thuật TP.HCM")
                        .param("khoa", "Công nghệ Thông tin")
                        .param("sdtPhuHuynh", "0909000111")
                        .param("hoTenPhuHuynh", "Nguyễn Văn Phụ Huynh"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sinhvien/profile"));

        SinhVien sv = sinhVienRepository.findById("SV001").orElseThrow();
        assertEquals("123 Võ Văn Ngân, Thủ Đức", sv.getDiaChi());
        assertEquals("0909000111", sv.getSdtPhuHuynh());
        assertEquals("ĐH Sư Phạm Kỹ Thuật TP.HCM", sv.getTruongDaiHoc());
        assertEquals("Công nghệ Thông tin", sv.getKhoa());
    }

    @Test
    @WithMockUser(username = "SV001", roles = {"SINH_VIEN"})
    @DisplayName("Sinh viên 3: Xem danh sách hóa đơn")
    void testStudentInvoiceList() throws Exception {
        mockMvc.perform(get("/sinhvien/hoa-don"))
                .andExpect(status().isOk())
                .andExpect(view().name("sinhvien/hoadon_list"))
                .andExpect(model().attributeExists("hoaDonPage"));
    }

    // =========================================================================
    // 5. ADMIN FLOW TESTS
    // =========================================================================

    @Test
    @WithMockUser(username = "admin", roles = {"QUAN_TRI"})
    @DisplayName("Admin 1: Truy cập Dashboard thống kê doanh thu và báo cáo")
    void testAdminDashboard() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/dashboard"))
                .andExpect(model().attributeExists("totalUsers"))
                .andExpect(model().attributeExists("totalStudents"))
                .andExpect(model().attributeExists("revenueData"));
    }

    // =========================================================================
    // 6. THREE ADVANCED FEATURES TESTS (WEBSOCKET NOTIFICATION, PDF EXPORT, LIVE CHAT)
    // =========================================================================

    @Autowired
    private vn.iotstar.dormitory.service.ThongBaoService thongBaoService;

    @Autowired
    private ThongBaoRepository thongBaoRepository;

    @Autowired
    private HopDongRepository hopDongRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private PhanPhongRepository phanPhongRepository;

    @Test
    @DisplayName("Ý tưởng 1: Thông báo đẩy tức thời (Push Notification) hoạt động chính xác")
    void testRealtimeNotificationPush() {
        thongBaoService.taoThongBao("SV001", "Thông báo thử nghiệm realtime", "Nội dung kiểm thử", "TEST", "/link");
        assertTrue(thongBaoRepository.existsByNguoiNhanAndLoaiThongBaoAndTieuDe("SV001", "TEST", "Thông báo thử nghiệm realtime"));
    }

    @Test
    @WithMockUser(username = "quansinh", roles = {"QUAN_SINH"})
    @DisplayName("Ý tưởng 2: Quản sinh xuất file PDF Hợp đồng và Hóa đơn")
    void testExportPdfAsManager() throws Exception {
        SinhVien sv = sinhVienRepository.findById("SV001").orElseThrow();
        Phong p = phongRepository.findAll().stream().findFirst().orElseThrow();

        // Tạo phân phòng và hợp đồng
        vn.iotstar.dormitory.entity.PhanPhong pp = new vn.iotstar.dormitory.entity.PhanPhong();
        pp.setMaPhanPhong("PP_TEST_PDF");
        pp.setSinhVien(sv);
        pp.setPhong(p);
        pp.setTrangThai("Đang ở");
        phanPhongRepository.save(pp);

        vn.iotstar.dormitory.entity.HopDong hd = new vn.iotstar.dormitory.entity.HopDong();
        hd.setMaHopDong("HD_TEST_PDF");
        hd.setPhanPhong(pp);
        hd.setNgayLap(LocalDate.now());
        hd.setNgayBatDau(LocalDate.now());
        hd.setNgayKetThuc(LocalDate.now().plusMonths(12));
        hd.setGiaTien(1500000.0);
        hd.setTrangThai("Còn hạn");
        hopDongRepository.save(hd);

        // Xuất PDF Hợp đồng
        mockMvc.perform(get("/quansinh/hop-dong/xuat-pdf/HD_TEST_PDF"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/pdf"));

        // Tạo hóa đơn
        vn.iotstar.dormitory.entity.HoaDon hoadon = new vn.iotstar.dormitory.entity.HoaDon();
        hoadon.setMaHoaDon("HDON_TEST_PDF");
        hoadon.setSinhVien(sv);
        hoadon.setPhong(p);
        hoadon.setThang(10);
        hoadon.setNam(2026);
        hoadon.setChiSoDienCu(100.0);
        hoadon.setChiSoDienMoi(120.0);
        hoadon.setTienDien(70000.0);
        hoadon.setChiSoNuocCu(10.0);
        hoadon.setChiSoNuocMoi(12.0);
        hoadon.setTienNuoc(30000.0);
        hoadon.setTienPhong(1200000.0);
        hoadon.setTongTien(1300000.0);
        hoadon.setTrangThai("Chưa thanh toán");
        hoaDonRepository.save(hoadon);

        // Xuất PDF Hóa đơn
        mockMvc.perform(get("/quansinh/hoa-don/xuat-pdf/HDON_TEST_PDF"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/pdf"));
    }

    @Test
    @WithMockUser(username = "SV001", roles = {"SINH_VIEN"})
    @DisplayName("Ý tưởng 2: Sinh viên tải PDF Hóa đơn của chính mình")
    void testExportPdfAsStudent() throws Exception {
        SinhVien sv = sinhVienRepository.findById("SV001").orElseThrow();
        Phong p = phongRepository.findAll().stream().findFirst().orElseThrow();

        vn.iotstar.dormitory.entity.HoaDon hoadon = new vn.iotstar.dormitory.entity.HoaDon();
        hoadon.setMaHoaDon("HDON_SV_TEST");
        hoadon.setSinhVien(sv);
        hoadon.setPhong(p);
        hoadon.setThang(10);
        hoadon.setNam(2026);
        hoadon.setChiSoDienCu(50.0);
        hoadon.setChiSoDienMoi(70.0);
        hoadon.setTienDien(70000.0);
        hoadon.setChiSoNuocCu(5.0);
        hoadon.setChiSoNuocMoi(7.0);
        hoadon.setTienNuoc(30000.0);
        hoadon.setTienPhong(1000000.0);
        hoadon.setTongTien(1100000.0);
        hoadon.setTrangThai("Chưa thanh toán");
        hoaDonRepository.save(hoadon);

        mockMvc.perform(get("/sinhvien/hoa-don/xuat-pdf/HDON_SV_TEST"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/pdf"));
    }

    @Test
    @WithMockUser(username = "SV001", roles = {"SINH_VIEN"})
    @DisplayName("Ý tưởng 3: Live Chat - Sinh viên gửi tin nhắn, lấy lịch sử và quản sinh phản hồi")
    void testLiveChatFlow() throws Exception {
        // Sinh viên gửi tin nhắn
        String chatJson = "{\"maSV\":\"SV001\",\"nguoiGui\":\"SV001\",\"tenNguoiGui\":\"Nguyễn Duy Quang\",\"vaiTroGui\":\"SINH_VIEN\",\"noiDung\":\"Xin chào ban quản lý KTX!\"}";
        mockMvc.perform(post("/api/chat/send")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(chatJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maSV").value("SV001"))
                .andExpect(jsonPath("$.noiDung").value("Xin chào ban quản lý KTX!"));

        // Sinh viên lấy lịch sử chat
        mockMvc.perform(get("/api/chat/history?maSV=SV001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].noiDung").value("Xin chào ban quản lý KTX!"));

        // Quản sinh đánh dấu đã đọc
        mockMvc.perform(post("/api/chat/read?maSV=SV001&vaiTro=QUAN_SINH"))
                .andExpect(status().isOk());

        // Lấy danh sách hội thoại
        mockMvc.perform(get("/api/chat/conversations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].maSV").value("SV001"));
    }

    @Test
    @WithMockUser(username = "quansinh", roles = {"QUAN_SINH"})
    @DisplayName("Ý tưởng 3: Live Chat - Quản sinh truy cập trung tâm chat trực tuyến")
    void testManagerChatPortal() throws Exception {
        mockMvc.perform(get("/quansinh/chat"))
                .andExpect(status().isOk())
                .andExpect(view().name("quansinh/chat"))
                .andExpect(model().attributeExists("selectedMaSV"));
    }

    @Test
    @WithMockUser(username = "SV001", roles = {"SINH_VIEN"})
    @DisplayName("Ý tưởng 3: Live Chat - Sinh viên truy cập /sinhvien/chat chuyển hướng sang dashboard kèm tham số mở chatbox")
    void testStudentChatRedirect() throws Exception {
        mockMvc.perform(get("/sinhvien/chat"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sinhvien/dashboard?openChat=true"));
    }

    @Test
    @WithMockUser(username = "SV001", roles = {"SINH_VIEN"})
    @DisplayName("Ý tưởng 3: Live Chat - API kiểm tra số lượng tin nhắn chưa đọc")
    void testChatUnreadCountApi() throws Exception {
        mockMvc.perform(get("/api/chat/unread-count")
                        .param("vaiTro", "SINH_VIEN")
                        .param("maSV", "SV001"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/chat/unread-count")
                        .param("vaiTro", "QUAN_SINH"))
                .andExpect(status().isOk());
    }
}
