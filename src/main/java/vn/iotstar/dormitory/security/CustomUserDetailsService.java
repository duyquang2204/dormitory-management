package vn.iotstar.dormitory.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.entity.NguoiDung;
import vn.iotstar.dormitory.entity.SinhVien;
import vn.iotstar.dormitory.repository.NguoiDungRepository;
import vn.iotstar.dormitory.repository.SinhVienRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NguoiDung nguoiDung = nguoiDungRepository.findByTenDangNhap(username);
        if (nguoiDung != null) {
            if ("Bị khóa".equals(nguoiDung.getTrangThai())) {
                throw new UsernameNotFoundException("Tài khoản đã bị khóa");
            }
            List<GrantedAuthority> authorities = new ArrayList<>();
            String role = "ROLE_USER";
            if ("Quản sinh".equals(nguoiDung.getChucVu())) {
                role = "ROLE_QUAN_SINH";
            } else if ("Nhân viên sửa chữa".equals(nguoiDung.getChucVu())) {
                role = "ROLE_NHAN_VIEN";
            } else if ("Quản trị viên".equals(nguoiDung.getChucVu())) {
                role = "ROLE_QUAN_TRI";
            }
            authorities.add(new SimpleGrantedAuthority(role));
            return new CustomUserDetails(nguoiDung.getTenDangNhap(), nguoiDung.getMatKhau(), authorities, nguoiDung.getHoTen());
        }

        SinhVien sinhVien = sinhVienRepository.findByMaSV(username);
        if (sinhVien != null) {
            if (sinhVien.getMatKhau() == null || sinhVien.getMatKhau().isEmpty()) {
                throw new UsernameNotFoundException("Tài khoản sinh viên chưa được thiết lập mật khẩu");
            }
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_SINH_VIEN"));
            return new CustomUserDetails(sinhVien.getMaSV(), sinhVien.getMatKhau(), authorities, sinhVien.getHoTen());
        }

        throw new UsernameNotFoundException("Không tìm thấy tài khoản: " + username);
    }
}
