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
import vn.iotstar.dormitory.service.PhongService;
import vn.iotstar.dormitory.service.SinhVienService;

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
            @RequestParam(name = "size", defaultValue = "12") int size,
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
            @RequestParam(name = "maKhu", required = false) String maKhu,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "12") int size,
            Model model) {
            
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page - 1, size);
        org.springframework.data.domain.Page<Phong> phongPage;
        
        if (maKhu != null && !maKhu.isEmpty()) {
            phongPage = phongService.findByKhu(maKhu, pageable);
        } else {
            // Return empty page if no zone is selected
            phongPage = org.springframework.data.domain.Page.empty(pageable);
        }
        
        model.addAttribute("phongPage", phongPage);
        model.addAttribute("maKhu", maKhu);
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
    public String listDangKy(Model model) {
        model.addAttribute("dangKyList", dangKyKTXService.findAll());
        return "quansinh/dangky_list";
    }

    @PostMapping("/dang-ky/tu-choi/{id}")
    public String tuChoiDangKy(@PathVariable("id") String id) {
        dangKyKTXService.updateStatus(id, "Từ chối");
        return "redirect:/quansinh/dang-ky";
    }

    @GetMapping("/dang-ky/duyet/{id}")
    public String formDuyetDangKy(@PathVariable("id") String id, Model model) {
        java.util.Optional<vn.iotstar.dormitory.entity.DangKyKTX> dkOpt = dangKyKTXService.findById(id);
        if (dkOpt.isPresent()) {
            model.addAttribute("dangKy", dkOpt.get());
            model.addAttribute("phongs", phongService.findAll().stream()
                    .filter(p -> p.getLoaiPhong() != null 
                            && p.getLoaiPhong().getMaLoaiPhong().equals(dkOpt.get().getLoaiPhong().getMaLoaiPhong())
                            && !"Đã đầy".equals(p.getTrangThai()))
                    .toList());
            return "quansinh/phanphong_form";
        }
        return "redirect:/quansinh/dang-ky";
    }

    @PostMapping("/dang-ky/duyet/{id}")
    public String luuDuyetDangKy(@PathVariable("id") String id, 
                                 @RequestParam("maPhong") String maPhong,
                                 @RequestParam("ngayBatDau") java.time.LocalDate ngayBatDau,
                                 @RequestParam("ngayKetThuc") java.time.LocalDate ngayKetThuc,
                                 org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            phanPhongService.assignRoom(id, maPhong, ngayBatDau, ngayKetThuc);
            return "redirect:/quansinh/dang-ky?success";
        } catch (RuntimeException e) {
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

        model.addAttribute("sinhVien", svOpt.get());
        model.addAttribute("phongs", phongService.findAll().stream()
                .filter(p -> !"Đã đầy".equals(p.getTrangThai()))
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
    public String listSuaChua(Model model) {
        model.addAttribute("yeuCauList", yeuCauSuaChuaService.findAll());
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

    // --- QUẢN LÝ HỢP ĐỒNG ---
    @Autowired
    private vn.iotstar.dormitory.service.HopDongService hopDongService;

    @GetMapping("/hop-dong")
    public String listHopDong(Model model) {
        model.addAttribute("hopDongs", hopDongService.findAll());
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
}
