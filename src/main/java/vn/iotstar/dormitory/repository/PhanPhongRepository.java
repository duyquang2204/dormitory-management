package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.PhanPhong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhanPhongRepository extends JpaRepository<PhanPhong, String> {
    long countByPhong_MaPhongAndTrangThai(String maPhong, String trangThai);
    java.util.Optional<PhanPhong> findBySinhVien_MaSVAndTrangThai(String maSV, String trangThai);
    java.util.List<PhanPhong> findBySinhVien_MaSV(String maSV);
    java.util.List<PhanPhong> findByPhong_MaPhongAndTrangThai(String maPhong, String trangThai);

    @org.springframework.data.jpa.repository.Query("SELECT p.phong.maPhong, COUNT(p) FROM PhanPhong p WHERE p.trangThai = 'Đang ở' GROUP BY p.phong.maPhong")
    java.util.List<Object[]> countOccupantsByRoom();
}
