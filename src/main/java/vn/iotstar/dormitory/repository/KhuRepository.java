package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.Khu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhuRepository extends JpaRepository<Khu, String> {
}
