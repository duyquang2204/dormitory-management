package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.Phong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhongRepository extends JpaRepository<Phong, String> {
    Page<Phong> findByKhu_MaKhu(String maKhu, Pageable pageable);
}
