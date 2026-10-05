package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.PhanPhong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhanPhongRepository extends JpaRepository<PhanPhong, String> {
    long countByPhong_MaPhongAndTrangThai(String maPhong, String trangThai);
}
