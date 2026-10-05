package vn.iotstar.dormitory.repository;

import vn.iotstar.dormitory.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, String> {
    Page<HoaDon> findByPhong_Khu_MaKhu(String maKhu, Pageable pageable);
    Page<HoaDon> findByPhong_MaPhong(String maPhong, Pageable pageable);
    Page<HoaDon> findBySinhVien_MaSV(String maSV, Pageable pageable);
    long countByPhong_MaPhongAndTrangThaiNot(String maPhong, String trangThai);
    long countBySinhVien_MaSVAndTrangThaiNot(String maSV, String trangThai);
    long countBySinhVien_MaSVAndTrangThai(String maSV, String trangThai);
    long countByTrangThaiContaining(String trangThai);

    @org.springframework.data.jpa.repository.Query("SELECT h.thang, SUM(h.tongTien) FROM HoaDon h WHERE h.trangThai = 'Đã thanh toán' AND h.nam = :nam GROUP BY h.thang ORDER BY h.thang")
    java.util.List<Object[]> getRevenueByMonthAndYear(@org.springframework.data.repository.query.Param("nam") int nam);
    
    java.util.List<HoaDon> findByPhong_MaPhongAndThangAndNam(String maPhong, int thang, int nam);
    java.util.Optional<HoaDon> findByPhong_MaPhongAndSinhVien_MaSVAndThangAndNam(String maPhong, String maSV, int thang, int nam);

    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT h.nam FROM HoaDon h WHERE h.trangThai = 'Đã thanh toán' ORDER BY h.nam DESC")
    java.util.List<Integer> findDistinctYears();
    
    @org.springframework.data.jpa.repository.Query("SELECT h FROM HoaDon h WHERE h.phong.maPhong = :maPhong AND (h.nam < :nam OR (h.nam = :nam AND h.thang < :thang)) ORDER BY h.nam DESC, h.thang DESC")
    java.util.List<HoaDon> findPreviousInvoices(@org.springframework.data.repository.query.Param("maPhong") String maPhong, @org.springframework.data.repository.query.Param("thang") int thang, @org.springframework.data.repository.query.Param("nam") int nam, org.springframework.data.domain.Pageable pageable);
}
