package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.dto.SinhVienDTO;
import vn.iotstar.dormitory.entity.SinhVien;
import vn.iotstar.dormitory.repository.SinhVienRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class SinhVienService {

    @Autowired
    private SinhVienRepository sinhVienRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public List<SinhVien> findAll() {
        return sinhVienRepository.findAll();
    }
    
    public Page<SinhVien> findAll(Pageable pageable) {
        return sinhVienRepository.findAll(pageable);
    }
    
    public Page<SinhVien> searchByKeyword(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return sinhVienRepository.findByHoTenContainingIgnoreCase(keyword, pageable);
        }
        return sinhVienRepository.findAll(pageable);
    }

    public Optional<SinhVien> findById(String id) {
        return sinhVienRepository.findById(id);
    }
    
    public void delete(String id) {
        sinhVienRepository.deleteById(id);
    }

    public void save(SinhVienDTO dto) {
        boolean isNew = false;
        SinhVien sv = sinhVienRepository.findById(dto.getMaSV()).orElseGet(() -> new SinhVien());
        if (sv.getMaSV() == null) {
            isNew = true;
        }
        
        sv.setMaSV(dto.getMaSV());
        sv.setHoTen(dto.getHoTen());
        sv.setNgaySinh(dto.getNgaySinh());
        sv.setGioiTinh(dto.getGioiTinh());
        sv.setQueQuan(dto.getQueQuan());
        sv.setCccd(dto.getCccd());
        sv.setSdt(dto.getSdt());
        sv.setEmail(dto.getEmail());
        sv.setKhoa(dto.getKhoa());
        sv.setNamHoc(dto.getNamHoc());
        sv.setDienUuTien(dto.getDienUuTien());
        
        // Cấp mật khẩu mặc định nếu chưa có
        String plainPassword = null;
        if (dto.getMatKhau() != null && !dto.getMatKhau().isEmpty()) {
            plainPassword = dto.getMatKhau();
            sv.setMatKhau(passwordEncoder.encode(plainPassword));
        } else if (sv.getMatKhau() == null) {
            plainPassword = "123456";
            sv.setMatKhau(passwordEncoder.encode(plainPassword));
        }
        
        sinhVienRepository.save(sv);
        
        // Gửi email thông báo tài khoản nếu là sinh viên mới tạo và có email
        if (isNew && sv.getEmail() != null && !sv.getEmail().isEmpty()) {
            String subject = "Thông báo: Đăng ký Ký túc xá thành công";
            String body = "Chào " + sv.getHoTen() + ",\n\n" +
                          "Hồ sơ đăng ký lưu trú KTX của bạn đã được tiếp nhận và tạo tài khoản thành công.\n" +
                          "Dưới đây là thông tin đăng nhập hệ thống:\n" +
                          "- Tên đăng nhập (Mã SV): " + sv.getMaSV() + "\n" +
                          "- Mật khẩu: " + (plainPassword != null ? plainPassword : "Đã được mã hóa") + "\n\n" +
                          "Vui lòng đăng nhập vào hệ thống để kiểm tra trạng thái phân phòng và các hóa đơn hàng tháng.\n" +
                          "Trân trọng,\nBan quản lý KTX.";
            emailService.sendEmail(sv.getEmail(), subject, body);
        }
    }
}
