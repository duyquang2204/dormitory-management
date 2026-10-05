package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.KetQuaSuaChua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KetQuaSuaChuaRepository extends JpaRepository<KetQuaSuaChua, String> {
}
