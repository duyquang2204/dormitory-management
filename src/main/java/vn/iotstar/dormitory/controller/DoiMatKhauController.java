package vn.iotstar.dormitory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.dormitory.service.NguoiDungService;

@Controller
public class DoiMatKhauController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping("/doi-mat-khau")
    public String showChangePasswordForm() {
        return "doi-mat-khau";
    }

    @PostMapping("/doi-mat-khau")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {
        
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu xác nhận không khớp!");
            return "redirect:/doi-mat-khau";
        }
        
        String username = authentication.getName();
        boolean success = nguoiDungService.changePassword(username, oldPassword, newPassword);
        
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Đổi mật khẩu thành công!");
            return "redirect:/doi-mat-khau";
        } else {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu cũ không chính xác!");
            return "redirect:/doi-mat-khau";
        }
    }
}
