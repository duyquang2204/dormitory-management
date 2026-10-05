package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.TraPhong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraPhongRepository extends JpaRepository<TraPhong, String> {
}
