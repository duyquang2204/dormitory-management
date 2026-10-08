package vn.iotstar.dormitory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.dormitory.service.KetQuaSuaChuaService;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienController {

    @Autowired
    private KetQuaSuaChuaService ketQuaSuaChuaService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "redirect:/nhanvien/sua-chua";
    }

    @GetMapping("/sua-chua")
    public String listCongViec(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model, Authentication auth) {
        String tenDangNhap = auth.getName();
        java.util.List<vn.iotstar.dormitory.entity.KetQuaSuaChua> fullList = ketQuaSuaChuaService.findByNhanVienTenDangNhap(tenDangNhap);

        int totalElements = fullList.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        if (totalPages == 0) totalPages = 1;
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;

        int start = Math.min((page - 1) * size, totalElements);
        int end = Math.min(start + size, totalElements);
        java.util.List<vn.iotstar.dormitory.entity.KetQuaSuaChua> pagedList = fullList.subList(start, end);

        org.springframework.data.domain.Page<vn.iotstar.dormitory.entity.KetQuaSuaChua> congViecPage =
                new org.springframework.data.domain.PageImpl<>(pagedList, org.springframework.data.domain.PageRequest.of(page - 1, size), totalElements);

        model.addAttribute("congViecPage", congViecPage);
        model.addAttribute("congViecList", congViecPage.getContent());
        return "nhanvien/suachua_list";
    }

    @PostMapping("/sua-chua/cap-nhat")
    public String capNhatCongViec(@RequestParam("maPhanCong") String maPhanCong,
                                  @RequestParam("noiDungXuLy") String noiDungXuLy,
                                  @RequestParam("ketQua") String ketQua,
                                  @RequestParam(value = "hinhAnhFile", required = false) org.springframework.web.multipart.MultipartFile hinhAnhFile) {
        ketQuaSuaChuaService.updateResult(maPhanCong, noiDungXuLy, ketQua, hinhAnhFile);
        return "redirect:/nhanvien/sua-chua?success";
    }
}
