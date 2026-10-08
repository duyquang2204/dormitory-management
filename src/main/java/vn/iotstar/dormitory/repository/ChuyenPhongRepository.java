package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.ChuyenPhong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChuyenPhongRepository extends JpaRepository<ChuyenPhong, String> {
    java.util.List<ChuyenPhong> findByPhanPhong_MaPhanPhong(String maPhanPhong);
}
