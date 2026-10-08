package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.SinhVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
    SinhVien findByMaSV(String maSV);
    
    Optional<SinhVien> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCccd(String cccd);
    
    Page<SinhVien> findByHoTenContainingIgnoreCase(String keyword, Pageable pageable);

    Page<SinhVien> findByHoTenContainingIgnoreCaseOrMaSVContainingIgnoreCase(String hoTen, String maSV, Pageable pageable);
}
