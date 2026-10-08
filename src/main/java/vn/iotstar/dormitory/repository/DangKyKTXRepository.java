package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.DangKyKTX;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DangKyKTXRepository extends JpaRepository<DangKyKTX, String> {
    long countByTrangThai(String trangThai);
    java.util.List<DangKyKTX> findBySinhVien_MaSV(String maSV);
}
