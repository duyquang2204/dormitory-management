package vn.iotstar.dormitory.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.dormitory.dto.KhuVucChoTrongDTO;
import vn.iotstar.dormitory.dto.NopDonOnlineDTO;
import vn.iotstar.dormitory.entity.DangKyKTX;
import vn.iotstar.dormitory.entity.Phong;
import vn.iotstar.dormitory.entity.SinhVien;
import vn.iotstar.dormitory.service.DangKyKTXService;
import vn.iotstar.dormitory.service.PhongService;
import vn.iotstar.dormitory.service.SinhVienService;
import vn.iotstar.dormitory.service.ThongBaoService;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private ThongBaoService thongBaoService;

    @Autowired
    private PhongService phongService;

    @Autowired
    private DangKyKTXService dangKyKTXService;

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            for (GrantedAuthority authority : auth.getAuthorities()) {
                String role = authority.getAuthority();
                if (role.equals("ROLE_QUAN_TRI")) {
                    return "redirect:/admin/dashboard";
                } else if (role.equals("ROLE_QUAN_SINH")) {
                    return "redirect:/quansinh/dashboard";
                } else if (role.equals("ROLE_NHAN_VIEN")) {
                    return "redirect:/nhanvien/dashboard";
                } else if (role.equals("ROLE_SINH_VIEN")) {
                    return "redirect:/sinhvien/dashboard";
                }
            }
        }
        return prepareHomeModel(model);
    }

    @GetMapping("/home")
    public String home(Model model) {
        return prepareHomeModel(model);
    }

    private String prepareHomeModel(Model model) {
        model.addAttribute("loaiPhongs", phongService.findAllLoaiPhong());
        model.addAttribute("khus", phongService.findAllKhu());
        
        List<Phong> allRooms = phongService.findAll();
        long phongTrong = allRooms.stream()
                .filter(p -> p.getSoChoTrong() > 0 && !"Đã đầy".equalsIgnoreCase(p.getTrangThai()))
                .count();
        model.addAttribute("totalPhong", allRooms.size());
        model.addAttribute("phongConCho", phongTrong);
        return "home";
    }

    // --- 1. TRA CỨU TÌNH TRẠNG CHỖ TRỐNG & HẠNG PHÒNG LƯU TRÚ (PUBLIC CHO KHÁCH) ---
    @GetMapping("/tra-cuu-phong")
    public String traCuuPhong(
            @RequestParam(value = "maKhu", required = false) String maKhu,
            @RequestParam(value = "gioiTinh", required = false) String gioiTinh,
            Model model) {

        List<Phong> allRooms = phongService.findAll();

        // 6 khu vực phân tầng lưu trú chuẩn của KTX
        List<KhuVucChoTrongDTO> allKhuVuc = new java.util.ArrayList<>();

        allKhuVuc.add(buildKhuVucDTO("A", "Khu A", "Nữ", "Tầng 1 & 2 (Dành cho Nữ)", "LP08", "Phòng 8 người", 8, 800000.0,
                "Quạt trần công suất lớn, giường tầng khung thép, bàn học & giá sách, tủ cá nhân an toàn", allRooms));

        allKhuVuc.add(buildKhuVucDTO("A", "Khu A", "Nam", "Tầng 3 & 4 (Dành cho Nam)", "LP08", "Phòng 8 người", 8, 800000.0,
                "Quạt trần công suất lớn, giường tầng khung thép, bàn học & giá sách, tủ cá nhân an toàn", allRooms));

        allKhuVuc.add(buildKhuVucDTO("B", "Khu B", "Nữ", "Tầng 1 & 2 (Dành cho Nữ)", "LP06", "Phòng 6 người", 6, 1000000.0,
                "Bình nóng lạnh tự động, quạt trần làm mát, giường tầng nệm cao cấp, bàn học cá nhân", allRooms));

        allKhuVuc.add(buildKhuVucDTO("B", "Khu B", "Nam", "Tầng 3 & 4 (Dành cho Nam)", "LP06", "Phòng 6 người", 6, 1000000.0,
                "Bình nóng lạnh tự động, quạt trần làm mát, giường tầng nệm cao cấp, bàn học cá nhân", allRooms));

        allKhuVuc.add(buildKhuVucDTO("C", "Khu C", "Nữ", "Tầng 1 & 2 (Dành cho Nữ)", "LP04", "Phòng 4 người cao cấp", 4, 1500000.0,
                "Máy lạnh Inverter 24/24, Bình nóng lạnh cao cấp, quạt trần, nệm êm ái, tủ đồ âm tường", allRooms));

        allKhuVuc.add(buildKhuVucDTO("C", "Khu C", "Nam", "Tầng 3 & 4 (Dành cho Nam)", "LP04", "Phòng 4 người cao cấp", 4, 1500000.0,
                "Máy lạnh Inverter 24/24, Bình nóng lạnh cao cấp, quạt trần, nệm êm ái, tủ đồ âm tường", allRooms));

        // Thống kê tổng thể toàn hệ thống
        long tongChoToanKTX = allKhuVuc.stream().mapToLong(KhuVucChoTrongDTO::getTongSoCho).sum();
        long tongDangO = allKhuVuc.stream().mapToLong(KhuVucChoTrongDTO::getSoNguoiDangO).sum();
        long tongConTrong = allKhuVuc.stream().mapToLong(KhuVucChoTrongDTO::getSoChoConTrong).sum();
        int tyLeLapDayToanKTX = tongChoToanKTX > 0 ? (int) Math.round((double) tongDangO * 100 / tongChoToanKTX) : 0;
        int tongSoPhongToanKTX = allKhuVuc.stream().mapToInt(KhuVucChoTrongDTO::getTongSoPhong).sum();

        // Lọc theo điều kiện tìm kiếm nếu có
        List<KhuVucChoTrongDTO> filteredKhuVuc = allKhuVuc.stream()
                .filter(k -> {
                    boolean matchKhu = (maKhu == null || maKhu.trim().isEmpty() || "ALL".equalsIgnoreCase(maKhu))
                            || k.getMaKhu().equalsIgnoreCase(maKhu);
                    boolean matchGender = (gioiTinh == null || gioiTinh.trim().isEmpty() || "ALL".equalsIgnoreCase(gioiTinh))
                            || k.getGioiTinh().equalsIgnoreCase(gioiTinh);
                    return matchKhu && matchGender;
                })
                .toList();

        model.addAttribute("khuVucs", filteredKhuVuc);
        model.addAttribute("tongChoToanKTX", tongChoToanKTX);
        model.addAttribute("tongDangO", tongDangO);
        model.addAttribute("tongConTrong", tongConTrong);
        model.addAttribute("tyLeLapDayToanKTX", tyLeLapDayToanKTX);
        model.addAttribute("tongSoPhongToanKTX", tongSoPhongToanKTX);

        model.addAttribute("selectedKhu", maKhu);
        model.addAttribute("selectedGioiTinh", gioiTinh);
        return "guest/phong_search";
    }

    private KhuVucChoTrongDTO buildKhuVucDTO(String maKhu, String tenKhu, String gioiTinh, String tangText,
                                             String maLoaiPhong, String tenLoaiPhong, int soNguoiPhong,
                                             double donGia, String tienNghi, List<Phong> allRooms) {
        List<Phong> areaRooms = allRooms.stream()
                .filter(p -> p.getKhu() != null && maKhu.equalsIgnoreCase(p.getKhu().getMaKhu())
                        && p.getGioiTinh() != null && gioiTinh.equalsIgnoreCase(p.getGioiTinh()))
                .toList();

        int tongSoPhong = areaRooms.size();
        long tongSoCho = areaRooms.stream().mapToLong(p -> p.getLoaiPhong() != null ? p.getLoaiPhong().getSoNguoiToiDa() : soNguoiPhong).sum();
        long daO = areaRooms.stream().mapToLong(Phong::getSoNguoiHienTai).sum();
        long conTrong = areaRooms.stream().mapToLong(Phong::getSoChoTrong).sum();
        int tyLeLapDay = tongSoCho > 0 ? (int) Math.round((double) daO * 100 / tongSoCho) : 0;
        boolean conNhanDon = conTrong > 0;

        return new KhuVucChoTrongDTO(maKhu, tenKhu, gioiTinh, tangText, maLoaiPhong, tenLoaiPhong,
                soNguoiPhong, donGia, tienNghi, tongSoPhong, tongSoCho, daO, conTrong, tyLeLapDay, conNhanDon);
    }

    // --- 2. NỘP ĐƠN HỒ SƠ LƯU TRÚ KTX ONLINE ---
    @GetMapping("/nop-don")
    public String formNopDon(@RequestParam(value = "loaiPhong", required = false) String preLoaiPhong,
                             Model model) {
        NopDonOnlineDTO dto = new NopDonOnlineDTO();
        if (preLoaiPhong != null && !preLoaiPhong.isEmpty()) {
            dto.setMaLoaiPhong(preLoaiPhong);
        }

        // Nếu đã đăng nhập sinh viên, tự động điền thông tin sẵn có
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            Optional<SinhVien> svOpt = sinhVienService.findById(auth.getName());
            if (svOpt.isPresent()) {
                SinhVien sv = svOpt.get();
                dto.setHoTen(sv.getHoTen());
                dto.setEmail(sv.getEmail());
                dto.setSdt(sv.getSdt());
                dto.setCccd(sv.getCccd());
                dto.setGioiTinh(sv.getGioiTinh());
                dto.setNgaySinh(sv.getNgaySinh());
                dto.setQueQuan(sv.getQueQuan());
                dto.setTruongDaiHoc(sv.getTruongDaiHoc());
                dto.setKhoa(sv.getKhoa());
                dto.setNamHoc(sv.getNamHoc());
                dto.setDienUuTien(sv.getDienUuTien());
            }
        }

        model.addAttribute("nopDonDTO", dto);
        model.addAttribute("loaiPhongs", phongService.findAllLoaiPhong());
        return "guest/nop_don";
    }

    @PostMapping("/nop-don")
    public String handleNopDon(@Valid @ModelAttribute("nopDonDTO") NopDonOnlineDTO dto,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("loaiPhongs", phongService.findAllLoaiPhong());
            return "guest/nop_don";
        }

        try {
            DangKyKTX saved = dangKyKTXService.submitOnlineRegistration(dto);
            redirectAttributes.addFlashAttribute("maDangKy", saved.getMaDangKy());
            redirectAttributes.addFlashAttribute("hoTen", saved.getHoTen());
            redirectAttributes.addFlashAttribute("email", saved.getEmail());
            return "redirect:/nop-don-thanh-cong";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Lỗi khi nộp hồ sơ: " + e.getMessage());
            model.addAttribute("loaiPhongs", phongService.findAllLoaiPhong());
            return "guest/nop_don";
        }
    }

    @GetMapping("/nop-don-thanh-cong")
    public String nopDonThanhCong() {
        return "guest/nop_don_thanh_cong";
    }

    // --- 3. TRA CỨU TIẾN ĐỘ HỒ SƠ ---
    @GetMapping("/tra-cuu-ho-so")
    public String traCuuHoSoForm(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            String kw = keyword.trim().toLowerCase();
            List<DangKyKTX> ketQua = dangKyKTXService.findAll().stream()
                    .filter(dk -> {
                        String ma = dk.getMaDangKy() != null ? dk.getMaDangKy().toLowerCase() : "";
                        String email = dk.getEmail() != null ? dk.getEmail().toLowerCase() : "";
                        String cccd = dk.getCccd() != null ? dk.getCccd().toLowerCase() : "";
                        String sdt = dk.getSdt() != null ? dk.getSdt().toLowerCase() : "";
                        return ma.equals(kw) || email.equals(kw) || cccd.equals(kw) || sdt.equals(kw);
                    })
                    .toList();

            int totalElements = ketQua.size();
            int totalPages = (int) Math.ceil((double) totalElements / size);
            if (totalPages == 0) totalPages = 1;
            if (page < 1) page = 1;
            if (page > totalPages) page = totalPages;

            int start = Math.min((page - 1) * size, totalElements);
            int end = Math.min(start + size, totalElements);
            List<DangKyKTX> pagedList = ketQua.subList(start, end);

            org.springframework.data.domain.Page<DangKyKTX> hoSoPage =
                    new org.springframework.data.domain.PageImpl<>(pagedList, org.springframework.data.domain.PageRequest.of(page - 1, size), totalElements);

            model.addAttribute("hoSoPage", hoSoPage);
            model.addAttribute("danhSachHoSo", hoSoPage.getContent());
            model.addAttribute("searched", true);
            model.addAttribute("keyword", keyword);
        }
        return "guest/tra_cuu_ho_so";
    }

    @GetMapping("/thong-bao/doc/{id}")
    public String docThongBao(@PathVariable("id") String id) {
        String redirectLink = thongBaoService.markAsRead(id);
        return "redirect:" + redirectLink;
    }
}
