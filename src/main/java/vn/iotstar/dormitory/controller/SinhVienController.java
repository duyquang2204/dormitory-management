package vn.iotstar.dormitory.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.dormitory.dto.DangKyKTXDTO;
import vn.iotstar.dormitory.service.DangKyKTXService;
import vn.iotstar.dormitory.service.PhongService;
import vn.iotstar.dormitory.service.PhanPhongService;

@Controller
@RequestMapping("/sinhvien")
public class SinhVienController {

    @Autowired
    private DangKyKTXService dangKyKTXService;
    
    @Autowired
    private PhongService phongService;
    
    @Autowired
    private PhanPhongService phanPhongService;

    @Autowired
    private vn.iotstar.dormitory.service.YeuCauSuaChuaService yeuCauSuaChuaService;

    @Autowired
    private vn.iotstar.dormitory.service.HopDongService hopDongService;

    @Autowired
    private vn.iotstar.dormitory.service.HoaDonService hoaDonService;

    @Autowired
    private vn.iotstar.dormitory.service.VNPAYService vnpayService;

    @Autowired
    private vn.iotstar.dormitory.service.ViPhamService viPhamService;

    @Autowired
    private vn.iotstar.dormitory.service.SinhVienService sinhVienService;

    @Autowired
    private vn.iotstar.dormitory.service.PdfExportService pdfExportService;

    @Autowired
    private vn.iotstar.dormitory.repository.SinhVienRepository sinhVienRepository;

    @Autowired
    private vn.iotstar.dormitory.repository.PhanPhongRepository phanPhongRepository;

    @Autowired
    private vn.iotstar.dormitory.repository.HoaDonRepository hoaDonRepository;

    @Autowired
    private vn.iotstar.dormitory.repository.HopDongRepository hopDongRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        String maSV = auth.getName();
        
        // 1. Thông tin sinh viên
        vn.iotstar.dormitory.entity.SinhVien sv = sinhVienRepository.findById(maSV).orElse(null);
        model.addAttribute("sinhVien", sv);

        // 2. Tìm phân phòng hiện tại đang ở (đồng bộ theo hợp đồng còn hạn mới nhất)
        java.util.Optional<vn.iotstar.dormitory.entity.PhanPhong> ppOpt = phanPhongService.findBySinhVienId(maSV);
        
        if (ppOpt.isPresent()) {
            vn.iotstar.dormitory.entity.PhanPhong currentPp = ppOpt.get();
            vn.iotstar.dormitory.entity.Phong currentRoom = currentPp.getPhong();
            model.addAttribute("currentPp", currentPp);
            model.addAttribute("currentRoom", currentRoom);
            
            // Bạn cùng phòng đang ở cùng phòng này
            java.util.List<vn.iotstar.dormitory.entity.PhanPhong> roommates = phanPhongRepository.findByPhong_MaPhongAndTrangThai(currentRoom.getMaPhong(), "Đang ở")
                    .stream()
                    .filter(p -> p.getSinhVien() != null && !p.getSinhVien().getMaSV().equals(maSV))
                    .toList();
            model.addAttribute("roommates", roommates);

            // Hợp đồng lưu trú
            java.util.List<vn.iotstar.dormitory.entity.HopDong> contracts = hopDongService.findBySinhVienId(maSV);
            vn.iotstar.dormitory.entity.HopDong activeContract = contracts.stream()
                    .filter(hd -> "Còn hạn".equals(hd.getTrangThai()))
                    .findFirst()
                    .orElse(contracts.isEmpty() ? null : contracts.get(0));
            model.addAttribute("activeContract", activeContract);

            // Hóa đơn gần nhất
            java.util.List<vn.iotstar.dormitory.entity.HoaDon> myInvoices = hoaDonRepository.findAllBySinhVien_MaSV(maSV).stream()
                    .sorted((a, b) -> {
                        int c = Integer.compare(b.getNam(), a.getNam());
                        if (c != 0) return c;
                        return Integer.compare(b.getThang(), a.getThang());
                    })
                    .toList();
            vn.iotstar.dormitory.entity.HoaDon latestInvoice = myInvoices.isEmpty() ? null : myInvoices.get(0);
            model.addAttribute("latestInvoice", latestInvoice);

            long unpaidInvoices = hoaDonService.countUnpaidBySinhVien(maSV);
            model.addAttribute("unpaidInvoices", unpaidInvoices);

            // Yêu cầu sửa chữa gần nhất
            java.util.List<vn.iotstar.dormitory.entity.YeuCauSuaChua> repairs = yeuCauSuaChuaService.findBySinhVienId(maSV);
            model.addAttribute("recentRepairs", repairs.stream().limit(3).toList());

        } else {
            model.addAttribute("currentRoom", null);
            model.addAttribute("unpaidInvoices", 0L);

            // Kiểm tra các đơn đăng ký của sinh viên này
            java.util.List<vn.iotstar.dormitory.entity.DangKyKTX> registrations = dangKyKTXService.findBySinhVienId(maSV);
            vn.iotstar.dormitory.entity.DangKyKTX pendingRegistration = registrations.stream()
                    .filter(dk -> "Chờ duyệt".equalsIgnoreCase(dk.getTrangThai()))
                    .findFirst()
                    .orElse(null);
            model.addAttribute("pendingRegistration", pendingRegistration);
            model.addAttribute("latestRegistration", registrations.isEmpty() ? null : registrations.get(registrations.size() - 1));
        }

        // Danh sách loại phòng để sinh viên tham khảo tiện nghi & đơn giá
        model.addAttribute("loaiPhongs", phongService.findAllLoaiPhong());

        // 3. Số vi phạm
        long totalViolations = viPhamService.countByMaSV(maSV);
        model.addAttribute("totalViolations", totalViolations);

        return "sinhvien/dashboard";
    }

    @GetMapping("/dang-ky")
    public String formDangKy(Model model, Authentication auth) {
        String maSV = auth.getName();
        model.addAttribute("dangKyList", dangKyKTXService.findBySinhVienId(maSV));
        model.addAttribute("dangKyKTXDTO", new DangKyKTXDTO());
        model.addAttribute("loaiPhongs", phongService.findAllLoaiPhong());
        return "sinhvien/dangky_form";
    }

    @PostMapping("/dang-ky/luu")
    public String luuDangKy(@Valid @ModelAttribute("dangKyKTXDTO") DangKyKTXDTO dto, BindingResult result, Model model, Authentication auth) {
        String maSV = auth.getName();
        if (result.hasErrors()) {
            model.addAttribute("dangKyList", dangKyKTXService.findBySinhVienId(maSV));
            model.addAttribute("loaiPhongs", phongService.findAllLoaiPhong());
            return "sinhvien/dangky_form";
        }
        try {
            dangKyKTXService.createRegistration(dto, maSV);
            return "redirect:/sinhvien/dang-ky?success";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("dangKyList", dangKyKTXService.findBySinhVienId(maSV));
            model.addAttribute("loaiPhongs", phongService.findAllLoaiPhong());
            return "sinhvien/dangky_form";
        }
    }

    // --- YÊU CẦU SỬA CHỮA ---

    @GetMapping("/sua-chua")
    public String listSuaChua(Model model, Authentication auth) {
        String maSV = auth.getName();
        model.addAttribute("yeuCauList", yeuCauSuaChuaService.findBySinhVienId(maSV));
        model.addAttribute("yeuCauDTO", new vn.iotstar.dormitory.dto.YeuCauSuaChuaDTO());
        
        // Lấy danh sách phòng sinh viên đang ở
        java.util.List<vn.iotstar.dormitory.entity.PhanPhong> myRooms = phanPhongService.findBySinhVienId(maSV).stream().toList();
        model.addAttribute("myRooms", myRooms);
        return "sinhvien/suachua_form";
    }

    @PostMapping("/sua-chua/gui")
    public String guiYeuCauSuaChua(@Valid @ModelAttribute("yeuCauDTO") vn.iotstar.dormitory.dto.YeuCauSuaChuaDTO dto, BindingResult result, Model model, Authentication auth) {
        String maSV = auth.getName();
        if (result.hasErrors()) {
            model.addAttribute("yeuCauList", yeuCauSuaChuaService.findBySinhVienId(maSV));
            model.addAttribute("myRooms", phanPhongService.findBySinhVienId(maSV).stream().toList());
            return "sinhvien/suachua_form";
        }
        yeuCauSuaChuaService.createYeuCau(dto, maSV);
        return "redirect:/sinhvien/sua-chua?success";
    }

    // --- THÔNG TIN PHÒNG VÀ HỢP ĐỒNG ---

    @GetMapping("/phong")
    public String thongTinPhong(Model model, Authentication auth) {
        String maSV = auth.getName();
        
        // 1. Hợp đồng lưu trú (tự động đồng bộ hợp đồng mới nhất còn hạn và hết hạn hợp đồng cũ)
        java.util.List<vn.iotstar.dormitory.entity.HopDong> hopDongs = hopDongService.findBySinhVienId(maSV);
        model.addAttribute("hopDongs", hopDongs);

        // 2. Phân phòng hiện tại đang ở
        java.util.Optional<vn.iotstar.dormitory.entity.PhanPhong> currentPpOpt = phanPhongService.findBySinhVienId(maSV);
        vn.iotstar.dormitory.entity.PhanPhong currentRoomPp = currentPpOpt.orElse(null);
        model.addAttribute("currentRoomPp", currentRoomPp);
        
        // Tương thích cho code cũ sử dụng myRooms:
        if (currentRoomPp != null) {
            model.addAttribute("myRooms", java.util.List.of(currentRoomPp));
        } else {
            model.addAttribute("myRooms", java.util.Collections.emptyList());
        }

        // 3. Lịch sử các phòng từng ở trước đó
        java.util.List<vn.iotstar.dormitory.entity.PhanPhong> oldRooms = phanPhongRepository.findBySinhVien_MaSV(maSV).stream()
                .filter(pp -> currentRoomPp == null || !pp.getMaPhanPhong().equals(currentRoomPp.getMaPhanPhong()))
                .sorted((a, b) -> {
                    if (a.getNgayBatDau() != null && b.getNgayBatDau() != null) {
                        return b.getNgayBatDau().compareTo(a.getNgayBatDau());
                    }
                    return 0;
                })
                .toList();
        model.addAttribute("oldRooms", oldRooms);

        return "sinhvien/phong_info";
    }

    @PostMapping("/hop-dong/yeu-cau")
    public String guiYeuCauHopDong(@RequestParam("maHopDong") String maHopDong, 
                                   @RequestParam("loaiYeuCau") String loaiYeuCau, 
                                   org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        hopDongService.guiYeuCau(maHopDong, loaiYeuCau);
        redirectAttributes.addFlashAttribute("success", "Đã gửi yêu cầu '" + loaiYeuCau + "' thành công!");
        return "redirect:/sinhvien/phong";
    }

    // --- HÓA ĐƠN ĐIỆN NƯỚC ---

    @GetMapping("/hoa-don")
    public String listHoaDon(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Authentication auth, Model model) {
        
        String maSV = auth.getName();
        java.util.List<vn.iotstar.dormitory.entity.PhanPhong> myRooms = phanPhongService.findBySinhVienId(maSV).stream()
            .filter(pp -> "Đang ở".equals(pp.getTrangThai()))
            .toList();
            
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page - 1, size);
        
        if (!myRooms.isEmpty()) {
            model.addAttribute("hoaDonPage", hoaDonService.findBySinhVien(maSV, pageable));
            model.addAttribute("currentRoom", myRooms.get(0).getPhong());
        } else {
            model.addAttribute("hoaDonPage", org.springframework.data.domain.Page.empty());
        }
        
        return "sinhvien/hoadon_list";
    }

    @GetMapping("/vnpay/thanh-toan")
    public String thanhToanVNPAY(@RequestParam("maHoaDon") String maHoaDon, 
                                 jakarta.servlet.http.HttpServletRequest request,
                                 org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        java.util.Optional<vn.iotstar.dormitory.entity.HoaDon> hdOpt = hoaDonService.findById(maHoaDon);
        if (hdOpt.isPresent()) {
            vn.iotstar.dormitory.entity.HoaDon hd = hdOpt.get();
            if ("Đã thanh toán".equals(hd.getTrangThai())) {
                redirectAttributes.addFlashAttribute("error", "Hóa đơn này đã được thanh toán!");
                return "redirect:/sinhvien/hoa-don";
            }
            long amount = hd.getTongTien() != null ? hd.getTongTien().longValue() : 0L;
            String orderInfo = hd.getMaHoaDon();
            String paymentUrl = vnpayService.createOrder(orderInfo, amount, request);
            return "redirect:" + paymentUrl;
        }
        redirectAttributes.addFlashAttribute("error", "Không tìm thấy hóa đơn!");
        return "redirect:/sinhvien/hoa-don";
    }

    @GetMapping("/vnpay/return")
    public String vnpayReturn(jakarta.servlet.http.HttpServletRequest request, 
                              org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        boolean success = vnpayService.orderReturn(request);
        if (success) {
            String maHoaDon = request.getParameter("vnp_OrderInfo");
            hoaDonService.thanhToan(maHoaDon);
            redirectAttributes.addFlashAttribute("success", "Thanh toán hóa đơn qua VNPAY thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Thanh toán thất bại hoặc đã bị hủy.");
        }
        return "redirect:/sinhvien/hoa-don";
    }

    // --- KỶ LUẬT (VI PHẠM) ---

    @GetMapping("/vi-pham")
    public String listViPham(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model, Authentication auth) {
        String maSV = auth.getName();
        java.util.List<vn.iotstar.dormitory.entity.ViPham> fullList = viPhamService.findByMaSV(maSV);

        int totalElements = fullList.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        if (totalPages == 0) totalPages = 1;
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;

        int start = Math.min((page - 1) * size, totalElements);
        int end = Math.min(start + size, totalElements);
        java.util.List<vn.iotstar.dormitory.entity.ViPham> pagedList = fullList.subList(start, end);

        org.springframework.data.domain.Page<vn.iotstar.dormitory.entity.ViPham> viPhamPage =
                new org.springframework.data.domain.PageImpl<>(pagedList, org.springframework.data.domain.PageRequest.of(page - 1, size), totalElements);

        model.addAttribute("viPhamPage", viPhamPage);
        model.addAttribute("viPhamList", viPhamPage.getContent());
        return "sinhvien/vipham_list";
    }

    // --- HỒ SƠ CÁ NHÂN (PROFILE) ---

    @GetMapping("/profile")
    public String viewProfile(Model model, Authentication auth) {
        String maSV = auth.getName();
        java.util.Optional<vn.iotstar.dormitory.entity.SinhVien> svOpt = sinhVienService.findById(maSV);
        if (svOpt.isPresent()) {
            model.addAttribute("sinhVien", svOpt.get());
            java.util.Optional<vn.iotstar.dormitory.entity.PhanPhong> ppOpt = phanPhongService.findBySinhVienId(maSV);
            ppOpt.ifPresent(pp -> model.addAttribute("currentRoom", pp.getPhong()));
            return "sinhvien/profile";
        }
        return "redirect:/sinhvien/dashboard";
    }

    @PostMapping("/profile/cap-nhat")
    public String updateProfile(@RequestParam("sdt") String sdt,
                                @RequestParam("email") String email,
                                @RequestParam("queQuan") String queQuan,
                                @RequestParam(value = "diaChi", required = false) String diaChi,
                                @RequestParam(value = "sdtPhuHuynh", required = false) String sdtPhuHuynh,
                                @RequestParam(value = "hoTenPhuHuynh", required = false) String hoTenPhuHuynh,
                                @RequestParam(value = "truongDaiHoc", required = false) String truongDaiHoc,
                                @RequestParam(value = "khoa", required = false) String khoa,
                                Authentication auth,
                                org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        String maSV = auth.getName();
        java.util.Optional<vn.iotstar.dormitory.entity.SinhVien> svOpt = sinhVienService.findById(maSV);
        if (svOpt.isPresent()) {
            vn.iotstar.dormitory.entity.SinhVien sv = svOpt.get();
            sv.setSdt(sdt);
            sv.setEmail(email);
            sv.setQueQuan(queQuan);
            sv.setDiaChi(diaChi);
            sv.setSdtPhuHuynh(sdtPhuHuynh);
            sv.setHoTenPhuHuynh(hoTenPhuHuynh);

            // Cho phép sinh viên nhập / bổ sung trường & khoa
            if (truongDaiHoc != null && !truongDaiHoc.trim().isEmpty()) {
                sv.setTruongDaiHoc(truongDaiHoc.trim());
            }
            if (khoa != null && !khoa.trim().isEmpty()) {
                sv.setKhoa(khoa.trim());
            }

            sinhVienRepository.save(sv);
            redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin cá nhân thành công!");
        }
        return "redirect:/sinhvien/profile";
    }

    // --- CHAT VỚI BAN QUẢN LÝ ---
    @GetMapping("/chat")
    public String chatWithBql() {
        return "redirect:/sinhvien/dashboard?openChat=true";
    }

    // --- XUẤT FILE PDF CHO SINH VIÊN ---
    @GetMapping("/hop-dong/xuat-pdf/{id}")
    public void xuatPdfHopDong(@PathVariable("id") String id, Authentication auth, jakarta.servlet.http.HttpServletResponse response) throws Exception {
        java.util.Optional<vn.iotstar.dormitory.entity.HopDong> hdOpt = hopDongRepository.findById(id);
        if (hdOpt.isPresent()) {
            vn.iotstar.dormitory.entity.HopDong hd = hdOpt.get();
            if (hd.getPhanPhong() != null && hd.getPhanPhong().getSinhVien() != null
                    && auth.getName().equals(hd.getPhanPhong().getSinhVien().getMaSV())) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename=\"HopDong_" + id + ".pdf\"");
                pdfExportService.exportHopDongPdf(hd, response.getOutputStream());
                return;
            }
        }
        response.sendError(jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập hợp đồng này");
    }

    @GetMapping("/hoa-don/xuat-pdf/{id}")
    public void xuatPdfHoaDon(@PathVariable("id") String id, Authentication auth, jakarta.servlet.http.HttpServletResponse response) throws Exception {
        java.util.Optional<vn.iotstar.dormitory.entity.HoaDon> hdOpt = hoaDonRepository.findById(id);
        if (hdOpt.isPresent()) {
            vn.iotstar.dormitory.entity.HoaDon hd = hdOpt.get();
            boolean hopLe = false;
            if (hd.getSinhVien() != null && auth.getName().equals(hd.getSinhVien().getMaSV())) {
                hopLe = true;
            } else {
                java.util.Optional<vn.iotstar.dormitory.entity.PhanPhong> ppOpt = phanPhongService.findBySinhVienId(auth.getName());
                if (ppOpt.isPresent() && ppOpt.get().getPhong() != null && hd.getPhong() != null
                        && ppOpt.get().getPhong().getMaPhong().equals(hd.getPhong().getMaPhong())) {
                    hopLe = true;
                }
            }
            if (hopLe) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename=\"HoaDon_" + id + ".pdf\"");
                pdfExportService.exportHoaDonPdf(hd, response.getOutputStream());
                return;
            }
        }
        response.sendError(jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập hóa đơn này");
    }
}
