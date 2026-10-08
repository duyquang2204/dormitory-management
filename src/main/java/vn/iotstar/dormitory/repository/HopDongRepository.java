package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.HopDong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HopDongRepository extends JpaRepository<HopDong, String> {
    long countByYeuCauCuaSVNotNull();
    java.util.List<HopDong> findByPhanPhong_MaPhanPhong(String maPhanPhong);
    java.util.List<HopDong> findByPhanPhong_SinhVien_MaSV(String maSV);
}
