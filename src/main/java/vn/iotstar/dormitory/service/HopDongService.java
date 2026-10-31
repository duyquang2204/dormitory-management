package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.dto.HopDongDTO;
import vn.iotstar.dormitory.entity.HopDong;
import vn.iotstar.dormitory.entity.PhanPhong;
import vn.iotstar.dormitory.entity.Phong;
import vn.iotstar.dormitory.entity.SinhVien;
import vn.iotstar.dormitory.repository.HopDongRepository;
import vn.iotstar.dormitory.repository.NguoiDungRepository;
import vn.iotstar.dormitory.repository.PhanPhongRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HopDongService {

    @Autowired
    private HopDongRepository hopDongRepository;
    
    @Autowired
    private PhanPhongRepository phanPhongRepository;
    
    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private vn.iotstar.dormitory.repository.PhongRepository phongRepository;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired(required = false)
    private ThongBaoService thongBaoService;

    @jakarta.annotation.PostConstruct
    public void cleanupMultipleActiveContracts() {
        try {
            List<HopDong> all = hopDongRepository.findAll();
            java.util.Map<String, List<HopDong>> svContracts = new java.util.HashMap<>();
            for (HopDong hd : all) {
                if (hd.getPhanPhong() != null && hd.getPhanPhong().getSinhVien() != null) {
                    String maSV = hd.getPhanPhong().getSinhVien().getMaSV();
                    svContracts.computeIfAbsent(maSV, k -> new java.util.ArrayList<>()).add(hd);
                }
            }
            for (List<HopDong> contracts : svContracts.values()) {
                contracts.sort((a, b) -> {
                    LocalDate d1 = a.getNgayBatDau() != null ? a.getNgayBatDau() : a.getNgayLap();
                    LocalDate d2 = b.getNgayBatDau() != null ? b.getNgayBatDau() : b.getNgayLap();
                    if (d1 == null && d2 == null) return 0;
                    if (d1 == null) return 1;
                    if (d2 == null) return -1;
                    int cmp = d2.compareTo(d1);
                    if (cmp != 0) return cmp;
                    if (a.getMaHopDong() != null && b.getMaHopDong() != null) {
                        return b.getMaHopDong().compareTo(a.getMaHopDong());
                    }
                    return 0;
                });
                boolean activeFound = false;
                for (HopDong hd : contracts) {
                    if ("Còn hạn".equals(hd.getTrangThai())) {
                        if (!activeFound) {
                            activeFound = true;
                            if (hd.getPhanPhong() != null) {
                                PhanPhong activePp = hd.getPhanPhong();
                                if (!"Đang ở".equals(activePp.getTrangThai())) {
                                    activePp.setTrangThai("Đang ở");
                                    phanPhongRepository.save(activePp);
                                }
                            }
                        } else {
                            hd.setTrangThai("Đã hết hạn");
                            hopDongRepository.save(hd);
                        }
                    }
                }
                if (!activeFound && !contracts.isEmpty()) {
                    HopDong latest = contracts.get(0);
                    if (!"Đã thanh lý".equals(latest.getTrangThai()) && !"Đã hết hạn".equals(latest.getTrangThai())) {
                        latest.setTrangThai("Còn hạn");
                        if (latest.getPhanPhong() != null) {
                            PhanPhong pp = latest.getPhanPhong();
                            pp.setTrangThai("Đang ở");
                            phanPhongRepository.save(pp);
                        }
                        hopDongRepository.save(latest);
                    }
                }
            }

            // Đảm bảo các phân phòng gắn với hợp đồng Đã thanh lý luôn có trạng thái Đã trả phòng
            for (HopDong hd : all) {
                if ("Đã thanh lý".equals(hd.getTrangThai()) && hd.getPhanPhong() != null) {
                    PhanPhong pp = hd.getPhanPhong();
                    if ("Đang ở".equals(pp.getTrangThai())) {
                        pp.setTrangThai("Đã trả phòng");
                        phanPhongRepository.save(pp);
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    public List<HopDong> findAll() {
        return hopDongRepository.findAll().stream()
                .sorted((a, b) -> {
                    LocalDate d1 = a.getNgayBatDau() != null ? a.getNgayBatDau() : a.getNgayLap();
                    LocalDate d2 = b.getNgayBatDau() != null ? b.getNgayBatDau() : b.getNgayLap();
                    if (d1 == null && d2 == null) return 0;
                    if (d1 == null) return 1;
                    if (d2 == null) return -1;
                    int cmp = d2.compareTo(d1);
                    if (cmp != 0) return cmp;
                    if (a.getMaHopDong() != null && b.getMaHopDong() != null) {
                        return b.getMaHopDong().compareTo(a.getMaHopDong());
                    }
                    return 0;
                })
                .toList();
    }
    
    public List<HopDong> findBySinhVienId(String maSV) {
        List<HopDong> list = hopDongRepository.findAll().stream()
                .filter(hd -> hd.getPhanPhong() != null 
                           && hd.getPhanPhong().getSinhVien() != null 
                           && hd.getPhanPhong().getSinhVien().getMaSV().equals(maSV))
                .sorted((a, b) -> {
                    LocalDate d1 = a.getNgayBatDau() != null ? a.getNgayBatDau() : a.getNgayLap();
                    LocalDate d2 = b.getNgayBatDau() != null ? b.getNgayBatDau() : b.getNgayLap();
                    if (d1 == null && d2 == null) return 0;
                    if (d1 == null) return 1;
                    if (d2 == null) return -1;
                    int cmp = d2.compareTo(d1);
                    if (cmp != 0) return cmp;
                    if (a.getMaHopDong() != null && b.getMaHopDong() != null) {
                        return b.getMaHopDong().compareTo(a.getMaHopDong());
                    }
                    return 0;
                })
                .toList();

        // Đảm bảo chỉ có tối đa 1 hợp đồng mới nhất là "Còn hạn", các hợp đồng cũ hơn lập tức chuyển sang "Đã hết hạn"
        boolean activeFound = false;
        for (HopDong hd : list) {
            if ("Còn hạn".equals(hd.getTrangThai())) {
                if (!activeFound) {
                    activeFound = true;
                } else {
                    hd.setTrangThai("Đã hết hạn");
                    hopDongRepository.save(hd);
                }
            }
        }
        if (!activeFound && !list.isEmpty()) {
            HopDong latest = list.get(0);
            if (!"Đã thanh lý".equals(latest.getTrangThai()) && !"Đã hết hạn".equals(latest.getTrangThai())) {
                latest.setTrangThai("Còn hạn");
                if (latest.getPhanPhong() != null) {
                    PhanPhong pp = latest.getPhanPhong();
                    pp.setTrangThai("Đang ở");
                    phanPhongRepository.save(pp);
                }
                hopDongRepository.save(latest);
            }
        }
        return list;
    }
    
    public Optional<HopDong> findById(String id) {
        return hopDongRepository.findById(id);
    }

    public void createHopDong(HopDongDTO dto, String maNhanVien) {
        Optional<PhanPhong> ppOpt = phanPhongRepository.findById(dto.getMaPhanPhong());
        if (ppOpt.isPresent()) {
            PhanPhong pp = ppOpt.get();
            SinhVien sv = pp.getSinhVien();
            if (sv != null && sv.getMaSV() != null) {
                // Tự động hết hạn tất cả các hợp đồng cũ còn hạn của sinh viên
                List<HopDong> oldContracts = hopDongRepository.findByPhanPhong_SinhVien_MaSV(sv.getMaSV());
                for (HopDong oldHd : oldContracts) {
                    if ("Còn hạn".equals(oldHd.getTrangThai())) {
                        oldHd.setTrangThai("Đã hết hạn");
                        hopDongRepository.save(oldHd);
                    }
                }
                // Đồng thời đóng các phân phòng cũ của sinh viên khác với phân phòng này
                List<PhanPhong> oldPps = phanPhongRepository.findBySinhVien_MaSV(sv.getMaSV());
                for (PhanPhong oldPp : oldPps) {
                    if (!oldPp.getMaPhanPhong().equals(pp.getMaPhanPhong()) && "Đang ở".equals(oldPp.getTrangThai())) {
                        oldPp.setTrangThai("Đã kết thúc");
                        phanPhongRepository.save(oldPp);
                    }
                }
            }

            // Đảm bảo phân phòng của hợp đồng mới luôn có trạng thái "Đang ở"
            if (!"Đang ở".equals(pp.getTrangThai())) {
                pp.setTrangThai("Đang ở");
                phanPhongRepository.save(pp);
            }

            HopDong hd = new HopDong();
            hd.setMaHopDong("HD" + (System.currentTimeMillis() % 10000000));
            hd.setPhanPhong(pp);
            hd.setNgayLap(LocalDate.now());
            hd.setNgayBatDau(dto.getNgayBatDau());
            hd.setNgayKetThuc(dto.getNgayKetThuc());
            hd.setGiaTien(dto.getGiaTien());
            hd.setTrangThai("Còn hạn");
            
            nguoiDungRepository.findById(maNhanVien).ifPresent(hd::setNhanVienLap);
            
            hopDongRepository.save(hd);

            // Sinh hóa đơn tiền phòng 1 năm tương ứng
            if (pp.getPhong() != null && pp.getSinhVien() != null) {
                double donGiaThang = (pp.getPhong().getLoaiPhong() != null && pp.getPhong().getLoaiPhong().getDonGia() != null) ? pp.getPhong().getLoaiPhong().getDonGia() : 0.0;
                long monthsDiff = (dto.getNgayBatDau() != null && dto.getNgayKetThuc() != null) ? java.time.temporal.ChronoUnit.MONTHS.between(dto.getNgayBatDau(), dto.getNgayKetThuc()) : 12;
                int soThang = (int) Math.max(1, monthsDiff);
                hoaDonService.createTienPhongInvoice(pp.getSinhVien(), pp.getPhong(), dto.getNgayBatDau(), donGiaThang, soThang);
            }
        }
    }

    public void guiYeuCau(String maHopDong, String loaiYeuCau) {
        Optional<HopDong> hdOpt = hopDongRepository.findById(maHopDong);
        if (hdOpt.isPresent()) {
            HopDong hd = hdOpt.get();
            if ("Còn hạn".equals(hd.getTrangThai())) {
                hd.setYeuCauCuaSV(loaiYeuCau);
                hopDongRepository.save(hd);
            }
        }
    }

    public void duyetGiaHan(String maHopDong, int soThang) {
        Optional<HopDong> hdOpt = hopDongRepository.findById(maHopDong);
        if (hdOpt.isPresent()) {
            HopDong hd = hdOpt.get();
            LocalDate ngayBatDauGiaHan = hd.getNgayKetThuc() != null ? hd.getNgayKetThuc() : LocalDate.now();
            if (hd.getNgayKetThuc() != null) {
                hd.setNgayKetThuc(hd.getNgayKetThuc().plusMonths(soThang));
            } else {
                hd.setNgayKetThuc(LocalDate.now().plusMonths(soThang));
            }
            hd.setTrangThai("Còn hạn");
            hd.setYeuCauCuaSV(null); // Clear the request after approval
            hopDongRepository.save(hd);

            // Sinh hóa đơn tiền phòng cho đợt gia hạn mới
            if (hd.getPhanPhong() != null && hd.getPhanPhong().getPhong() != null && hd.getPhanPhong().getSinhVien() != null) {
                Phong p = hd.getPhanPhong().getPhong();
                double donGiaThang = (p.getLoaiPhong() != null && p.getLoaiPhong().getDonGia() != null) ? p.getLoaiPhong().getDonGia() : 0.0;
                hoaDonService.createTienPhongInvoice(hd.getPhanPhong().getSinhVien(), p, ngayBatDauGiaHan, donGiaThang, soThang);
                
                if (thongBaoService != null) {
                    try {
                        thongBaoService.taoThongBao(
                            hd.getPhanPhong().getSinhVien().getMaSV(),
                            "Hợp đồng được gia hạn",
                            "Hợp đồng " + hd.getMaHopDong() + " đã được Ban quản lý duyệt gia hạn thêm " + soThang + " tháng.",
                            "HopDong",
                            "/sinhvien/phong"
                        );
                    } catch (Exception ignored) {}
                }
            }
        }
    }

    public void thanhLyHopDong(String maHopDong) {
        Optional<HopDong> hdOpt = hopDongRepository.findById(maHopDong);
        if (hdOpt.isPresent()) {
            HopDong hd = hdOpt.get();
            hd.setTrangThai("Đã thanh lý");
            hd.setYeuCauCuaSV(null);
            
            if (hd.getPhanPhong() != null) {
                PhanPhong pp = hd.getPhanPhong();
                pp.setTrangThai("Đã trả phòng");
                phanPhongRepository.save(pp);

                // Cập nhật tất cả phân phòng đang ở của sinh viên này sang Đã trả phòng
                if (pp.getSinhVien() != null) {
                    List<PhanPhong> svPps = phanPhongRepository.findBySinhVien_MaSV(pp.getSinhVien().getMaSV());
                    for (PhanPhong otherPp : svPps) {
                        if ("Đang ở".equals(otherPp.getTrangThai())) {
                            otherPp.setTrangThai("Đã trả phòng");
                            phanPhongRepository.save(otherPp);
                        }
                    }
                }

                // Cập nhật lại số lượng và trạng thái phòng (Còn trống / Đang ở / Đã đầy)
                if (pp.getPhong() != null && phongRepository != null) {
                    Phong p = pp.getPhong();
                    long cnt = phanPhongRepository.countByPhong_MaPhongAndTrangThai(p.getMaPhong(), "Đang ở");
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

                // Gửi thông báo đến sinh viên
                if (thongBaoService != null && pp.getSinhVien() != null) {
                    try {
                        thongBaoService.taoThongBao(
                            pp.getSinhVien().getMaSV(),
                            "Hợp đồng lưu trú đã được thanh lý",
                            "Hợp đồng " + hd.getMaHopDong() + " đã được Ban quản lý thanh lý hoàn tất và thu hồi chỗ ở KTX.",
                            "HopDong",
                            "/sinhvien/phong"
                        );
                    } catch (Exception ignored) {}
                }
            }
            hopDongRepository.save(hd);
        }
    }
}
