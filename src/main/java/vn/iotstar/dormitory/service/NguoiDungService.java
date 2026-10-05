package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.dto.NguoiDungDTO;
import vn.iotstar.dormitory.entity.NguoiDung;
import vn.iotstar.dormitory.repository.NguoiDungRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NguoiDungService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<NguoiDung> findAll() {
        return nguoiDungRepository.findAll();
    }

    public Optional<NguoiDung> findById(String id) {
        return nguoiDungRepository.findById(id);
    }

    public void save(NguoiDungDTO dto) {
        NguoiDung nd;
        if (dto.getMaNguoiDung() != null && !dto.getMaNguoiDung().isEmpty()) {
            nd = nguoiDungRepository.findById(dto.getMaNguoiDung()).orElse(new NguoiDung());
        } else {
            nd = new NguoiDung();
            nd.setMaNguoiDung(UUID.randomUUID().toString());
        }

        nd.setTenDangNhap(dto.getTenDangNhap());
        nd.setHoTen(dto.getHoTen());
        nd.setSdt(dto.getSdt());
        nd.setChucVu(dto.getChucVu());
        nd.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : "Hoạt động");

        if (dto.getMatKhau() != null && !dto.getMatKhau().isEmpty()) {
            nd.setMatKhau(passwordEncoder.encode(dto.getMatKhau()));
        } else if (nd.getMatKhau() == null) {
            nd.setMatKhau(passwordEncoder.encode("123456"));
        }

        nguoiDungRepository.save(nd);
    }

    @Autowired
    private vn.iotstar.dormitory.repository.SinhVienRepository sinhVienRepository;

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        NguoiDung user = nguoiDungRepository.findByTenDangNhap(username);
        if (user != null) {
            if (passwordEncoder.matches(oldPassword, user.getMatKhau())) {
                user.setMatKhau(passwordEncoder.encode(newPassword));
                nguoiDungRepository.save(user);
                return true;
            }
            return false;
        }

        vn.iotstar.dormitory.entity.SinhVien sv = sinhVienRepository.findByMaSV(username);
        if (sv != null) {
            if (passwordEncoder.matches(oldPassword, sv.getMatKhau())) {
                sv.setMatKhau(passwordEncoder.encode(newPassword));
                sinhVienRepository.save(sv);
                return true;
            }
            return false;
        }

        return false;
    }

    public void deleteById(String id) {
        nguoiDungRepository.deleteById(id);
    }
}
