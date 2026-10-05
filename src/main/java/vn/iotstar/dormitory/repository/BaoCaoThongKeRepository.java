package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.BaoCaoThongKe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaoCaoThongKeRepository extends JpaRepository<BaoCaoThongKe, String> {
}
