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

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        String maSV = auth.getName();
        java.util.Optional<vn.iotstar.dormitory.entity.PhanPhong> ppOpt = phanPhongService.findBySinhVienId(maSV);
        if (ppOpt.isPresent() && "Đang ở".equals(ppOpt.get().getTrangThai())) {
            model.addAttribute("currentRoom", ppOpt.get().getPhong());
            
            // Lấy số hóa đơn chưa thanh toán
            long unpaidInvoices = hoaDonService.countUnpaidBySinhVien(maSV);
            model.addAttribute("unpaidInvoices", unpaidInvoices);
        } else {
            model.addAttribute("unpaidInvoices", 0);
        }
        
        // Lấy số lượng vi phạm
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
    @Autowired
    private vn.iotstar.dormitory.service.YeuCauSuaChuaService yeuCauSuaChuaService;

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
    @Autowired
    private vn.iotstar.dormitory.service.HopDongService hopDongService;

    @GetMapping("/phong")
    public String thongTinPhong(Model model, Authentication auth) {
        String maSV = auth.getName();
        java.util.List<vn.iotstar.dormitory.entity.PhanPhong> myRooms = phanPhongService.findBySinhVienId(maSV).stream().toList();
        model.addAttribute("myRooms", myRooms);
        model.addAttribute("hopDongs", hopDongService.findBySinhVienId(maSV));
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
    @Autowired
    private vn.iotstar.dormitory.service.HoaDonService hoaDonService;
    
    @Autowired
    private vn.iotstar.dormitory.service.CloudinaryService cloudinaryService;

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

    @Autowired
    private vn.iotstar.dormitory.service.VNPAYService vnpayService;

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
    @Autowired
    private vn.iotstar.dormitory.service.ViPhamService viPhamService;

    @GetMapping("/vi-pham")
    public String listViPham(Model model, Authentication auth) {
        String maSV = auth.getName();
        model.addAttribute("viPhamList", viPhamService.findByMaSV(maSV));
        return "sinhvien/vipham_list";
    }
}
