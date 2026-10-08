package vn.iotstar.dormitory.service;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("KTX-System <duyquang22042005@gmail.com>");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            logger.info("EMAIL TEXT SENT: to={}", to);
        } catch (Exception e) {
            logger.error("Lỗi khi gửi email đến {}: {}", to, e.getMessage());
        }
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom("Hệ Thống KTX Tư Nhân <duyquang22042005@gmail.com>");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
            logger.info("EMAIL HTML SENT: to={}, subject={}", to, subject);
        } catch (Exception e) {
            logger.error("Lỗi khi gửi HTML email đến {}: {}", to, e.getMessage());
        }
    }

    public void sendOtpEmail(String toEmail, String otpCode, String purpose) {
        String subject = "Mã xác thực OTP (" + otpCode + ") - " + purpose;
        String content = """
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 25px; border: 1px solid #e0e0e0; border-radius: 10px; background-color: #fbfbfb;">
                <div style="text-align: center; border-bottom: 2px solid #198754; padding-bottom: 15px;">
                    <h2 style="color: #198754; margin: 0;">🏢 HỆ THỐNG KÝ TÚC XÁ TƯ NHÂN</h2>
                    <p style="color: #666; font-size: 13px; margin: 5px 0 0 0;">An toàn - Tiện nghi - Đồng hành cùng Sinh viên</p>
                </div>
                <div style="padding: 25px 0;">
                    <p style="font-size: 15px; color: #333;">Xin chào bạn,</p>
                    <p style="font-size: 15px; color: #333;">Bạn vừa yêu cầu mã xác thực OTP để <strong>%s</strong> trên hệ thống Quản lý Ký túc xá.</p>
                    <div style="text-align: center; margin: 30px 0;">
                        <span style="display: inline-block; font-size: 32px; font-weight: bold; letter-spacing: 8px; color: #0d6efd; background-color: #e7f1ff; padding: 12px 30px; border-radius: 8px; border: 1px dashed #0d6efd;">%s</span>
                    </div>
                    <p style="font-size: 13px; color: #dc3545; text-align: center;">⏰ Mã OTP này có hiệu lực trong vòng <strong>5 phút</strong>. Tuyệt đối không chia sẻ mã này cho bất kỳ ai.</p>
                    <p style="font-size: 14px; color: #555; margin-top: 20px;">Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email.</p>
                </div>
                <div style="border-top: 1px solid #e0e0e0; padding-top: 15px; text-align: center; font-size: 12px; color: #888;">
                    Ban Quản lý Ký túc xá tư nhân cao cấp &bull; Hotline: 1900 6868 &bull; Email: hotro@ktx-housing.vn
                </div>
            </div>
            """.formatted(purpose, otpCode);
        sendHtmlEmail(toEmail, subject, content);
    }

    public void sendTiepNhanHoSoEmail(String toEmail, String hoTen, String maDangKy) {
        String subject = "Xác nhận tiếp nhận hồ sơ đăng ký Ký túc xá [" + maDangKy + "]";
        String content = """
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 25px; border: 1px solid #e0e0e0; border-radius: 10px; background-color: #ffffff;">
                <div style="text-align: center; border-bottom: 2px solid #0d6efd; padding-bottom: 15px;">
                    <h2 style="color: #0d6efd; margin: 0;">🏢 XÁC NHẬN TIẾP NHẬN HỒ SƠ LƯU TRÚ</h2>
                </div>
                <div style="padding: 20px 0; font-size: 15px; color: #333;">
                    <p>Kính gửi bạn <strong>%s</strong>,</p>
                    <p>Hệ thống Ký túc xá tư nhân đã nhận được hồ sơ đăng ký lưu trú trực tuyến của bạn.</p>
                    <div style="background-color: #f8f9fa; padding: 15px; border-radius: 6px; border-left: 4px solid #0d6efd; margin: 15px 0;">
                        <p style="margin: 5px 0;"><strong>Mã hồ sơ:</strong> <span style="color: #0d6efd; font-weight: bold;">%s</span></p>
                        <p style="margin: 5px 0;"><strong>Trạng thái:</strong> <span style="color: #ffc107; font-weight: bold; background: #333; padding: 2px 8px; border-radius: 4px;">Chờ duyệt</span></p>
                        <p style="margin: 5px 0;"><strong>Thời gian xét duyệt dự kiến:</strong> Trong vòng 1 - 2 ngày làm việc</p>
                    </div>
                    <p>Ban Quản lý sẽ thẩm tra thông tin và kiểm tra minh chứng sinh viên của bạn. Kết quả phân phòng sẽ được gửi qua email này ngay khi hoàn tất.</p>
                </div>
                <div style="border-top: 1px solid #e0e0e0; padding-top: 15px; text-align: center; font-size: 12px; color: #888;">
                    Ban Quản lý Ký túc xá tư nhân cao cấp &bull; Hotline: 1900 6868
                </div>
            </div>
            """.formatted(hoTen, maDangKy);
        sendHtmlEmail(toEmail, subject, content);
    }

    public void sendTrungTuyenEmail(String toEmail, String hoTen, String maSV, String matKhau,
                                   String soPhong, String tenKhu, String loaiPhong, Double giaPhong, LocalDate ngayBatDau) {
        String formattedGia = giaPhong != null ? NumberFormat.getNumberInstance(Locale.GERMANY).format(giaPhong) + " VNĐ/tháng" : "Theo quy định";
        String formattedNgay = ngayBatDau != null ? ngayBatDau.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Ngay hôm nay";
        String subject = "🎉 CHÚC MỪNG BẠN ĐÃ TRÚNG TUYỂN KÝ TÚC XÁ TƯ NHÂN";
        String content = """
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 25px; border: 1px solid #e0e0e0; border-radius: 10px; background-color: #ffffff;">
                <div style="text-align: center; border-bottom: 3px solid #198754; padding-bottom: 15px;">
                    <h2 style="color: #198754; margin: 0;">🎉 CHÚC MỪNG BẠN ĐÃ ĐƯỢC TIẾP NHẬN VÀO KÝ TÚC XÁ</h2>
                    <p style="color: #555; font-size: 14px; margin: 5px 0 0 0;">Chào mừng bạn đến với cộng đồng sinh viên văn minh & tiện nghi!</p>
                </div>
                <div style="padding: 20px 0; font-size: 15px; color: #333;">
                    <p>Chào bạn <strong>%s</strong>,</p>
                    <p>Hồ sơ đăng ký của bạn đã được Ban Quản lý ký túc xá phê duyệt. Dưới đây là thông tin phòng ở và tài khoản của bạn:</p>
                    
                    <div style="background-color: #e8f5e9; padding: 15px; border-radius: 8px; border: 1px solid #c8e6c9; margin: 15px 0;">
                        <h4 style="color: #2e7d32; margin-top: 0; margin-bottom: 10px;">🏠 THÔNG TIN PHÒNG Ở ĐƯỢC XẾP:</h4>
                        <p style="margin: 4px 0;">&bull; <strong>Phòng:</strong> <span style="font-size: 16px; color: #1b5e20; font-weight: bold;">%s</span> (Khu: %s)</p>
                        <p style="margin: 4px 0;">&bull; <strong>Loại phòng:</strong> %s</p>
                        <p style="margin: 4px 0;">&bull; <strong>Đơn giá thuê:</strong> <span style="color: #d32f2f; font-weight: bold;">%s</span></p>
                        <p style="margin: 4px 0;">&bull; <strong>Ngày bắt đầu lưu trú:</strong> %s</p>
                    </div>

                    <div style="background-color: #e3f2fd; padding: 15px; border-radius: 8px; border: 1px solid #bbdefb; margin: 15px 0;">
                        <h4 style="color: #1565c0; margin-top: 0; margin-bottom: 10px;">🔑 TÀI KHOẢN ĐĂNG NHẬP HỆ THỐNG:</h4>
                        <p style="margin: 4px 0;">&bull; <strong>Mã cư dân (Tên đăng nhập):</strong> <span style="font-weight: bold; color: #0d47a1;">%s</span></p>
                        <p style="margin: 4px 0;">&bull; <strong>Mật khẩu khởi tạo:</strong> <span style="font-weight: bold; color: #d32f2f;">%s</span></p>
                        <p style="margin: 4px 0; font-size: 13px; color: #666;"><em>(Vui lòng đăng nhập vào hệ thống và đổi mật khẩu cá nhân ở lần đầu tiên)</em></p>
                    </div>

                    <div style="padding: 10px 0; font-size: 14px; color: #555;">
                        <p><strong>Hướng dẫn tiếp theo:</strong></p>
                        <ol style="padding-left: 20px; margin: 5px 0;">
                            <li>Mang theo CCCD gốc và Thẻ sinh viên đến Văn phòng KTX để làm thủ tục nhận phòng.</li>
                            <li>Đăng nhập vào hệ thống để theo dõi hợp đồng và thanh toán tiền phòng/điện nước qua VNPAY.</li>
                        </ol>
                    </div>
                </div>
                <div style="border-top: 1px solid #e0e0e0; padding-top: 15px; text-align: center; font-size: 12px; color: #888;">
                    Ban Quản lý Ký túc xá tư nhân cao cấp &bull; Hotline hỗ trợ: 1900 6868
                </div>
            </div>
            """.formatted(hoTen, soPhong, tenKhu, loaiPhong, formattedGia, formattedNgay, maSV, matKhau);
        sendHtmlEmail(toEmail, subject, content);
    }

    public void sendTuChoiEmail(String toEmail, String hoTen, String lyDo) {
        String subject = "Thông báo kết quả xét duyệt hồ sơ Ký túc xá";
        String content = """
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 25px; border: 1px solid #e0e0e0; border-radius: 10px; background-color: #ffffff;">
                <div style="text-align: center; border-bottom: 3px solid #dc3545; padding-bottom: 15px;">
                    <h2 style="color: #dc3545; margin: 0;">THÔNG BÁO KẾT QUẢ XÉT DUYỆT HỒ SƠ</h2>
                </div>
                <div style="padding: 20px 0; font-size: 15px; color: #333;">
                    <p>Chào bạn <strong>%s</strong>,</p>
                    <p>Cảm ơn bạn đã quan tâm và nộp hồ sơ đăng ký lưu trú tại Ký túc xá của chúng tôi.</p>
                    <p>Sau khi xem xét hồ sơ và tình trạng phòng trống hiện tại, Ban Quản lý rất tiếc phải thông báo hồ sơ của bạn <strong>chưa được tiếp nhận</strong> đợt này.</p>
                    
                    <div style="background-color: #fff3cd; padding: 15px; border-radius: 6px; border-left: 4px solid #ffc107; margin: 15px 0;">
                        <p style="margin: 0; color: #856404;"><strong>Lý do:</strong> %s</p>
                    </div>

                    <p style="font-size: 14px; color: #555;">Bạn có thể liên hệ lại Ban Quản lý để được hỗ trợ chuyển đổi sang loại phòng khác hoặc đăng ký vào danh sách chờ đợt sau.</p>
                </div>
                <div style="border-top: 1px solid #e0e0e0; padding-top: 15px; text-align: center; font-size: 12px; color: #888;">
                    Ban Quản lý Ký túc xá tư nhân cao cấp &bull; Hotline: 1900 6868 &bull; Email: hotro@ktx-housing.vn
                </div>
            </div>
            """.formatted(hoTen, (lyDo != null && !lyDo.isEmpty() ? lyDo : "Phòng đăng ký đã hết chỗ hoặc hồ sơ chưa đáp ứng tiêu chí."));
        sendHtmlEmail(toEmail, subject, content);
    }
}
