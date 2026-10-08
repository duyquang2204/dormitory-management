package vn.iotstar.dormitory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iotstar.dormitory.entity.OtpXacThuc;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpXacThucRepository extends JpaRepository<OtpXacThuc, Long> {
    
    Optional<OtpXacThuc> findTopByEmailAndLoaiOtpAndDaSuDungFalseOrderByNgayTaoDesc(String email, String loaiOtp);
    
    Optional<OtpXacThuc> findTopByEmailAndOtpCodeAndLoaiOtpAndDaSuDungFalseAndThoiGianHetHanAfterOrderByNgayTaoDesc(
            String email, String otpCode, String loaiOtp, LocalDateTime now);

    java.util.List<OtpXacThuc> findByEmail(String email);
}
