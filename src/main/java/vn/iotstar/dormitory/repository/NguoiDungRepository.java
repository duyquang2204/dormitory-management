package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, String> {
    NguoiDung findByTenDangNhap(String tenDangNhap);
}
