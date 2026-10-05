package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.dto.HoaDonDTO;
import vn.iotstar.dormitory.entity.HoaDon;
import vn.iotstar.dormitory.entity.Phong;
import vn.iotstar.dormitory.repository.HoaDonRepository;
import vn.iotstar.dormitory.repository.PhongRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private PhongRepository phongRepository;

    @Autowired
    private ThongBaoService thongBaoService;

    // Giả sử giá điện: 3500đ/kwh, nước: 15000đ/m3
    private static final double GIA_DIEN = 3500.0;
    private static final double GIA_NUOC = 15000.0;

    public Page<HoaDon> findAll(Pageable pageable) {
        return hoaDonRepository.findAll(pageable);
    }

    public java.util.List<HoaDon> findAll() {
        return hoaDonRepository.findAll();
    }

    public Page<HoaDon> findByKhu(String maKhu, Pageable pageable) {
        return hoaDonRepository.findByPhong_Khu_MaKhu(maKhu, pageable);
    }
    
    public Page<HoaDon> findByPhong(String maPhong, Pageable pageable) {
        return hoaDonRepository.findByPhong_MaPhong(maPhong, pageable);
    }
    
    public Page<HoaDon> findBySinhVien(String maSV, Pageable pageable) {
        return hoaDonRepository.findBySinhVien_MaSV(maSV, pageable);
    }

    public Optional<HoaDon> findById(String id) {
        return hoaDonRepository.findById(id);
    }
    
    @Autowired
    private vn.iotstar.dormitory.repository.HopDongRepository hopDongRepo;

    public java.util.List<HoaDon> findByPhongAndThangAndNam(String maPhong, int thang, int nam) {
        return hoaDonRepository.findByPhong_MaPhongAndThangAndNam(maPhong, thang, nam);
    }
    
    public long countUnpaidByPhong(String maPhong) {
        return hoaDonRepository.countByPhong_MaPhongAndTrangThaiNot(maPhong, "Đã thanh toán");
    }

    public long countUnpaidBySinhVien(String maSV) {
        return hoaDonRepository.countBySinhVien_MaSVAndTrangThaiNot(maSV, "Đã thanh toán");
    }

    public void save(HoaDonDTO dto) {
        Optional<Phong> pOpt = phongRepository.findById(dto.getMaPhong());
        if (!pOpt.isPresent()) {
            throw new RuntimeException("Không tìm thấy phòng");
        }
        Phong p = pOpt.get();

        // Get students in this room
        java.util.List<vn.iotstar.dormitory.entity.SinhVien> sinhViens = hopDongRepo.findAll().stream()
            .filter(hd -> "Còn hạn".equals(hd.getTrangThai()) 
                       && hd.getPhanPhong() != null 
                       && hd.getPhanPhong().getPhong() != null 
                       && hd.getPhanPhong().getPhong().getMaPhong().equals(dto.getMaPhong()))
            .map(hd -> hd.getPhanPhong().getSinhVien())
            .toList();

        int n = sinhViens.size();
        if (n == 0) {
            throw new RuntimeException("Phòng không có sinh viên đang ở, không thể lập hóa đơn chia theo đầu người.");
        }

        // Tự động tính chỉ số cũ từ tháng trước hoặc hóa đơn gần nhất
        Double autoChiSoDienCu = getChiSoDienMoiNhat(dto.getMaPhong(), dto.getThang(), dto.getNam());
        Double autoChiSoNuocCu = getChiSoNuocMoiNhat(dto.getMaPhong(), dto.getThang(), dto.getNam());

        double totalTienDien = (dto.getChiSoDienMoi() - autoChiSoDienCu) * GIA_DIEN;
        double totalTienNuoc = (dto.getChiSoNuocMoi() - autoChiSoNuocCu) * GIA_NUOC;
        if (totalTienDien < 0) totalTienDien = 0;
        if (totalTienNuoc < 0) totalTienNuoc = 0;
        
        double tienDienPerStudent = totalTienDien / n;
        double tienNuocPerStudent = totalTienNuoc / n;
        double tienPhongPerStudent = p.getLoaiPhong() != null ? p.getLoaiPhong().getDonGia() : 0.0;
        double tongTienPerStudent = tienDienPerStudent + tienNuocPerStudent + tienPhongPerStudent;

        for (vn.iotstar.dormitory.entity.SinhVien sv : sinhViens) {
            HoaDon hoaDon = hoaDonRepository.findByPhong_MaPhongAndSinhVien_MaSVAndThangAndNam(dto.getMaPhong(), sv.getMaSV(), dto.getThang(), dto.getNam()).orElse(null);
            boolean isNew = false;
            if (hoaDon == null) {
                hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(UUID.randomUUID().toString());
                hoaDon.setNgayTao(LocalDate.now());
                hoaDon.setTrangThai("Chưa thanh toán");
                isNew = true;
            }

            hoaDon.setPhong(p);
            hoaDon.setSinhVien(sv);
            hoaDon.setThang(dto.getThang());
            hoaDon.setNam(dto.getNam());
            
            hoaDon.setChiSoDienCu(autoChiSoDienCu);
            hoaDon.setChiSoDienMoi(dto.getChiSoDienMoi());
            hoaDon.setChiSoNuocCu(autoChiSoNuocCu);
            hoaDon.setChiSoNuocMoi(dto.getChiSoNuocMoi());
            
            hoaDon.setTienDien(tienDienPerStudent);
            hoaDon.setTienNuoc(tienNuocPerStudent);
            hoaDon.setTienPhong(tienPhongPerStudent);
            hoaDon.setTongTien(tongTienPerStudent);
            
            if (dto.getTrangThai() != null && !dto.getTrangThai().isEmpty()) {
                hoaDon.setTrangThai(dto.getTrangThai());
            }

            hoaDonRepository.save(hoaDon);
            
            if (isNew) {
                thongBaoService.taoThongBao(sv.getMaSV(), 
                    "Hóa đơn mới", 
                    "Bạn có hóa đơn tháng " + dto.getThang() + "/" + dto.getNam() + " cần thanh toán.", 
                    "HoaDon", "/sinhvien/hoa-don");
            }
        }
    }
    
    public void thanhToan(String id) {
        Optional<HoaDon> hdOpt = hoaDonRepository.findById(id);
        if (hdOpt.isPresent()) {
            HoaDon hd = hdOpt.get();
            hd.setTrangThai("Đã thanh toán");
            hoaDonRepository.save(hd);
        }
    }
    
    public void thanhToanTienMat(String id) {
        Optional<HoaDon> hdOpt = hoaDonRepository.findById(id);
        if (hdOpt.isPresent()) {
            HoaDon hd = hdOpt.get();
            hd.setPhuongThucThanhToan("Tiền mặt");
            hd.setTrangThai("Chờ duyệt (Tiền mặt)");
            hoaDonRepository.save(hd);
        }
    }
    
    public void uploadMinhChung(String id, String url) {
        Optional<HoaDon> hdOpt = hoaDonRepository.findById(id);
        if (hdOpt.isPresent()) {
            HoaDon hd = hdOpt.get();
            hd.setMinhChungThanhToan(url);
            hd.setPhuongThucThanhToan("Chuyển khoản");
            hd.setTrangThai("Chờ duyệt (Chuyển khoản)");
            hoaDonRepository.save(hd);
        }
    }
    
    public java.util.List<Object[]> getRevenueByMonthAndYear(int nam) {
        return hoaDonRepository.getRevenueByMonthAndYear(nam);
    }
    
    public java.util.List<Integer> getDistinctYears() {
        return hoaDonRepository.findDistinctYears();
    }
    
    public Double getChiSoDienMoiNhat(String maPhong, int thang, int nam) {
        org.springframework.data.domain.Pageable topOne = org.springframework.data.domain.PageRequest.of(0, 1);
        java.util.List<HoaDon> invoices = hoaDonRepository.findPreviousInvoices(maPhong, thang, nam, topOne);
        if (!invoices.isEmpty()) {
            return invoices.get(0).getChiSoDienMoi();
        }
        return 0.0;
    }

    public Double getChiSoNuocMoiNhat(String maPhong, int thang, int nam) {
        org.springframework.data.domain.Pageable topOne = org.springframework.data.domain.PageRequest.of(0, 1);
        java.util.List<HoaDon> invoices = hoaDonRepository.findPreviousInvoices(maPhong, thang, nam, topOne);
        if (!invoices.isEmpty()) {
            return invoices.get(0).getChiSoNuocMoi();
        }
        return 0.0;
    }
    
    public void saveMass(vn.iotstar.dormitory.dto.HoaDonMassInputDTO massDto) {
        for (vn.iotstar.dormitory.dto.HoaDonRoomInputDTO roomDto : massDto.getRooms()) {
            if (roomDto.getChiSoDienMoi() != null && roomDto.getChiSoNuocMoi() != null) {
                // Ignore if new is less than old (invalid) or empty
                if (roomDto.getChiSoDienMoi() >= roomDto.getChiSoDienCu() && roomDto.getChiSoNuocMoi() >= roomDto.getChiSoNuocCu()) {
                    HoaDonDTO dto = new HoaDonDTO();
                    dto.setMaPhong(roomDto.getMaPhong());
                    dto.setThang(massDto.getThang());
                    dto.setNam(massDto.getNam());
                    dto.setChiSoDienCu(roomDto.getChiSoDienCu());
                    dto.setChiSoNuocCu(roomDto.getChiSoNuocCu());
                    dto.setChiSoDienMoi(roomDto.getChiSoDienMoi());
                    dto.setChiSoNuocMoi(roomDto.getChiSoNuocMoi());
                    // check if invoice already exists
                    java.util.List<HoaDon> existingList = hoaDonRepository.findByPhong_MaPhongAndThangAndNam(roomDto.getMaPhong(), massDto.getThang(), massDto.getNam());
                    if (!existingList.isEmpty()) {
                        // Assuming mass input edits the first one? But save(dto) loops and edits by student anyway
                        // If it exists, save(dto) will overwrite the values for each student.
                        dto.setMaHoaDon(existingList.get(0).getMaHoaDon());
                        dto.setTrangThai(existingList.get(0).getTrangThai());
                    }
                    save(dto);
                }
            }
        }
    }
}
