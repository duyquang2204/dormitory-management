package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.SinhVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
    SinhVien findByMaSV(String maSV);
    
    Page<SinhVien> findByHoTenContainingIgnoreCase(String keyword, Pageable pageable);
}
