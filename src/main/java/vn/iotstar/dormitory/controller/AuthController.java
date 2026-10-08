package vn.iotstar.dormitory.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.dormitory.dto.RegisterDTO;
import vn.iotstar.dormitory.dto.ResetPasswordDTO;
import vn.iotstar.dormitory.entity.SinhVien;
import vn.iotstar.dormitory.service.OtpService;
import vn.iotstar.dormitory.service.SinhVienService;

@Controller
public class AuthController {

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private OtpService otpService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // --- 1. ĐĂNG KÝ TÀI KHOẢN ---
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("registerDTO") RegisterDTO dto,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "auth/register";
        }

        try {
            SinhVien sv = sinhVienService.register(dto);
            redirectAttributes.addFlashAttribute("email", sv.getEmail());
            redirectAttributes.addFlashAttribute("successMessage", 
                    "Đăng ký thành công! Mã OTP kích hoạt đã được gửi về email của bạn. Vui lòng kiểm tra hộp thư.");
            return "redirect:/verify-otp?email=" + sv.getEmail();
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/register";
        }
    }

    // --- 2. XÁC THỰC MÃ OTP KÍCH HOẠT ---
    @GetMapping("/verify-otp")
    public String verifyOtpForm(@RequestParam(value = "email", required = false) String email, Model model) {
        if (email == null || email.trim().isEmpty()) {
            return "redirect:/login";
        }
        model.addAttribute("email", email);
        return "auth/verify_otp";
    }

    @PostMapping("/verify-otp")
    public String handleVerifyOtp(@RequestParam("email") String email,
                                  @RequestParam("otpCode") String otpCode,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        try {
            SinhVien sv = sinhVienService.activateAccount(email, otpCode);
            redirectAttributes.addFlashAttribute("successMessage", 
                    "Chúc mừng bạn đã kích hoạt tài khoản thành công! Tên đăng nhập của bạn là: " + sv.getMaSV() + " (hoặc email: " + sv.getEmail() + ")");
            return "redirect:/login?verified";
        } catch (Exception e) {
            model.addAttribute("email", email);
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/verify_otp";
        }
    }

    @GetMapping("/resend-otp")
    public String resendOtp(@RequestParam("email") String email, 
                            @RequestParam(value = "type", defaultValue = "DANG_KY") String type,
                            RedirectAttributes redirectAttributes) {
        try {
            String purpose = "DANG_KY".equals(type) ? "Kích hoạt tài khoản Ký túc xá" : "Đặt lại mật khẩu đăng nhập";
            otpService.generateAndSendOtp(email, type, purpose);
            redirectAttributes.addFlashAttribute("successMessage", "Mã OTP mới đã được gửi lại về email của bạn.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể gửi lại mã OTP: " + e.getMessage());
        }

        if ("QUEN_MAT_KHAU".equals(type)) {
            return "redirect:/reset-password?email=" + email;
        }
        return "redirect:/verify-otp?email=" + email;
    }

    // --- 3. QUÊN MẬT KHẨU ---
    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "auth/forgot_password";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("account") String account,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {
        try {
            String email = sinhVienService.sendForgotPasswordOtp(account);
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("successMessage", 
                    "Mã OTP đặt lại mật khẩu đã được gửi đến email: " + email);
            return "redirect:/reset-password?email=" + email;
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("account", account);
            return "auth/forgot_password";
        }
    }

    // --- 4. ĐẶT LẠI MẬT KHẨU BẰNG OTP ---
    @GetMapping("/reset-password")
    public String resetPasswordForm(@RequestParam(value = "email", required = false) String email, Model model) {
        if (email == null || email.trim().isEmpty()) {
            return "redirect:/forgot-password";
        }
        ResetPasswordDTO dto = new ResetPasswordDTO();
        dto.setEmail(email);
        model.addAttribute("resetPasswordDTO", dto);
        return "auth/reset_password";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(@Valid @ModelAttribute("resetPasswordDTO") ResetPasswordDTO dto,
                                      BindingResult result,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "auth/reset_password";
        }

        try {
            sinhVienService.resetPassword(dto);
            redirectAttributes.addFlashAttribute("successMessage", 
                    "Đặt lại mật khẩu thành công! Bạn có thể đăng nhập ngay với mật khẩu mới.");
            return "redirect:/login?resetSuccess";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/reset_password";
        }
    }
}
