package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.ViPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViPhamRepository extends JpaRepository<ViPham, String> {
    Page<ViPham> findBySinhVien_MaSVContainingIgnoreCase(String keyword, Pageable pageable);
    List<ViPham> findBySinhVien_MaSV(String maSV);
    long countBySinhVien_MaSV(String maSV);
    long countByTrangThaiXuLy(String trangThaiXuLy);
    long countBySinhVien_MaSVAndTrangThaiXuLy(String maSV, String trangThaiXuLy);
}
