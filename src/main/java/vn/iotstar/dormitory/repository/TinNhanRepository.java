package vn.iotstar.dormitory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.iotstar.dormitory.entity.TinNhan;

import java.util.List;

@Repository
public interface TinNhanRepository extends JpaRepository<TinNhan, Long> {

    List<TinNhan> findByMaSVOrderByThoiGianAsc(String maSV);

    long countByMaSVAndVaiTroGuiAndDaDocFalse(String maSV, String vaiTroGui);

    long countByVaiTroGuiAndDaDocFalse(String vaiTroGui);

    @Query("SELECT DISTINCT t.maSV FROM TinNhan t")
    List<String> findDistinctMaSVList();
}
