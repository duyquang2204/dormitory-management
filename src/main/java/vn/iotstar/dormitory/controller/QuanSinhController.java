package vn.iotstar.dormitory.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.dormitory.dto.PhongDTO;
import vn.iotstar.dormitory.dto.SinhVienDTO;
import vn.iotstar.dormitory.entity.Phong;
import vn.iotstar.dormitory.entity.SinhVien;
import vn.iotstar.dormitory.entity.Khu;
import vn.iotstar.dormitory.entity.LoaiPhong;
import vn.iotstar.dormitory.entity.DangKyKTX;
import vn.iotstar.dormitory.service.PhongService;
import vn.iotstar.dormitory.service.SinhVienService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/quansinh")
public class QuanSinhController {

    @Autowired
    private SinhVienService sinhVienService;
    
    @Autowired
    private PhongService phongService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long countNoiTru = phanPhongService.findAll().stream()
                .filter(p -> "Đang ở".equals(p.getTrangThai()))
                .count();
        model.addAttribute("totalSinhVien", countNoiTru);
        model.addAttribute("totalPhong", phongService.findAll().size());
        
        long countDangKy = dangKyKTXService.findAll().stream().filter(d -> "Chờ duyệt".equals(d.getTrangThai())).count();
        model.addAttribute("totalDangKyChoDuyet", countDangKy);
        
        long countSuaChua = yeuCauSuaChuaService.findAll().stream().filter(y -> "Chờ xử lý".equals(y.getTrangThai())).count();
        model.addAttribute("totalSuaChuaChoXuLy", countSuaChua);

        return "quansinh/dashboard";
    }

    // --- QUẢN LÝ SINH VIÊN ---
    
    @GetMapping("/sinhvien")
    public String listSinhVien(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page - 1, size);
        org.springframework.data.domain.Page<SinhVien> pageData = sinhVienService.searchByKeyword(keyword, pageable);
        
        java.util.List<vn.iotstar.dormitory.entity.PhanPhong> allPhanPhong = phanPhongService.findAll();
        java.util.Map<String, String> trangThaiMap = new java.util.HashMap<>();
        
        for (SinhVien sv : pageData.getContent()) {
            java.util.Optional<vn.iotstar.dormitory.entity.PhanPhong> ppOpt = allPhanPhong.stream()
                .filter(p -> p.getSinhVien() != null && p.getSinhVien().getMaSV().equals(sv.getMaSV()) && "Đang ở".equals(p.getTrangThai()))
                .findFirst();
            if (ppOpt.isPresent()) {
                trangThaiMap.put(sv.getMaSV(), "Nội trú (Phòng " + ppOpt.get().getPhong().getSoPhong() + ")");
            } else {
                trangThaiMap.put(sv.getMaSV(), "Chờ xếp phòng");
            }
        }
        
        model.addAttribute("sinhVienPage", pageData);
        model.addAttribute("trangThaiMap", trangThaiMap);
        model.addAttribute("keyword", keyword);
        return "quansinh/sinhvien_list";
    }
    
    @GetMapping("/sinhvien/them")
    public String themSinhVien(Model model) {
        long count = sinhVienService.findAll().size();
        String generatedId = String.format("SV%04d", count + 1);
        while (sinhVienService.findById(generatedId).isPresent()) {
            count++;
            generatedId = String.format("SV%04d", count + 1);
        }
        
        SinhVienDTO dto = new SinhVienDTO();
        dto.setMaSV(generatedId);
        
        model.addAttribute("sinhVienDTO", dto);
        model.addAttribute("isEdit", false);
        return "quansinh/sinhvien_form";
    }
    
    @PostMapping("/sinhvien/luu")
    public String luuSinhVien(@Valid @ModelAttribute("sinhVienDTO") SinhVienDTO dto, 
                              BindingResult result, 
                              @RequestParam(value = "isEdit", defaultValue = "false") boolean isEdit,
                              Model model) {
        if (!isEdit && sinhVienService.findById(dto.getMaSV()).isPresent()) {
            result.rejectValue("maSV", "error.sinhVienDTO", "Mã sinh viên đã tồn tại trong hệ thống!");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("isEdit", isEdit);
            return "quansinh/sinhvien_form";
        }
        sinhVienService.save(dto);
        return "redirect:/quansinh/sinhvien?success";
    }
    
    @GetMapping("/sinhvien/sua/{id}")
    public String suaSinhVien(@PathVariable("id") String id, Model model) {
        Optional<SinhVien> svOpt = sinhVienService.findById(id);
        if (svOpt.isPresent()) {
            SinhVien sv = svOpt.get();
            SinhVienDTO dto = new SinhVienDTO();
            dto.setMaSV(sv.getMaSV());
            dto.setHoTen(sv.getHoTen());
            dto.setNgaySinh(sv.getNgaySinh());
            dto.setGioiTinh(sv.getGioiTinh());
            dto.setQueQuan(sv.getQueQuan());
            dto.setCccd(sv.getCccd());
            dto.setSdt(sv.getSdt());
            dto.setKhoa(sv.getKhoa());
            dto.setTruongDaiHoc(sv.getTruongDaiHoc());
            dto.setNamHoc(sv.getNamHoc());
            dto.setDienUuTien(sv.getDienUuTien());
            model.addAttribute("sinhVienDTO", dto);
            model.addAttribute("isEdit", true);
            return "quansinh/sinhvien_form";
        }
        return "redirect:/quansinh/sinhvien";
    }
    
    @PostMapping("/sinhvien/xoa/{id}")
    public String xoaSinhVien(@PathVariable("id") String id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            sinhVienService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Đã xóa sinh viên thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa sinh viên này (có thể do dữ liệu ràng buộc)!");
        }
        return "redirect:/quansinh/sinhvien";
    }

    // --- QUẢN LÝ PHÒNG ---
    
    @GetMapping("/phong")
    public String listPhong(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "maKhu", required = false) String maKhu,
            @RequestParam(name = "tang", required = false) Integer tang,
            @RequestParam(name = "gioiTinh", required = false) String gioiTinh,
            @RequestParam(name = "trangThai", required = false) String trangThai,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "12") int size,
            Model model) {
            
        org.springframework.data.domain.Pageable pageable = 
                org.springframework.data.domain.PageRequest.of(page - 1, size);
        org.springframework.data.domain.Page<Phong> phongPage = 
                phongService.searchAndFilterPhong(keyword, maKhu, tang, gioiTinh, trangThai, pageable);
        
        StringBuilder qp = new StringBuilder();
        if (keyword != null && !keyword.trim().isEmpty()) {
            qp.append("&keyword=").append(java.net.URLEncoder.encode(keyword.trim(), java.nio.charset.StandardCharsets.UTF_8));
        }
        if (maKhu != null && !maKhu.trim().isEmpty()) {
            qp.append("&maKhu=").append(java.net.URLEncoder.encode(maKhu.trim(), java.nio.charset.StandardCharsets.UTF_8));
        }
        if (tang != null && tang > 0) {
            qp.append("&tang=").append(tang);
        }
        if (gioiTinh != null && !gioiTinh.trim().isEmpty()) {
            qp.append("&gioiTinh=").append(java.net.URLEncoder.encode(gioiTinh.trim(), java.nio.charset.StandardCharsets.UTF_8));
        }
        if (trangThai != null && !trangThai.trim().isEmpty()) {
            qp.append("&trangThai=").append(java.net.URLEncoder.encode(trangThai.trim(), java.nio.charset.StandardCharsets.UTF_8));
        }

        model.addAttribute("phongPage", phongPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("maKhu", maKhu != null ? maKhu : "");
        model.addAttribute("tang", tang != null ? tang : 0);
        model.addAttribute("gioiTinh", gioiTinh != null ? gioiTinh : "");
        model.addAttribute("trangThai", trangThai != null ? trangThai : "");
        model.addAttribute("queryParams", qp.toString());
        model.addAttribute("khus", phongService.findAllKhu());
        return "quansinh/phong_list";
    }
    
    @GetMapping("/phong/them")
    public String themPhong(Model model) {
        model.addAttribute("phongDTO", new PhongDTO());
        model.addAttribute("khus", phongService.findAllKhu());
        model.addAttribute("loaiPhongs", phongService.findAllLoaiPhong());
        return "quansinh/phong_form";
    }
    
    @PostMapping("/phong/luu")
    public String luuPhong(@Valid @ModelAttribute("phongDTO") PhongDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("khus", phongService.findAllKhu());
            model.addAttribute("loaiPhongs", phongService.findAllLoaiPhong());
            return "quansinh/phong_form";
        }
        phongService.save(dto);
        return "redirect:/quansinh/phong?success";
    }
    
    @GetMapping("/phong/sua/{id}")
    public String suaPhong(@PathVariable("id") String id, Model model) {
        Optional<Phong> pOpt = phongService.findById(id);
        if (pOpt.isPresent()) {
            Phong p = pOpt.get();
            PhongDTO dto = new PhongDTO();
            dto.setMaPhong(p.getMaPhong());
            dto.setSoPhong(p.getSoPhong());
            if (p.getKhu() != null) dto.setMaKhu(p.getKhu().getMaKhu());
            if (p.getLoaiPhong() != null) dto.setMaLoaiPhong(p.getLoaiPhong().getMaLoaiPhong());
            dto.setTrangThai(p.getTrangThai());
            
            model.addAttribute("phongDTO", dto);
            model.addAttribute("khus", phongService.findAllKhu());
            model.addAttribute("loaiPhongs", phongService.findAllLoaiPhong());
            return "quansinh/phong_form";
        }
        return "redirect:/quansinh/phong";
    }

    // --- QUẢN LÝ KHU ---
    @GetMapping("/khu/them")
    public String themKhu(Model model) {
        model.addAttribute("khu", new Khu());
        return "quansinh/khu_form";
    }

    @PostMapping("/khu/luu")
    public String luuKhu(@ModelAttribute("khu") Khu khu) {
        phongService.saveKhu(khu);
        return "redirect:/quansinh/phong/them?successKhu";
    }

    // --- QUẢN LÝ LOẠI PHÒNG ---
    @GetMapping("/loaiphong/them")
    public String themLoaiPhong(Model model) {
        model.addAttribute("loaiPhong", new LoaiPhong());
        return "quansinh/loaiphong_form";
    }

    @PostMapping("/loaiphong/luu")
    public String luuLoaiPhong(@ModelAttribute("loaiPhong") LoaiPhong loaiPhong) {
        phongService.saveLoaiPhong(loaiPhong);
        return "redirect:/quansinh/phong/them?successLoaiPhong";
    }

    // --- DUYỆT ĐĂNG KÝ VÀ PHÂN PHÒNG ---
    @Autowired
    private vn.iotstar.dormitory.service.DangKyKTXService dangKyKTXService;
    
    @Autowired
    private vn.iotstar.dormitory.service.PhanPhongService phanPhongService;

    @GetMapping("/dang-ky")
    public String listDangKy(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "trangThai", required = false) String trangThai,
            @RequestParam(value = "maLoaiPhong", required = false) String maLoaiPhong,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {
        List<DangKyKTX> fullList = dangKyKTXService.search(keyword, trangThai, maLoaiPhong);

        int totalElements = fullList.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        if (totalPages == 0) totalPages = 1;
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;

        int start = Math.min((page - 1) * size, totalElements);
        int end = Math.min(start + size, totalElements);
        List<DangKyKTX> pagedList = fullList.subList(start, end);

        org.springframework.data.domain.Page<DangKyKTX> dangKyPage =
                new org.springframework.data.domain.PageImpl<>(pagedList, org.springframework.data.domain.PageRequest.of(page - 1, size), totalElements);

        StringBuilder qp = new StringBuilder();
        if (keyword != null && !keyword.isEmpty()) qp.append("&keyword=").append(keyword);
        if (trangThai != null && !trangThai.isEmpty()) qp.append("&trangThai=").append(trangThai);
        if (maLoaiPhong != null && !maLoaiPhong.isEmpty()) qp.append("&maLoaiPhong=").append(maLoaiPhong);

        model.addAttribute("dangKyPage", dangKyPage);
        model.addAttribute("dangKyList", dangKyPage.getContent());
        model.addAttribute("queryParams", qp.toString());
        model.addAttribute("loaiPhongs", phongService.findAllLoaiPhong());
        model.addAttribute("keyword", keyword);
        model.addAttribute("trangThai", trangThai);
        model.addAttribute("maLoaiPhong", maLoaiPhong);
        return "quansinh/dangky_list";
    }

    @PostMapping("/dang-ky/tu-choi/{id}")
    public String tuChoiDangKy(@PathVariable("id") String id,
                               @RequestParam(value = "lyDoTuChoi", required = false, defaultValue = "Phòng đăng ký đã hết chỗ hoặc hồ sơ chưa đáp ứng tiêu chí đợt này.") String lyDoTuChoi,
                               org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            dangKyKTXService.rejectRegistration(id, lyDoTuChoi);
            redirectAttributes.addFlashAttribute("success", "Đã từ chối đơn đăng ký và tự động gửi email thông báo lý do tới sinh viên.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/quansinh/dang-ky";
    }

    @GetMapping("/dang-ky/duyet/{id}")
    public String formDuyetDangKy(@PathVariable("id") String id, Model model) {
        java.util.Optional<vn.iotstar.dormitory.entity.DangKyKTX> dkOpt = dangKyKTXService.findById(id);
        if (dkOpt.isPresent()) {
            vn.iotstar.dormitory.entity.DangKyKTX dk = dkOpt.get();
            model.addAttribute("dangKy", dk);

            String gioiTinh = dk.getGioiTinh();
            if (gioiTinh == null && dk.getSinhVien() != null) {
                gioiTinh = dk.getSinhVien().getGioiTinh();
            }
            final String finalGioiTinh = gioiTinh;

            List<Phong> allPhongs = phongService.findAll();
            
            // 1. Tìm phòng đúng loại phòng yêu cầu và đúng giới tính
            List<Phong> matchingPhongs = allPhongs.stream()
                    .filter(p -> {
                        boolean matchLoai = (dk.getLoaiPhong() == null) || (p.getLoaiPhong() != null && p.getLoaiPhong().getMaLoaiPhong().equals(dk.getLoaiPhong().getMaLoaiPhong()));
                        boolean conCho = p.getSoChoTrong() > 0 && !"Đã đầy".equalsIgnoreCase(p.getTrangThai());
                        boolean matchGioiTinh = (finalGioiTinh == null) || finalGioiTinh.equalsIgnoreCase(p.getGioiTinh());
                        return matchLoai && conCho && matchGioiTinh;
                    })
                    .toList();

            // 2. Nếu phòng loại đó hết chỗ, hiển thị tất cả các phòng khác cùng giới tính còn chỗ
            List<Phong> displayPhongs = matchingPhongs;
            if (displayPhongs.isEmpty()) {
                displayPhongs = allPhongs.stream()
                        .filter(p -> {
                            boolean conCho = p.getSoChoTrong() > 0 && !"Đã đầy".equalsIgnoreCase(p.getTrangThai());
                            boolean matchGioiTinh = (finalGioiTinh == null) || finalGioiTinh.equalsIgnoreCase(p.getGioiTinh());
                            return conCho && matchGioiTinh;
                        })
                        .toList();
                if (!displayPhongs.isEmpty() && dk.getLoaiPhong() != null) {
                    model.addAttribute("warningMessage", "Loại phòng \"" + dk.getLoaiPhong().getTenLoaiPhong() + "\" hiện đã hết chỗ. Hệ thống gợi ý các phòng khác cùng giới tính (" + (finalGioiTinh != null ? finalGioiTinh : "") + ") còn trống để Quản sinh linh hoạt xếp phòng.");
                }
            }

            model.addAttribute("phongs", displayPhongs);
            model.addAttribute("homNay", java.time.LocalDate.now());
            model.addAttribute("ngayKetThucMacDinh", java.time.LocalDate.now().plusMonths(6));
            return "quansinh/phanphong_form";
        }
        return "redirect:/quansinh/dang-ky";
    }

    @PostMapping("/dang-ky/duyet/{id}")
    public String luuDuyetDangKy(@PathVariable("id") String id, 
                                 @RequestParam("maPhong") String maPhong,
                                 @RequestParam("ngayBatDau") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate ngayBatDau,
                                 @RequestParam("ngayKetThuc") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate ngayKetThuc,
                                 org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            dangKyKTXService.approveRegistration(id, maPhong, ngayBatDau, ngayKetThuc);
            redirectAttributes.addFlashAttribute("success", "Đã duyệt đơn, xếp phòng và gửi email chúc mừng trúng tuyển kèm thông tin tài khoản cho sinh viên thành công!");
            return "redirect:/quansinh/dang-ky";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/quansinh/dang-ky/duyet/" + id;
        }
    }

    @GetMapping("/phong/phan-nhanh/{maSV}")
    public String formPhanNhanh(@PathVariable("maSV") String maSV, Model model, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        Optional<SinhVien> svOpt = sinhVienService.findById(maSV);
        if (!svOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sinh viên!");
            return "redirect:/quansinh/sinhvien";
        }
        
        long violations = viPhamService.countByMaSV(maSV);
        if (violations >= 3) {
            redirectAttributes.addFlashAttribute("error", "Sinh viên đã vi phạm kỷ luật từ 3 lần trở lên, không được phép xếp phòng!");
            return "redirect:/quansinh/sinhvien";
        }

        SinhVien sv = svOpt.get();
        String gioiTinh = sv.getGioiTinh();
        model.addAttribute("sinhVien", sv);
        model.addAttribute("phongs", phongService.findAll().stream()
                .filter(p -> {
                    boolean conCho = !"Đã đầy".equals(p.getTrangThai());
                    boolean matchGioiTinh = (gioiTinh == null) || gioiTinh.equalsIgnoreCase(p.getGioiTinh());
                    return conCho && matchGioiTinh;
                })
                .toList());
        return "quansinh/phanphong_nhanh_form";
    }

    @PostMapping("/phong/phan-nhanh/{maSV}")
    public String luuPhanNhanh(@PathVariable("maSV") String maSV, 
                                 @RequestParam("maPhong") String maPhong,
                                 @RequestParam("ngayBatDau") java.time.LocalDate ngayBatDau,
                                 @RequestParam("ngayKetThuc") java.time.LocalDate ngayKetThuc,
                                 org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            phanPhongService.assignRoomQuickly(maSV, maPhong, ngayBatDau, ngayKetThuc);
            redirectAttributes.addFlashAttribute("success", "Đã phân phòng trực tiếp thành công!");
            return "redirect:/quansinh/sinhvien";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/quansinh/phong/phan-nhanh/" + maSV;
        }
    }

    // --- YÊU CẦU SỬA CHỮA ---
    @Autowired
    private vn.iotstar.dormitory.service.YeuCauSuaChuaService yeuCauSuaChuaService;

    @Autowired
    private vn.iotstar.dormitory.service.KetQuaSuaChuaService ketQuaSuaChuaService;

    @Autowired
    private vn.iotstar.dormitory.repository.NguoiDungRepository nguoiDungRepository;

    @GetMapping("/sua-chua")
    public String listSuaChua(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {
        List<vn.iotstar.dormitory.entity.YeuCauSuaChua> fullList = yeuCauSuaChuaService.findAll();
        int totalElements = fullList.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        if (totalPages == 0) totalPages = 1;
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;

        int start = Math.min((page - 1) * size, totalElements);
        int end = Math.min(start + size, totalElements);
        List<vn.iotstar.dormitory.entity.YeuCauSuaChua> pagedList = fullList.subList(start, end);

        org.springframework.data.domain.Page<vn.iotstar.dormitory.entity.YeuCauSuaChua> yeuCauPage =
                new org.springframework.data.domain.PageImpl<>(pagedList, org.springframework.data.domain.PageRequest.of(page - 1, size), totalElements);

        model.addAttribute("yeuCauPage", yeuCauPage);
        model.addAttribute("yeuCauList", yeuCauPage.getContent());
        model.addAttribute("nhanViens", nguoiDungRepository.findAll().stream()
                .filter(nd -> "Nhân viên sửa chữa".equals(nd.getChucVu()) && "Hoạt động".equals(nd.getTrangThai()))
                .toList());
        return "quansinh/suachua_list";
    }

    @PostMapping("/sua-chua/phan-cong")
    public String phanCongSuaChua(@RequestParam("maYeuCau") String maYeuCau, 
                                  @RequestParam("maNhanVienList") java.util.List<String> maNhanVienList) {
        ketQuaSuaChuaService.assignTask(maYeuCau, maNhanVienList);
        return "redirect:/quansinh/sua-chua?success";
    }

    @PostMapping("/sua-chua/huy")
    public String huyYeuCau(@RequestParam("maYeuCau") String maYeuCau) {
        ketQuaSuaChuaService.cancelTask(maYeuCau);
        return "redirect:/quansinh/sua-chua?success";
    }

    @PostMapping("/sua-chua/xoa/{id}")
    public String xoaYeuCauSuaChua(@PathVariable("id") String id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            yeuCauSuaChuaService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Đã xóa yêu cầu sửa chữa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa yêu cầu sửa chữa: " + e.getMessage());
        }
        return "redirect:/quansinh/sua-chua";
    }

    // --- QUẢN LÝ HỢP ĐỒNG ---
    @Autowired
    private vn.iotstar.dormitory.service.HopDongService hopDongService;

    @GetMapping("/hop-dong")
    public String listHopDong(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {
        List<vn.iotstar.dormitory.entity.HopDong> fullList = hopDongService.findAll();
        if (keyword != null && !keyword.trim().isEmpty()) {
            String kw = keyword.trim().toLowerCase();
            fullList = fullList.stream().filter(hd -> {
                String maHD = hd.getMaHopDong() != null ? hd.getMaHopDong().toLowerCase() : "";
                String tenSV = (hd.getPhanPhong() != null && hd.getPhanPhong().getSinhVien() != null && hd.getPhanPhong().getSinhVien().getHoTen() != null)
                        ? hd.getPhanPhong().getSinhVien().getHoTen().toLowerCase() : "";
                String maSV = (hd.getPhanPhong() != null && hd.getPhanPhong().getSinhVien() != null && hd.getPhanPhong().getSinhVien().getMaSV() != null)
                        ? hd.getPhanPhong().getSinhVien().getMaSV().toLowerCase() : "";
                String phong = (hd.getPhanPhong() != null && hd.getPhanPhong().getPhong() != null && hd.getPhanPhong().getPhong().getSoPhong() != null)
                        ? hd.getPhanPhong().getPhong().getSoPhong().toLowerCase() : "";
                return maHD.contains(kw) || tenSV.contains(kw) || maSV.contains(kw) || phong.contains(kw);
            }).toList();
        }

        int totalElements = fullList.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        if (totalPages == 0) totalPages = 1;
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;

        int start = Math.min((page - 1) * size, totalElements);
        int end = Math.min(start + size, totalElements);
        List<vn.iotstar.dormitory.entity.HopDong> pagedList = fullList.subList(start, end);

        org.springframework.data.domain.Page<vn.iotstar.dormitory.entity.HopDong> hopDongPage =
                new org.springframework.data.domain.PageImpl<>(pagedList, org.springframework.data.domain.PageRequest.of(page - 1, size), totalElements);

        model.addAttribute("hopDongPage", hopDongPage);
        model.addAttribute("hopDongs", hopDongPage.getContent());
        model.addAttribute("keyword", keyword);
        model.addAttribute("queryParams", keyword != null && !keyword.isEmpty() ? "&keyword=" + keyword : "");
        return "quansinh/hopdong_list";
    }

    @GetMapping("/hop-dong/them")
    public String formHopDong(Model model) {
        model.addAttribute("hopDongDTO", new vn.iotstar.dormitory.dto.HopDongDTO());
        
        java.util.Set<String> assignedPhanPhongIds = hopDongService.findAll().stream()
                .map(hd -> hd.getPhanPhong().getMaPhanPhong())
                .collect(java.util.stream.Collectors.toSet());

        model.addAttribute("phanPhongs", phanPhongService.findAll().stream()
                .filter(pp -> "Đang ở".equals(pp.getTrangThai()) && !assignedPhanPhongIds.contains(pp.getMaPhanPhong()))
                .toList());
        return "quansinh/hopdong_form";
    }

    @PostMapping("/hop-dong/luu")
    public String luuHopDong(@Valid @ModelAttribute("hopDongDTO") vn.iotstar.dormitory.dto.HopDongDTO dto, 
                             BindingResult result, Model model, 
                             org.springframework.security.core.Authentication auth) {
        if (result.hasErrors()) {
            java.util.Set<String> assignedPhanPhongIds = hopDongService.findAll().stream()
                    .map(hd -> hd.getPhanPhong().getMaPhanPhong())
                    .collect(java.util.stream.Collectors.toSet());

            model.addAttribute("phanPhongs", phanPhongService.findAll().stream()
                .filter(pp -> "Đang ở".equals(pp.getTrangThai()) && !assignedPhanPhongIds.contains(pp.getMaPhanPhong()))
                .toList());
            return "quansinh/hopdong_form";
        }
        hopDongService.createHopDong(dto, auth.getName());
        return "redirect:/quansinh/hop-dong?success";
    }

    @PostMapping("/hop-dong/duyet-gia-han")
    public String duyetGiaHan(@RequestParam("maHopDong") String maHopDong, @RequestParam("soThang") int soThang) {
        hopDongService.duyetGiaHan(maHopDong, soThang);
        return "redirect:/quansinh/hop-dong?success";
    }

    @PostMapping("/hop-dong/thanh-ly")
    public String thanhLyHopDong(@RequestParam("maHopDong") String maHopDong) {
        hopDongService.thanhLyHopDong(maHopDong);
        return "redirect:/quansinh/hop-dong?success";
    }

    // --- QUẢN LÝ HÓA ĐƠN ---
    @Autowired
    private vn.iotstar.dormitory.service.HoaDonService hoaDonService;

    @GetMapping("/hoa-don")
    public String listHoaDon(
            @RequestParam(name = "maKhu", required = false) String maKhu,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {
            
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page - 1, size);
        org.springframework.data.domain.Page<vn.iotstar.dormitory.entity.HoaDon> hoaDonPage;
        
        if (maKhu != null && !maKhu.isEmpty()) {
            hoaDonPage = hoaDonService.findByKhu(maKhu, pageable);
        } else {
            hoaDonPage = hoaDonService.findAll(pageable);
        }
        
        model.addAttribute("hoaDonPage", hoaDonPage);
        model.addAttribute("maKhu", maKhu);
        model.addAttribute("khus", phongService.findAllKhu());
        return "quansinh/hoadon_list";
    }

    @GetMapping("/hoa-don/them")
    public String themHoaDon(Model model) {
        model.addAttribute("hoaDonDTO", new vn.iotstar.dormitory.dto.HoaDonDTO());
        model.addAttribute("phongs", phongService.findAll());
        return "quansinh/hoadon_form";
    }

    @PostMapping("/hoa-don/luu")
    public String luuHoaDon(@Valid @ModelAttribute("hoaDonDTO") vn.iotstar.dormitory.dto.HoaDonDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("phongs", phongService.findAll());
            return "quansinh/hoadon_form";
        }
        try {
            hoaDonService.save(dto);
            return "redirect:/quansinh/hoa-don?success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("phongs", phongService.findAll());
            return "quansinh/hoadon_form";
        }
    }
    
    @GetMapping("/hoa-don/thanh-toan/{id}")
    public String thanhToanHoaDon(@PathVariable("id") String id) {
        hoaDonService.thanhToan(id);
        return "redirect:/quansinh/hoa-don?paid";
    }

    @GetMapping("/hoa-don/ghi-so-hang-loat")
    public String ghiSoHangLoatForm(@RequestParam(name = "maKhu", required = false) String maKhu,
                                    @RequestParam(name = "thang", required = false) Integer thang,
                                    @RequestParam(name = "nam", required = false) Integer nam,
                                    Model model) {
        vn.iotstar.dormitory.dto.HoaDonMassInputDTO dto = new vn.iotstar.dormitory.dto.HoaDonMassInputDTO();
        java.util.List<vn.iotstar.dormitory.dto.HoaDonRoomInputDTO> roomDtos = new java.util.ArrayList<>();
        
        if (maKhu != null && thang != null && nam != null) {
            dto.setMaKhu(maKhu);
            dto.setThang(thang);
            dto.setNam(nam);
            
            java.util.List<Phong> phongs = phongService.findAll().stream()
                .filter(p -> p.getKhu() != null && p.getKhu().getMaKhu().equals(maKhu))
                .toList();
                
            for (Phong p : phongs) {
                vn.iotstar.dormitory.dto.HoaDonRoomInputDTO roomDto = new vn.iotstar.dormitory.dto.HoaDonRoomInputDTO();
                roomDto.setMaPhong(p.getMaPhong());
                roomDto.setTenPhong(p.getSoPhong());
                
                // check if existing for THIS month
                java.util.List<vn.iotstar.dormitory.entity.HoaDon> existingHdList = hoaDonService.findByPhongAndThangAndNam(p.getMaPhong(), thang, nam);
                if (!existingHdList.isEmpty()) {
                    roomDto.setDaTonTai(true);
                    roomDto.setChiSoDienCu(existingHdList.get(0).getChiSoDienCu());
                    roomDto.setChiSoNuocCu(existingHdList.get(0).getChiSoNuocCu());
                    roomDto.setChiSoDienMoi(existingHdList.get(0).getChiSoDienMoi());
                    roomDto.setChiSoNuocMoi(existingHdList.get(0).getChiSoNuocMoi());
                } else {
                    roomDto.setDaTonTai(false);
                    roomDto.setChiSoDienCu(hoaDonService.getChiSoDienMoiNhat(p.getMaPhong(), thang, nam));
                    roomDto.setChiSoNuocCu(hoaDonService.getChiSoNuocMoiNhat(p.getMaPhong(), thang, nam));
                }
                roomDtos.add(roomDto);
            }
        }
        dto.setRooms(roomDtos);
        
        model.addAttribute("massDto", dto);
        model.addAttribute("khus", phongService.findAllKhu());
        
        return "quansinh/hoadon_mass_form";
    }

    @PostMapping("/hoa-don/ghi-so-hang-loat")
    public String luuGhiSoHangLoat(@ModelAttribute("massDto") vn.iotstar.dormitory.dto.HoaDonMassInputDTO dto,
                                   org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            hoaDonService.saveMass(dto);
            redirectAttributes.addFlashAttribute("success", "Đã lưu hóa đơn hàng loạt thành công!");
            return "redirect:/quansinh/hoa-don";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
            return "redirect:/quansinh/hoa-don/ghi-so-hang-loat?maKhu=" + dto.getMaKhu() + "&thang=" + dto.getThang() + "&nam=" + dto.getNam();
        }
    }

    // --- KỶ LUẬT (VI PHẠM) ---
    @Autowired
    private vn.iotstar.dormitory.service.ViPhamService viPhamService;

    @GetMapping("/vi-pham")
    public String listViPham(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {
            
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page - 1, size);
        org.springframework.data.domain.Page<vn.iotstar.dormitory.entity.ViPham> viPhamPage;
        
        if (keyword != null && !keyword.isEmpty()) {
            viPhamPage = viPhamService.searchByMaSV(keyword, pageable);
            model.addAttribute("keyword", keyword);
        } else {
            viPhamPage = viPhamService.findAll(pageable);
        }
        
        model.addAttribute("viPhamPage", viPhamPage);
        return "quansinh/vipham_list";
    }

    @GetMapping("/vi-pham/them")
    public String themViPham(Model model) {
        model.addAttribute("viPhamDTO", new vn.iotstar.dormitory.dto.ViPhamDTO());
        return "quansinh/vipham_form";
    }

    @PostMapping("/vi-pham/luu")
    public String luuViPham(@jakarta.validation.Valid @ModelAttribute("viPhamDTO") vn.iotstar.dormitory.dto.ViPhamDTO dto,
                            org.springframework.validation.BindingResult result,
                            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "quansinh/vipham_form";
        }
        try {
            viPhamService.save(dto);
            redirectAttributes.addFlashAttribute("success", "Đã lập biên bản vi phạm thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/quansinh/vi-pham/them";
        }
        return "redirect:/quansinh/vi-pham";
    }

    @PostMapping("/vi-pham/xu-ly")
    public String xuLyViPham(@RequestParam("maViPham") String maViPham,
                             @RequestParam("hinhThucXuLy") String hinhThucXuLy,
                             org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        viPhamService.xuLy(maViPham, hinhThucXuLy);
        redirectAttributes.addFlashAttribute("success", "Đã cập nhật hình thức xử lý thành công!");
        return "redirect:/quansinh/vi-pham";
    }

    // --- XUẤT FILE PDF ---
    @Autowired
    private vn.iotstar.dormitory.service.PdfExportService pdfExportService;

    @Autowired
    private vn.iotstar.dormitory.repository.HopDongRepository hopDongRepository;

    @Autowired
    private vn.iotstar.dormitory.repository.HoaDonRepository hoaDonRepository;

    @GetMapping("/hop-dong/xuat-pdf/{id}")
    public void xuatPdfHopDong(@PathVariable("id") String id, jakarta.servlet.http.HttpServletResponse response) throws Exception {
        java.util.Optional<vn.iotstar.dormitory.entity.HopDong> hdOpt = hopDongRepository.findById(id);
        if (hdOpt.isPresent()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"HopDong_" + id + ".pdf\"");
            pdfExportService.exportHopDongPdf(hdOpt.get(), response.getOutputStream());
        } else {
            response.sendError(jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy hợp đồng");
        }
    }

    @GetMapping("/hoa-don/xuat-pdf/{id}")
    public void xuatPdfHoaDon(@PathVariable("id") String id, jakarta.servlet.http.HttpServletResponse response) throws Exception {
        java.util.Optional<vn.iotstar.dormitory.entity.HoaDon> hdOpt = hoaDonRepository.findById(id);
        if (hdOpt.isPresent()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"HoaDon_" + id + ".pdf\"");
            pdfExportService.exportHoaDonPdf(hdOpt.get(), response.getOutputStream());
        } else {
            response.sendError(jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy hóa đơn");
        }
    }

    // --- HỖ TRỢ TRỰC TUYẾN / LIVE CHAT ---
    @GetMapping("/chat")
    public String liveChatPage(@RequestParam(name = "maSV", required = false) String maSV, Model model) {
        model.addAttribute("selectedMaSV", maSV != null ? maSV : "");
        return "quansinh/chat";
    }
}
