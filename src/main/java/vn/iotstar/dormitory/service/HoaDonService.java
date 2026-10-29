package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.dto.HoaDonDTO;
import vn.iotstar.dormitory.entity.HoaDon;
import vn.iotstar.dormitory.entity.Phong;
import vn.iotstar.dormitory.entity.SinhVien;
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

    @Autowired
    private vn.iotstar.dormitory.repository.PhanPhongRepository phanPhongRepo;

    @Autowired
    private vn.iotstar.dormitory.repository.ViPhamRepository viPhamRepo;

    // Giá mặc định gợi ý nếu quản sinh không nhập: điện: 3500đ/kwh, nước: 15000đ/m3
    public static final double GIA_DIEN_MAC_DINH = 3500.0;
    public static final double GIA_NUOC_MAC_DINH = 15000.0;

    @jakarta.annotation.PostConstruct
    public void syncRoomMeterReadings() {
        try {
            java.util.List<Phong> phongs = phongRepository.findAll();
            for (Phong p : phongs) {
                if (p.getChiSoDienHienTai() == null || p.getChiSoDienHienTai() == 0.0) {
                    org.springframework.data.domain.Pageable topOne = org.springframework.data.domain.PageRequest.of(0, 1);
                    java.util.List<HoaDon> invoices = hoaDonRepository.findPreviousInvoices(p.getMaPhong(), 13, 9999, topOne);
                    if (!invoices.isEmpty()) {
                        HoaDon latest = invoices.get(0);
                        boolean updated = false;
                        if (latest.getChiSoDienMoi() != null) {
                            p.setChiSoDienHienTai(latest.getChiSoDienMoi());
                            updated = true;
                        }
                        if (latest.getChiSoNuocMoi() != null) {
                            p.setChiSoNuocHienTai(latest.getChiSoNuocMoi());
                            updated = true;
                        }
                        if (updated) {
                            phongRepository.save(p);
                        }
                    }
                }
            }
        } catch (Exception ignored) {}
    }

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

    public HoaDon createTienPhongInvoice(vn.iotstar.dormitory.entity.SinhVien sv, Phong p, LocalDate ngayBatDau, Double donGiaThang, int soThang) {
        if (sv == null || p == null) return null;

        LocalDate hanNop = (ngayBatDau != null ? ngayBatDau : LocalDate.now()).plusDays(15);
        double donGia = donGiaThang != null ? donGiaThang : (p.getLoaiPhong() != null && p.getLoaiPhong().getDonGia() != null ? p.getLoaiPhong().getDonGia() : 0.0);
        int thangSo = soThang > 0 ? soThang : 12;
        double tongTienPhong = donGia * thangSo;

        HoaDon hd = new HoaDon();
        hd.setMaHoaDon("HDP" + (System.currentTimeMillis() % 10000000));
        hd.setPhong(p);
        hd.setSinhVien(sv);
        hd.setThang(ngayBatDau != null ? ngayBatDau.getMonthValue() : LocalDate.now().getMonthValue());
        hd.setNam(ngayBatDau != null ? ngayBatDau.getYear() : LocalDate.now().getYear());
        hd.setLoaiHoaDon("TIEN_PHONG");
        hd.setTienPhong(tongTienPhong);
        hd.setTienDien(0.0);
        hd.setTienNuoc(0.0);
        hd.setTongTien(tongTienPhong);
        hd.setNgayTao(LocalDate.now());
        hd.setHanThanhToan(hanNop);
        hd.setTrangThai("Chưa thanh toán");
        hd.setGhiChu("Tiền thuê chỗ ở KTX 01 năm (" + thangSo + " tháng)");

        HoaDon saved = hoaDonRepository.save(hd);

        java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String hanNopStr = hanNop.format(dtf);
        thongBaoService.taoThongBao(sv.getMaSV(),
                "Hóa đơn tiền phòng 01 năm mới",
                "Bạn có hóa đơn tiền phòng KTX 01 năm với số tiền " + String.format("%,.0f", tongTienPhong) + " đ. Thời hạn hoàn tất nộp trong vòng 15 ngày kể từ ngày vào ở (Hạn chót: " + hanNopStr + "). Vui lòng thanh toán đúng hạn để tránh bị thu hồi phòng và buộc rời khỏi KTX.",
                "HoaDon", "/sinhvien/hoa-don");

        return saved;
    }

    public void save(HoaDonDTO dto) {
        Optional<Phong> pOpt = phongRepository.findById(dto.getMaPhong());
        if (!pOpt.isPresent()) {
            throw new RuntimeException("Không tìm thấy phòng");
        }
        Phong p = pOpt.get();

        // Get students currently staying in this room
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

        // Lấy đơn giá điện nước do quản sinh nhập, nếu để trống thì dùng giá mặc định
        double donGiaDien = (dto.getDonGiaDien() != null && dto.getDonGiaDien() > 0) ? dto.getDonGiaDien() : GIA_DIEN_MAC_DINH;
        double donGiaNuoc = (dto.getDonGiaNuoc() != null && dto.getDonGiaNuoc() > 0) ? dto.getDonGiaNuoc() : GIA_NUOC_MAC_DINH;

        // Tự động tính chỉ số cũ từ tháng trước hoặc hóa đơn gần nhất
        Double autoChiSoDienCu = getChiSoDienMoiNhat(dto.getMaPhong(), dto.getThang(), dto.getNam());
        Double autoChiSoNuocCu = getChiSoNuocMoiNhat(dto.getMaPhong(), dto.getThang(), dto.getNam());

        double totalTienDien = (dto.getChiSoDienMoi() - autoChiSoDienCu) * donGiaDien;
        double totalTienNuoc = (dto.getChiSoNuocMoi() - autoChiSoNuocCu) * donGiaNuoc;
        if (totalTienDien < 0) totalTienDien = 0;
        if (totalTienNuoc < 0) totalTienNuoc = 0;
        
        double tienDienPerStudent = totalTienDien / n;
        double tienNuocPerStudent = totalTienNuoc / n;
        // TÁCH HÓA ĐƠN: Hóa đơn điện nước KHÔNG cộng tiền phòng nữa!
        double tienPhongPerStudent = 0.0;
        double tongTienPerStudent = tienDienPerStudent + tienNuocPerStudent;

        LocalDate hanThanhToan = LocalDate.of(dto.getNam(), dto.getThang(), 25);

        for (vn.iotstar.dormitory.entity.SinhVien sv : sinhViens) {
            HoaDon hoaDon = hoaDonRepository.findByPhong_MaPhongAndSinhVien_MaSVAndThangAndNam(dto.getMaPhong(), sv.getMaSV(), dto.getThang(), dto.getNam())
                    .filter(h -> "DIEN_NUOC".equals(h.getLoaiHoaDon()) || h.getLoaiHoaDon() == null)
                    .orElse(null);
            boolean isNew = false;
            if (hoaDon == null) {
                hoaDon = new HoaDon();
                hoaDon.setMaHoaDon("HDDN" + (System.currentTimeMillis() % 10000000) + "_" + sv.getMaSV());
                hoaDon.setNgayTao(LocalDate.now());
                hoaDon.setTrangThai("Chưa thanh toán");
                isNew = true;
            }

            hoaDon.setLoaiHoaDon("DIEN_NUOC");
            hoaDon.setPhong(p);
            hoaDon.setSinhVien(sv);
            hoaDon.setThang(dto.getThang());
            hoaDon.setNam(dto.getNam());
            
            hoaDon.setChiSoDienCu(autoChiSoDienCu);
            hoaDon.setChiSoDienMoi(dto.getChiSoDienMoi());
            hoaDon.setChiSoNuocCu(autoChiSoNuocCu);
            hoaDon.setChiSoNuocMoi(dto.getChiSoNuocMoi());
            
            hoaDon.setDonGiaDien(donGiaDien);
            hoaDon.setDonGiaNuoc(donGiaNuoc);
            hoaDon.setHanThanhToan(hanThanhToan);
            hoaDon.setGhiChu("Hóa đơn dịch vụ điện nước tháng " + dto.getThang() + "/" + dto.getNam());

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
                    "Hóa đơn điện nước mới", 
                    "Bạn có hóa đơn điện nước tháng " + dto.getThang() + "/" + dto.getNam() + " cần thanh toán trước ngày 25.", 
                    "HoaDon", "/sinhvien/hoa-don");
            }
        }

        // Cập nhật chỉ số điện nước mới nhất vào thực thể Phong để lưu trữ cố định theo phòng
        if (dto.getChiSoDienMoi() != null) {
            p.setChiSoDienHienTai(dto.getChiSoDienMoi());
        }
        if (dto.getChiSoNuocMoi() != null) {
            p.setChiSoNuocHienTai(dto.getChiSoNuocMoi());
        }
        phongRepository.save(p);
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

    public java.util.List<Object[]> getTienPhongRevenueByMonthAndYear(int nam) {
        return hoaDonRepository.getTienPhongRevenueByMonthAndYear(nam);
    }

    public java.util.List<Object[]> getDienNuocRevenueByMonthAndYear(int nam) {
        return hoaDonRepository.getDienNuocRevenueByMonthAndYear(nam);
    }
    
    public java.util.List<Integer> getDistinctYears() {
        return hoaDonRepository.findDistinctYears();
    }
    
    public Double getChiSoDienMoiNhat(String maPhong, int thang, int nam) {
        org.springframework.data.domain.Pageable topOne = org.springframework.data.domain.PageRequest.of(0, 1);
        java.util.List<HoaDon> invoices = hoaDonRepository.findPreviousInvoices(maPhong, thang, nam, topOne);
        if (!invoices.isEmpty() && invoices.get(0).getChiSoDienMoi() != null) {
            return invoices.get(0).getChiSoDienMoi();
        }
        // Fallback lấy chỉ số đã lưu trực tiếp trong thực thể Phòng
        Optional<Phong> pOpt = phongRepository.findById(maPhong);
        if (pOpt.isPresent() && pOpt.get().getChiSoDienHienTai() != null) {
            return pOpt.get().getChiSoDienHienTai();
        }
        return 0.0;
    }

    public Double getChiSoNuocMoiNhat(String maPhong, int thang, int nam) {
        org.springframework.data.domain.Pageable topOne = org.springframework.data.domain.PageRequest.of(0, 1);
        java.util.List<HoaDon> invoices = hoaDonRepository.findPreviousInvoices(maPhong, thang, nam, topOne);
        if (!invoices.isEmpty() && invoices.get(0).getChiSoNuocMoi() != null) {
            return invoices.get(0).getChiSoNuocMoi();
        }
        // Fallback lấy chỉ số đã lưu trực tiếp trong thực thể Phòng
        Optional<Phong> pOpt = phongRepository.findById(maPhong);
        if (pOpt.isPresent() && pOpt.get().getChiSoNuocHienTai() != null) {
            return pOpt.get().getChiSoNuocHienTai();
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
                    dto.setDonGiaDien(massDto.getDonGiaDien());
                    dto.setDonGiaNuoc(massDto.getDonGiaNuoc());
                    // check if invoice already exists
                    java.util.List<HoaDon> existingList = hoaDonRepository.findByPhong_MaPhongAndThangAndNam(roomDto.getMaPhong(), massDto.getThang(), massDto.getNam());
                    if (!existingList.isEmpty()) {
                        dto.setMaHoaDon(existingList.get(0).getMaHoaDon());
                        dto.setTrangThai(existingList.get(0).getTrangThai());
                    }
                    save(dto);
                }
            }
        }
    }

    public Page<HoaDon> findByLoaiHoaDon(String loaiHoaDon, Pageable pageable) {
        return hoaDonRepository.findByLoaiHoaDon(loaiHoaDon, pageable);
    }

    public Page<HoaDon> findByKhuAndLoaiHoaDon(String maKhu, String loaiHoaDon, Pageable pageable) {
        return hoaDonRepository.findByPhong_Khu_MaKhuAndLoaiHoaDon(maKhu, loaiHoaDon, pageable);
    }

    public Page<HoaDon> findOverdue(Pageable pageable) {
        return hoaDonRepository.findOverdueInvoices(LocalDate.now(), pageable);
    }

    public Page<HoaDon> findOverdueByKhu(String maKhu, Pageable pageable) {
        return hoaDonRepository.findOverdueInvoicesByKhu(maKhu, LocalDate.now(), pageable);
    }

    public Page<HoaDon> findBySinhVienAndLoai(String maSV, String loaiHoaDon, Pageable pageable) {
        return hoaDonRepository.findBySinhVien_MaSVAndLoaiHoaDon(maSV, loaiHoaDon, pageable);
    }

    public Optional<HoaDon> findTienPhongInvoiceBySinhVien(String maSV) {
        return hoaDonRepository.findFirstBySinhVien_MaSVAndLoaiHoaDonOrderByNgayTaoDesc(maSV, "TIEN_PHONG");
    }

    @org.springframework.transaction.annotation.Transactional
    public void xuLyQuaHanBuocRoiKTX(String maHoaDon) {
        HoaDon hd = hoaDonRepository.findById(maHoaDon)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn: " + maHoaDon));

        if ("Đã thanh toán".equalsIgnoreCase(hd.getTrangThai())) {
            throw new RuntimeException("Hóa đơn này đã được thanh toán, không thể xử lý kỷ luật!");
        }

        SinhVien sv = hd.getSinhVien();
        if (sv == null) {
            throw new RuntimeException("Hóa đơn không gắn với sinh viên cụ thể.");
        }

        // 1. Cập nhật trạng thái hóa đơn
        hd.setTrangThai("Quá hạn (Đã đuổi KTX)");
        hoaDonRepository.save(hd);

        // 2. Chấm dứt Hợp đồng
        java.util.List<vn.iotstar.dormitory.entity.HopDong> activeContracts = hopDongRepo.findByPhanPhong_SinhVien_MaSV(sv.getMaSV());
        for (vn.iotstar.dormitory.entity.HopDong hopDong : activeContracts) {
            if ("Còn hạn".equals(hopDong.getTrangThai())) {
                hopDong.setTrangThai("Đã chấm dứt (Buộc rời KTX)");
                hopDongRepo.save(hopDong);
            }
        }

        // 3. Kết thúc phân phòng & cập nhật trạng thái phòng (mở lại chỗ trống)
        java.util.List<vn.iotstar.dormitory.entity.PhanPhong> pps = phanPhongRepo.findBySinhVien_MaSV(sv.getMaSV());
        for (vn.iotstar.dormitory.entity.PhanPhong pp : pps) {
            if ("Đang ở".equals(pp.getTrangThai())) {
                pp.setTrangThai("Đã kết thúc");
                phanPhongRepo.save(pp);

                if (pp.getPhong() != null) {
                    Phong p = pp.getPhong();
                    long cnt = phanPhongRepo.countByPhong_MaPhongAndTrangThai(p.getMaPhong(), "Đang ở");
                    int max = (p.getLoaiPhong() != null && p.getLoaiPhong().getSoNguoiToiDa() != null) ? p.getLoaiPhong().getSoNguoiToiDa() : 4;
                    if (cnt == 0) {
                        p.setTrangThai("Còn trống");
                    } else if (cnt < max) {
                        p.setTrangThai("Đang ở");
                    } else {
                        p.setTrangThai("Đã đầy");
                    }
                    phongRepository.save(p);
                }
            }
        }

        // 4. Lập biên bản kỷ luật vi phạm
        vn.iotstar.dormitory.entity.ViPham vp = new vn.iotstar.dormitory.entity.ViPham();
        vp.setMaViPham(UUID.randomUUID().toString());
        vp.setSinhVien(sv);
        vp.setNgayLapBienBan(LocalDate.now());
        vp.setNgayViPham(hd.getHanThanhToan() != null ? hd.getHanThanhToan() : LocalDate.now());
        vp.setDiaDiem(hd.getPhong() != null ? "Phòng " + hd.getPhong().getSoPhong() : "Ký túc xá");
        vp.setNoiDung("Không hoàn thành đóng tiền phòng 01 năm quá hạn 15 ngày theo quy định Hợp đồng");
        vp.setHinhThucXuLy("Đuổi khỏi KTX");
        vp.setTrangThaiXuLy("Đã xử lý");
        viPhamRepo.save(vp);

        // 5. Gửi thông báo khẩn cho sinh viên
        thongBaoService.taoThongBao(sv.getMaSV(),
                "Quyết định kỷ luật: Buộc rời khỏi Ký túc xá",
                "Do bạn đã quá hạn 15 ngày không hoàn thành nghĩa vụ tài chính tiền phòng 01 năm, Ban Quản lý KTX quyết định chấm dứt hợp đồng và buộc bạn rời khỏi KTX. Chỗ ở của bạn đã bị thu hồi.",
                "ViPham", "/sinhvien/vi-pham");
    }
}
