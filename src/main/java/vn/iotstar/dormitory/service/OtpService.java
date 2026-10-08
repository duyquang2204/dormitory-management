package vn.iotstar.dormitory.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.entity.OtpXacThuc;
import vn.iotstar.dormitory.repository.OtpXacThucRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);

    @Autowired
    private OtpXacThucRepository otpXacThucRepository;

    @Autowired
    private EmailService emailService;

    public String generateAndSendOtp(String email, String loaiOtp, String purpose) {
        // Sinh mã 6 chữ số
        String otpCode = String.format("%06d", new Random().nextInt(1000000));
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);

        // Lưu vào DB
        OtpXacThuc otp = new OtpXacThuc(email, otpCode, loaiOtp, expiresAt);
        otpXacThucRepository.save(otp);

        logger.info("Generated OTP for {}: code={}, type={}", email, otpCode, loaiOtp);

        // Gửi qua email
        emailService.sendOtpEmail(email, otpCode, purpose);

        return otpCode;
    }

    public boolean verifyOtp(String email, String otpCode, String loaiOtp) {
        if (email == null || otpCode == null || loaiOtp == null) {
            return false;
        }

        Optional<OtpXacThuc> otpOpt = otpXacThucRepository
                .findTopByEmailAndOtpCodeAndLoaiOtpAndDaSuDungFalseAndThoiGianHetHanAfterOrderByNgayTaoDesc(
                        email.trim(), otpCode.trim(), loaiOtp, LocalDateTime.now());

        if (otpOpt.isPresent()) {
            OtpXacThuc otp = otpOpt.get();
            otp.setDaSuDung(true);
            otpXacThucRepository.save(otp);
            logger.info("OTP verified successfully for email={}", email);
            return true;
        }

        logger.warn("OTP verification failed for email={}, code={}", email, otpCode);
        return false;
    }
}
