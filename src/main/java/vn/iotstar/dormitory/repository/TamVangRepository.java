package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.TamVang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TamVangRepository extends JpaRepository<TamVang, String> {
}
