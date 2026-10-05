package vn.iotstar.dormitory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import vn.iotstar.dormitory.dto.ThongBaoDTO;
import vn.iotstar.dormitory.service.ThongBaoService;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private ThongBaoService thongBaoService;

    @ModelAttribute("thongBaoInfo")
    public ThongBaoDTO populateThongBao() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            String username = auth.getName();
            String role = auth.getAuthorities().stream().findFirst().get().getAuthority();
            return thongBaoService.getThongBaoForUser(username, role);
        }
        return new ThongBaoDTO();
    }
}
