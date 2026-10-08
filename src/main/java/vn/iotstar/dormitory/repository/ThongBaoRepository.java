package vn.iotstar.dormitory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.iotstar.dormitory.entity.ThongBao;

import java.util.List;

@Repository
public interface ThongBaoRepository extends JpaRepository<ThongBao, String> {

    @Query("SELECT t FROM ThongBao t WHERE t.nguoiNhan IN :nguoiNhans ORDER BY t.ngayTao DESC")
    List<ThongBao> findTop10ByNguoiNhans(List<String> nguoiNhans, org.springframework.data.domain.Pageable pageable);

    @Query("SELECT COUNT(t) FROM ThongBao t WHERE t.nguoiNhan IN :nguoiNhans AND t.daDoc = false")
    long countUnread(List<String> nguoiNhans);

    @Modifying
    @Query("UPDATE ThongBao t SET t.daDoc = true WHERE t.nguoiNhan IN :nguoiNhans AND t.daDoc = false")
    void markAllAsRead(List<String> nguoiNhans);

    boolean existsByNguoiNhanAndLoaiThongBaoAndTieuDe(String nguoiNhan, String loaiThongBao, String tieuDe);
    List<ThongBao> findByNguoiNhan(String nguoiNhan);
}
