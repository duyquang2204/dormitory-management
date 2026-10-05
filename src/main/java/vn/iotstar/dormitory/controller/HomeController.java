package vn.iotstar.dormitory.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @org.springframework.beans.factory.annotation.Autowired
    private vn.iotstar.dormitory.service.ThongBaoService thongBaoService;

    @GetMapping("/")
    public String index() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
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
        return "redirect:/login";
    }

    @GetMapping("/thong-bao/doc/{id}")
    public String docThongBao(@org.springframework.web.bind.annotation.PathVariable("id") String id) {
        String redirectLink = thongBaoService.markAsRead(id);
        return "redirect:" + redirectLink;
    }
}
