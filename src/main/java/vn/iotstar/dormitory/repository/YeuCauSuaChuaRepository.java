package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.YeuCauSuaChua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YeuCauSuaChuaRepository extends JpaRepository<YeuCauSuaChua, String> {
    long countByTrangThai(String trangThai);
    java.util.List<YeuCauSuaChua> findBySinhVien_MaSV(String maSV);
}
