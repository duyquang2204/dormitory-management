package vn.iotstar.dormitory.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.dormitory.dto.NguoiDungDTO;
import vn.iotstar.dormitory.entity.NguoiDung;
import vn.iotstar.dormitory.service.NguoiDungService;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private vn.iotstar.dormitory.service.HoaDonService hoaDonService;
    @Autowired
    private vn.iotstar.dormitory.service.YeuCauSuaChuaService yeuCauSuaChuaService;
    @Autowired
    private vn.iotstar.dormitory.service.PhanPhongService phanPhongService;

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(value = "year", required = false) Integer year, Model model) {
        model.addAttribute("totalUsers", nguoiDungService.findAll().size());
        
        long countNoiTru = phanPhongService.findAll().stream()
                .filter(p -> "Đang ở".equals(p.getTrangThai()))
                .count();
        model.addAttribute("totalStudents", countNoiTru);
        
        model.addAttribute("totalInvoices", hoaDonService.findAll().size());
        model.addAttribute("totalRepairs", yeuCauSuaChuaService.findAll().size());
        
        int currentYear = (year != null) ? year : java.time.LocalDate.now().getYear();
        
        java.util.List<Integer> distinctYears = hoaDonService.getDistinctYears();
        if (!distinctYears.contains(currentYear)) {
            distinctYears.add(currentYear);
        }
        java.util.Collections.sort(distinctYears, java.util.Collections.reverseOrder());
        
        java.util.List<Object[]> revenueRaw = hoaDonService.getRevenueByMonthAndYear(currentYear);
        java.util.List<Double> revenueList = new java.util.ArrayList<>(java.util.Collections.nCopies(12, 0.0));
        
        for (Object[] obj : revenueRaw) {
            int month = ((Number) obj[0]).intValue();
            double amount = ((Number) obj[1]).doubleValue();
            revenueList.set(month - 1, amount);
        }
        
        model.addAttribute("revenueData", revenueList);
        model.addAttribute("currentYear", currentYear);
        model.addAttribute("availableYears", distinctYears);
        
        return "admin/dashboard";
    }

    @GetMapping("/tai-khoan")
    public String listTaiKhoan(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {
        java.util.List<NguoiDung> fullList = nguoiDungService.findAll();
        if (keyword != null && !keyword.trim().isEmpty()) {
            String kw = keyword.trim().toLowerCase();
            fullList = fullList.stream().filter(u -> {
                String ten = u.getHoTen() != null ? u.getHoTen().toLowerCase() : "";
                String username = u.getTenDangNhap() != null ? u.getTenDangNhap().toLowerCase() : "";
                String sdt = u.getSdt() != null ? u.getSdt().toLowerCase() : "";
                String chucVu = u.getChucVu() != null ? u.getChucVu().toLowerCase() : "";
                return ten.contains(kw) || username.contains(kw) || sdt.contains(kw) || chucVu.contains(kw);
            }).toList();
        }

        int totalElements = fullList.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        if (totalPages == 0) totalPages = 1;
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;

        int start = Math.min((page - 1) * size, totalElements);
        int end = Math.min(start + size, totalElements);
        java.util.List<NguoiDung> pagedList = fullList.subList(start, end);

        org.springframework.data.domain.Page<NguoiDung> userPage =
                new org.springframework.data.domain.PageImpl<>(pagedList, org.springframework.data.domain.PageRequest.of(page - 1, size), totalElements);

        model.addAttribute("userPage", userPage);
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("keyword", keyword);
        model.addAttribute("queryParams", keyword != null && !keyword.isEmpty() ? "&keyword=" + keyword : "");
        return "admin/taikhoan_list";
    }

    @GetMapping("/tai-khoan/them")
    public String themTaiKhoan(Model model) {
        model.addAttribute("nguoiDungDTO", new NguoiDungDTO());
        return "admin/taikhoan_form";
    }

    @PostMapping("/tai-khoan/luu")
    public String luuTaiKhoan(@Valid @ModelAttribute("nguoiDungDTO") NguoiDungDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/taikhoan_form";
        }
        nguoiDungService.save(dto);
        return "redirect:/admin/tai-khoan?success";
    }

    @GetMapping("/tai-khoan/sua/{id}")
    public String suaTaiKhoan(@PathVariable("id") String id, Model model) {
        Optional<NguoiDung> nd = nguoiDungService.findById(id);
        if (nd.isPresent()) {
            NguoiDungDTO dto = new NguoiDungDTO();
            dto.setMaNguoiDung(nd.get().getMaNguoiDung());
            dto.setTenDangNhap(nd.get().getTenDangNhap());
            dto.setHoTen(nd.get().getHoTen());
            dto.setSdt(nd.get().getSdt());
            dto.setChucVu(nd.get().getChucVu());
            dto.setTrangThai(nd.get().getTrangThai());
            model.addAttribute("nguoiDungDTO", dto);
            return "admin/taikhoan_form";
        }
        return "redirect:/admin/tai-khoan";
    }
}
