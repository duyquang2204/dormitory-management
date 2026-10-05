package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.dto.DangKyKTXDTO;
import vn.iotstar.dormitory.entity.DangKyKTX;
import vn.iotstar.dormitory.entity.LoaiPhong;
import vn.iotstar.dormitory.entity.SinhVien;
import vn.iotstar.dormitory.repository.DangKyKTXRepository;
import vn.iotstar.dormitory.repository.LoaiPhongRepository;
import vn.iotstar.dormitory.repository.SinhVienRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DangKyKTXService {

    @Autowired
    private DangKyKTXRepository dangKyKTXRepository;
    
    @Autowired
    private LoaiPhongRepository loaiPhongRepository;
    
    @Autowired
    private SinhVienRepository sinhVienRepository;

    public List<DangKyKTX> findAll() {
        return dangKyKTXRepository.findAll();
    }
    
    public List<DangKyKTX> findBySinhVienId(String maSV) {
        return dangKyKTXRepository.findAll().stream()
                .filter(dk -> dk.getSinhVien() != null && dk.getSinhVien().getMaSV().equals(maSV))
                .toList();
    }
    
    public Optional<DangKyKTX> findById(String id) {
        return dangKyKTXRepository.findById(id);
    }

    @Autowired
    private vn.iotstar.dormitory.service.ViPhamService viPhamService;

    public DangKyKTX createRegistration(DangKyKTXDTO dto, String maSV) {
        long violations = viPhamService.countByMaSV(maSV);
        if (violations >= 3) {
            throw new RuntimeException("Bạn không thể đăng ký KTX vì đã có " + violations + " vi phạm kỷ luật.");
        }
        
        DangKyKTX dk = new DangKyKTX();
        dk.setMaDangKy(UUID.randomUUID().toString());
        dk.setNgayDangKy(LocalDate.now());
        dk.setTrangThai("Chờ duyệt");
        dk.setGhiChu(dto.getGhiChu());
        
        sinhVienRepository.findById(maSV).ifPresent(dk::setSinhVien);
        loaiPhongRepository.findById(dto.getMaLoaiPhong()).ifPresent(dk::setLoaiPhong);
        
        return dangKyKTXRepository.save(dk);
    }
    @Autowired
    private vn.iotstar.dormitory.service.ThongBaoService thongBaoService;
    
    public void updateStatus(String maDangKy, String status) {
        dangKyKTXRepository.findById(maDangKy).ifPresent(dk -> {
            dk.setTrangThai(status);
            dangKyKTXRepository.save(dk);
            
            if ("Từ chối".equals(status)) {
                thongBaoService.taoThongBao(dk.getSinhVien().getMaSV(), "Đơn đăng ký phòng bị từ chối", "Rất tiếc, đơn đăng ký của bạn không được chấp thuận.", "DangKy", "/sinhvien/dang-ky");
            }
        });
    }
}
