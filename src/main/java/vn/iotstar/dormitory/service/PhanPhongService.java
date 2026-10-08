package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.entity.DangKyKTX;
import vn.iotstar.dormitory.entity.HopDong;
import vn.iotstar.dormitory.entity.PhanPhong;
import vn.iotstar.dormitory.entity.Phong;
import vn.iotstar.dormitory.entity.SinhVien;
import vn.iotstar.dormitory.repository.DangKyKTXRepository;
import vn.iotstar.dormitory.repository.PhanPhongRepository;
import vn.iotstar.dormitory.repository.PhongRepository;
import vn.iotstar.dormitory.repository.SinhVienRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PhanPhongService {

    @Autowired
    private PhanPhongRepository phanPhongRepository;
    
    @Autowired
    private DangKyKTXRepository dangKyKTXRepository;
    
    @Autowired
    private PhongRepository phongRepository;
    
    @Autowired
    private SinhVienRepository sinhVienRepository;
    
    @Autowired
    private ThongBaoService thongBaoService;

    @Autowired
    private vn.iotstar.dormitory.repository.HopDongRepository hopDongRepository;

    public List<PhanPhong> findAll() {
        return phanPhongRepository.findAll();
    }
    
    public Optional<PhanPhong> findBySinhVienId(String maSV) {
        if (maSV == null || maSV.trim().isEmpty()) {
            return Optional.empty();
        }
        String cleanMaSV = maSV.trim();

        // 1. Tìm tất cả hợp đồng của sinh viên này
        List<HopDong> svContracts = new java.util.ArrayList<>();
        if (hopDongRepository != null) {
            svContracts = hopDongRepository.findAll().stream()
                    .filter(hd -> hd.getPhanPhong() != null 
                               && hd.getPhanPhong().getSinhVien() != null 
                               && cleanMaSV.equalsIgnoreCase(hd.getPhanPhong().getSinhVien().getMaSV().trim()))
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

        // 1.1. Ưu tiên Hợp đồng có trạng thái "Còn hạn"
        List<HopDong> activeContracts = svContracts.stream()
                .filter(hd -> "Còn hạn".equalsIgnoreCase(hd.getTrangThai() != null ? hd.getTrangThai().trim() : ""))
                .toList();

        if (!activeContracts.isEmpty()) {
            HopDong latestActiveContract = activeContracts.get(0);
            PhanPhong contractPp = latestActiveContract.getPhanPhong();
            if (contractPp != null) {
                if (!"Đang ở".equals(contractPp.getTrangThai())) {
                    contractPp.setTrangThai("Đang ở");
                    phanPhongRepository.save(contractPp);
                }
                // Tự động kết thúc các phân phòng cũ khác của sinh viên
                List<PhanPhong> allPps = phanPhongRepository.findBySinhVien_MaSV(cleanMaSV);
                for (PhanPhong otherPp : allPps) {
                    if (!otherPp.getMaPhanPhong().equals(contractPp.getMaPhanPhong()) && "Đang ở".equals(otherPp.getTrangThai())) {
                        otherPp.setTrangThai("Đã kết thúc");
                        phanPhongRepository.save(otherPp);
                    }
                }
                // Tự động hết hạn các hợp đồng cũ khác
                for (int i = 1; i < activeContracts.size(); i++) {
                    HopDong oldHd = activeContracts.get(i);
                    oldHd.setTrangThai("Đã hết hạn");
                    hopDongRepository.save(oldHd);
                }
                return Optional.of(contractPp);
            }
        }

        // 1.2. Nếu sinh viên có hợp đồng nhưng chưa có cái nào ghi "Còn hạn":
        // Tự động kích hoạt hợp đồng mới nhất (ưu tiên hợp đồng mới đổi sang)
        if (!svContracts.isEmpty()) {
            HopDong latestContract = svContracts.get(0);
            PhanPhong contractPp = latestContract.getPhanPhong();
            if (contractPp != null) {
                latestContract.setTrangThai("Còn hạn");
                hopDongRepository.save(latestContract);

                contractPp.setTrangThai("Đang ở");
                phanPhongRepository.save(contractPp);

                // Tự động kết thúc các phân phòng cũ khác
                List<PhanPhong> allPps = phanPhongRepository.findBySinhVien_MaSV(cleanMaSV);
                for (PhanPhong otherPp : allPps) {
                    if (!otherPp.getMaPhanPhong().equals(contractPp.getMaPhanPhong()) && "Đang ở".equals(otherPp.getTrangThai())) {
                        otherPp.setTrangThai("Đã kết thúc");
                        phanPhongRepository.save(otherPp);
                    }
                }
                return Optional.of(contractPp);
            }
        }

        // 2. Kiểm tra trực tiếp bảng Phân phòng của sinh viên
        List<PhanPhong> svPps = phanPhongRepository.findBySinhVien_MaSV(cleanMaSV).stream()
                .sorted((a, b) -> {
                    LocalDate d1 = a.getNgayBatDau();
                    LocalDate d2 = b.getNgayBatDau();
                    if (d1 == null && d2 == null) return 0;
                    if (d1 == null) return 1;
                    if (d2 == null) return -1;
                    return d2.compareTo(d1);
                })
                .toList();

        if (!svPps.isEmpty()) {
            // Ưu tiên phân phòng đang ghi "Đang ở"
            Optional<PhanPhong> activePpOpt = svPps.stream()
                    .filter(pp -> "Đang ở".equalsIgnoreCase(pp.getTrangThai() != null ? pp.getTrangThai().trim() : ""))
                    .findFirst();
            if (activePpOpt.isPresent()) {
                PhanPhong current = activePpOpt.get();
                for (PhanPhong other : svPps) {
                    if (!other.getMaPhanPhong().equals(current.getMaPhanPhong()) && "Đang ở".equals(other.getTrangThai())) {
                        other.setTrangThai("Đã kết thúc");
                        phanPhongRepository.save(other);
                    }
                }
                return Optional.of(current);
            }

            // Nếu không có phân phòng nào ghi "Đang ở", tự động lấy phân phòng mới nhất và kích hoạt lại
            PhanPhong latestPp = svPps.get(0);
            latestPp.setTrangThai("Đang ở");
            phanPhongRepository.save(latestPp);
            return Optional.of(latestPp);
        }

        // 3. Thực sự chưa từng được phân phòng
        return Optional.empty();
    }

    public void assignRoom(String maDangKy, String maPhong, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        Optional<DangKyKTX> dkOpt = dangKyKTXRepository.findById(maDangKy);
        Optional<Phong> pOpt = phongRepository.findById(maPhong);
        
        if (dkOpt.isPresent() && pOpt.isPresent()) {
            DangKyKTX dk = dkOpt.get();
            Phong p = pOpt.get();
            
            // Validate Giới Tính (Nam/Nữ) theo quy định phân tầng (Tầng 1-2: Nữ | Tầng 3-4: Nam)
            String gioiTinhSV = dk.getSinhVien() != null ? dk.getSinhVien().getGioiTinh() : dk.getGioiTinh();
            String gioiTinhPhong = p.getGioiTinh();

            if ("Nam".equalsIgnoreCase(gioiTinhSV) && "Nữ".equalsIgnoreCase(gioiTinhPhong)) {
                throw new RuntimeException("Lỗi: Phòng " + p.getSoPhong() + " (Tầng " + p.getTang() + ") thuộc khu vực dành riêng cho sinh viên Nữ (Tầng 1 & 2). Không thể xếp sinh viên Nam.");
            }
            if ("Nữ".equalsIgnoreCase(gioiTinhSV) && "Nam".equalsIgnoreCase(gioiTinhPhong)) {
                throw new RuntimeException("Lỗi: Phòng " + p.getSoPhong() + " (Tầng " + p.getTang() + ") thuộc khu vực dành riêng cho sinh viên Nam (Tầng 3 & 4). Không thể xếp sinh viên Nữ.");
            }

            // Validate Giới tính cùng phòng (áp dụng cho mọi phòng)
            List<PhanPhong> dsDangO = phanPhongRepository.findByPhong_MaPhongAndTrangThai(maPhong, "Đang ở");
            if (!dsDangO.isEmpty()) {
                String gioiTinhHienTai = dsDangO.get(0).getSinhVien() != null ? dsDangO.get(0).getSinhVien().getGioiTinh() : null;
                if (gioiTinhHienTai != null && !gioiTinhHienTai.equalsIgnoreCase(gioiTinhSV)) {
                    throw new RuntimeException("Lỗi: Phòng " + p.getSoPhong() + " hiện đang có sinh viên " + gioiTinhHienTai + " ở. Không thể xếp sinh viên " + gioiTinhSV + " vào chung phòng.");
                }
            }
            
            // Validate Số lượng người
            long soNguoiHienTai = phanPhongRepository.countByPhong_MaPhongAndTrangThai(maPhong, "Đang ở");
            int soNguoiToiDa = p.getLoaiPhong() != null ? p.getLoaiPhong().getSoNguoiToiDa() : 0;
            
            if (soNguoiHienTai >= soNguoiToiDa) {
                throw new RuntimeException("Lỗi: Phòng đã đủ số lượng người (" + soNguoiToiDa + "/" + soNguoiToiDa + ").");
            }

            // Kết thúc phân phòng cũ và hợp đồng cũ nếu sinh viên đang có phòng
            if (dk.getSinhVien() != null && dk.getSinhVien().getMaSV() != null) {
                String maSV = dk.getSinhVien().getMaSV();
                List<PhanPhong> oldPhanPhongs = phanPhongRepository.findBySinhVien_MaSV(maSV);
                for (PhanPhong oldPp : oldPhanPhongs) {
                    if ("Đang ở".equals(oldPp.getTrangThai())) {
                        oldPp.setTrangThai("Đã kết thúc");
                        phanPhongRepository.save(oldPp);
                    }
                }
                if (hopDongRepository != null) {
                    List<vn.iotstar.dormitory.entity.HopDong> oldHopDongs = hopDongRepository.findByPhanPhong_SinhVien_MaSV(maSV);
                    for (vn.iotstar.dormitory.entity.HopDong oldHd : oldHopDongs) {
                        if ("Còn hạn".equals(oldHd.getTrangThai())) {
                            oldHd.setTrangThai("Đã hết hạn");
                            hopDongRepository.save(oldHd);
                        }
                    }
                }
            }
            
            PhanPhong pp = new PhanPhong();
            pp.setMaPhanPhong(UUID.randomUUID().toString());
            pp.setSinhVien(dk.getSinhVien());
            pp.setPhong(p);
            pp.setDangKyKTX(dk);
            pp.setNgayBatDau(ngayBatDau);
            pp.setNgayKetThuc(ngayKetThuc);
            pp.setTrangThai("Đang ở");
            
            phanPhongRepository.save(pp);
            
            // Cập nhật trạng thái
            dk.setTrangThai("Đã duyệt");
            dangKyKTXRepository.save(dk);
            
            // Kiểm tra xem sau khi thêm, phòng đã đầy chưa
            if (soNguoiHienTai + 1 >= soNguoiToiDa) {
                p.setTrangThai("Đã đầy");
            } else {
                p.setTrangThai("Đang ở");
            }
            phongRepository.save(p);
            
            // Notify SV
            thongBaoService.taoThongBao(dk.getSinhVien().getMaSV(), "Đơn đăng ký phòng được duyệt", "Bạn đã được phân vào phòng " + p.getSoPhong() + ".", "DangKy", "/sinhvien/phong");
        } else {
            throw new RuntimeException("Lỗi: Không tìm thấy Đăng ký hoặc Phòng.");
        }
    }
    public void assignRoomQuickly(String maSV, String maPhong, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        Optional<SinhVien> svOpt = sinhVienRepository.findById(maSV);
        Optional<Phong> pOpt = phongRepository.findById(maPhong);
        
        if (svOpt.isPresent() && pOpt.isPresent()) {
            SinhVien sv = svOpt.get();
            Phong p = pOpt.get();
            
            // Validate Giới Tính (Nam/Nữ) theo quy định phân tầng (Tầng 1-2: Nữ | Tầng 3-4: Nam)
            String gioiTinhSV = sv.getGioiTinh();
            String gioiTinhPhong = p.getGioiTinh();

            if ("Nam".equalsIgnoreCase(gioiTinhSV) && "Nữ".equalsIgnoreCase(gioiTinhPhong)) {
                throw new RuntimeException("Lỗi: Phòng " + p.getSoPhong() + " (Tầng " + p.getTang() + ") thuộc khu vực dành riêng cho sinh viên Nữ (Tầng 1 & 2). Không thể xếp sinh viên Nam.");
            }
            if ("Nữ".equalsIgnoreCase(gioiTinhSV) && "Nam".equalsIgnoreCase(gioiTinhPhong)) {
                throw new RuntimeException("Lỗi: Phòng " + p.getSoPhong() + " (Tầng " + p.getTang() + ") thuộc khu vực dành riêng cho sinh viên Nam (Tầng 3 & 4). Không thể xếp sinh viên Nữ.");
            }

            // Validate Giới tính cùng phòng (áp dụng cho mọi phòng)
            List<PhanPhong> dsDangOQuick = phanPhongRepository.findByPhong_MaPhongAndTrangThai(maPhong, "Đang ở");
            if (!dsDangOQuick.isEmpty()) {
                String gioiTinhHienTai = dsDangOQuick.get(0).getSinhVien() != null ? dsDangOQuick.get(0).getSinhVien().getGioiTinh() : null;
                if (gioiTinhHienTai != null && !gioiTinhHienTai.equalsIgnoreCase(gioiTinhSV)) {
                    throw new RuntimeException("Lỗi: Phòng " + p.getSoPhong() + " hiện đang có sinh viên " + gioiTinhHienTai + " ở. Không thể xếp sinh viên " + gioiTinhSV + " vào chung phòng.");
                }
            }
            
            // Validate Số lượng người
            long soNguoiHienTai = phanPhongRepository.countByPhong_MaPhongAndTrangThai(maPhong, "Đang ở");
            int soNguoiToiDa = p.getLoaiPhong() != null ? p.getLoaiPhong().getSoNguoiToiDa() : 0;
            
            if (soNguoiHienTai >= soNguoiToiDa) {
                throw new RuntimeException("Lỗi: Phòng đã đủ số lượng người (" + soNguoiToiDa + "/" + soNguoiToiDa + ").");
            }

            // Kết thúc phân phòng cũ và hợp đồng cũ nếu sinh viên đang có phòng
            List<PhanPhong> oldPhanPhongs = phanPhongRepository.findBySinhVien_MaSV(maSV);
            for (PhanPhong oldPp : oldPhanPhongs) {
                if ("Đang ở".equals(oldPp.getTrangThai())) {
                    oldPp.setTrangThai("Đã kết thúc");
                    phanPhongRepository.save(oldPp);
                }
            }
            if (hopDongRepository != null) {
                List<vn.iotstar.dormitory.entity.HopDong> oldHopDongs = hopDongRepository.findByPhanPhong_SinhVien_MaSV(maSV);
                for (vn.iotstar.dormitory.entity.HopDong oldHd : oldHopDongs) {
                    if ("Còn hạn".equals(oldHd.getTrangThai())) {
                        oldHd.setTrangThai("Đã hết hạn");
                        hopDongRepository.save(oldHd);
                    }
                }
            }
            
            PhanPhong pp = new PhanPhong();
            pp.setMaPhanPhong(UUID.randomUUID().toString());
            pp.setSinhVien(sv);
            pp.setPhong(p);
            // Không có DangKyKTX
            pp.setNgayBatDau(ngayBatDau);
            pp.setNgayKetThuc(ngayKetThuc);
            pp.setTrangThai("Đang ở");
            
            phanPhongRepository.save(pp);
            
            // Kiểm tra xem sau khi thêm, phòng đã đầy chưa
            if (soNguoiHienTai + 1 >= soNguoiToiDa) {
                p.setTrangThai("Đã đầy");
            } else {
                p.setTrangThai("Đang ở");
            }
            phongRepository.save(p);
            
            // Notify SV
            thongBaoService.taoThongBao(sv.getMaSV(), "Phân phòng thành công", "Bạn đã được xếp trực tiếp vào phòng " + p.getSoPhong() + ".", "DangKy", "/sinhvien/phong");
        } else {
            throw new RuntimeException("Lỗi: Không tìm thấy Sinh viên hoặc Phòng.");
        }
    }
}
