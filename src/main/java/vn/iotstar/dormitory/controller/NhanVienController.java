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
    public String listCongViec(Model model, Authentication auth) {
        String tenDangNhap = auth.getName();
        model.addAttribute("congViecList", ketQuaSuaChuaService.findByNhanVienTenDangNhap(tenDangNhap));
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
