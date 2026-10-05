package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.GiaHanHopDong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiaHanHopDongRepository extends JpaRepository<GiaHanHopDong, String> {
}
