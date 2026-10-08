package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.dto.DangKyKTXDTO;
import vn.iotstar.dormitory.dto.NopDonOnlineDTO;
import vn.iotstar.dormitory.entity.*;
import vn.iotstar.dormitory.repository.*;

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

    @Autowired
    private ViPhamService viPhamService;

    @Autowired
    private ThongBaoService thongBaoService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private PhongRepository phongRepository;

    @Autowired
    private PhanPhongRepository phanPhongRepository;

    @Autowired
    private HopDongRepository hopDongRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public List<DangKyKTX> search(String keyword, String trangThai, String maLoaiPhong) {
        return dangKyKTXRepository.findAll().stream()
                .filter(dk -> {
                    boolean matchKeyword = true;
                    if (keyword != null && !keyword.trim().isEmpty()) {
                        String kw = keyword.trim().toLowerCase();
                        String name = dk.getHoTen() != null ? dk.getHoTen().toLowerCase() : "";
                        String cccd = dk.getCccd() != null ? dk.getCccd().toLowerCase() : "";
                        String email = dk.getEmail() != null ? dk.getEmail().toLowerCase() : "";
                        String sdt = dk.getSdt() != null ? dk.getSdt().toLowerCase() : "";
                        String truong = dk.getTruongDaiHoc() != null ? dk.getTruongDaiHoc().toLowerCase() : "";
                        String maSV = (dk.getSinhVien() != null) ? dk.getSinhVien().getMaSV().toLowerCase() : "";
                        matchKeyword = name.contains(kw) || cccd.contains(kw) || email.contains(kw) 
                                || sdt.contains(kw) || truong.contains(kw) || maSV.contains(kw);
                    }

                    boolean matchTrangThai = true;
                    if (trangThai != null && !trangThai.trim().isEmpty() && !"ALL".equalsIgnoreCase(trangThai)) {
                        matchTrangThai = trangThai.equalsIgnoreCase(dk.getTrangThai());
                    }

                    boolean matchLoaiPhong = true;
                    if (maLoaiPhong != null && !maLoaiPhong.trim().isEmpty() && !"ALL".equalsIgnoreCase(maLoaiPhong)) {
                        matchLoaiPhong = dk.getLoaiPhong() != null && maLoaiPhong.equals(dk.getLoaiPhong().getMaLoaiPhong());
                    }

                    return matchKeyword && matchTrangThai && matchLoaiPhong;
                })
                .sorted((a, b) -> {
                    if (b.getNgayDangKy() == null) return -1;
                    if (a.getNgayDangKy() == null) return 1;
                    return b.getNgayDangKy().compareTo(a.getNgayDangKy());
                })
                .toList();
    }

    public DangKyKTX submitOnlineRegistration(NopDonOnlineDTO dto) {
        DangKyKTX dk = new DangKyKTX();
        String generatedMaDK = "DK" + (System.currentTimeMillis() % 10000000);
        dk.setMaDangKy(generatedMaDK);
        dk.setNgayDangKy(LocalDate.now());
        dk.setTrangThai("Chờ duyệt");

        dk.setHoTen(dto.getHoTen());
        dk.setNgaySinh(dto.getNgaySinh());
        dk.setGioiTinh(dto.getGioiTinh());
        dk.setCccd(dto.getCccd());
        dk.setSdt(dto.getSdt());
        dk.setEmail(dto.getEmail());
        dk.setQueQuan(dto.getQueQuan());
        dk.setTruongDaiHoc(dto.getTruongDaiHoc());
        dk.setKhoa(dto.getKhoa());
        dk.setNamHoc(dto.getNamHoc());
        dk.setDienUuTien(dto.getDienUuTien());
        dk.setGhiChu(dto.getGhiChu());

        // Upload hình ảnh qua Cloudinary nếu có đính kèm
        if (dto.getAnhTheSinhVienFile() != null && !dto.getAnhTheSinhVienFile().isEmpty()) {
            String url = cloudinaryService.uploadImage(dto.getAnhTheSinhVienFile(), "the_sv");
            dk.setAnhTheSinhVien(url);
        }
        if (dto.getAnhCccdFile() != null && !dto.getAnhCccdFile().isEmpty()) {
            String url = cloudinaryService.uploadImage(dto.getAnhCccdFile(), "cccd");
            dk.setAnhCccd(url);
        }
        if (dto.getAnhMinhChungUuTienFile() != null && !dto.getAnhMinhChungUuTienFile().isEmpty()) {
            String url = cloudinaryService.uploadImage(dto.getAnhMinhChungUuTienFile(), "uuyen_tin");
            dk.setAnhMinhChungUuTien(url);
        }

        // Gán Loại phòng
        if (dto.getMaLoaiPhong() != null) {
            loaiPhongRepository.findById(dto.getMaLoaiPhong()).ifPresent(dk::setLoaiPhong);
        }

        // Kiểm tra xem sinh viên này đã có tài khoản sẵn trong hệ thống chưa
        Optional<SinhVien> svOpt = sinhVienRepository.findByEmail(dto.getEmail());
        svOpt.ifPresent(dk::setSinhVien);

        DangKyKTX saved = dangKyKTXRepository.save(dk);

        // Gửi email tiếp nhận hồ sơ
        emailService.sendTiepNhanHoSoEmail(dto.getEmail(), dto.getHoTen(), saved.getMaDangKy());

        return saved;
    }

    public DangKyKTX createRegistration(DangKyKTXDTO dto, String maSV) {
        long violations = viPhamService.countByMaSV(maSV);
        if (violations >= 3) {
            throw new RuntimeException("Bạn không thể đăng ký KTX vì đã có " + violations + " vi phạm kỷ luật.");
        }
        
        DangKyKTX dk = new DangKyKTX();
        dk.setMaDangKy("DK" + (System.currentTimeMillis() % 10000000));
        dk.setNgayDangKy(LocalDate.now());
        dk.setTrangThai("Chờ duyệt");
        dk.setGhiChu(dto.getGhiChu());
        
        Optional<SinhVien> svOpt = sinhVienRepository.findById(maSV);
        if (svOpt.isPresent()) {
            SinhVien sv = svOpt.get();
            dk.setSinhVien(sv);
            dk.setHoTen(sv.getHoTen());
            dk.setEmail(sv.getEmail());
            dk.setSdt(sv.getSdt());
            dk.setGioiTinh(sv.getGioiTinh());
            dk.setNgaySinh(sv.getNgaySinh());
            dk.setCccd(sv.getCccd());
            dk.setQueQuan(sv.getQueQuan());
            dk.setTruongDaiHoc(sv.getTruongDaiHoc());
            dk.setKhoa(sv.getKhoa());
            dk.setNamHoc(sv.getNamHoc());
            dk.setDienUuTien(sv.getDienUuTien());
        }

        loaiPhongRepository.findById(dto.getMaLoaiPhong()).ifPresent(dk::setLoaiPhong);
        
        DangKyKTX saved = dangKyKTXRepository.save(dk);
        if (dk.getEmail() != null && !dk.getEmail().isEmpty()) {
            emailService.sendTiepNhanHoSoEmail(dk.getEmail(), dk.getHoTen(), saved.getMaDangKy());
        }
        return saved;
    }

    @org.springframework.transaction.annotation.Transactional
    public void approveRegistration(String maDangKy, String maPhong, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        DangKyKTX dk = dangKyKTXRepository.findById(maDangKy)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn đăng ký: " + maDangKy));

        Phong p = phongRepository.findById(maPhong)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng: " + maPhong));

        // 1. Kiểm tra giới tính theo quy định phân tầng (Tầng 1-2: Nữ | Tầng 3-4: Nam)
        String gioiTinh = dk.getGioiTinh();
        if (gioiTinh == null && dk.getSinhVien() != null) {
            gioiTinh = dk.getSinhVien().getGioiTinh();
        }
        String gioiTinhPhong = p.getGioiTinh();
        if ("Nam".equalsIgnoreCase(gioiTinh) && "Nữ".equalsIgnoreCase(gioiTinhPhong)) {
            throw new RuntimeException("Lỗi: Phòng " + p.getSoPhong() + " (Tầng " + p.getTang() + ") thuộc khu vực dành riêng cho sinh viên Nữ (Tầng 1 & 2). Không thể xếp sinh viên Nam.");
        }
        if ("Nữ".equalsIgnoreCase(gioiTinh) && "Nam".equalsIgnoreCase(gioiTinhPhong)) {
            throw new RuntimeException("Lỗi: Phòng " + p.getSoPhong() + " (Tầng " + p.getTang() + ") thuộc khu vực dành riêng cho sinh viên Nam (Tầng 3 & 4). Không thể xếp sinh viên Nữ.");
        }

        // Kiểm tra cùng giới tính với các sinh viên đang ở trong phòng (áp dụng cho mọi phòng)
        List<PhanPhong> dsDangO = phanPhongRepository.findByPhong_MaPhongAndTrangThai(maPhong, "Đang ở");
        if (!dsDangO.isEmpty()) {
            String gioiTinhHienTai = dsDangO.get(0).getSinhVien() != null ? dsDangO.get(0).getSinhVien().getGioiTinh() : null;
            if (gioiTinhHienTai != null && gioiTinh != null && !gioiTinhHienTai.equalsIgnoreCase(gioiTinh)) {
                throw new RuntimeException("Lỗi: Phòng " + p.getSoPhong() + " hiện đang có sinh viên " + gioiTinhHienTai + " ở. Không thể xếp sinh viên " + gioiTinh + " vào chung phòng.");
            }
        }

        // 2. Kiểm tra sức chứa
        long soNguoiHienTai = phanPhongRepository.countByPhong_MaPhongAndTrangThai(maPhong, "Đang ở");
        int soNguoiToiDa = (p.getLoaiPhong() != null && p.getLoaiPhong().getSoNguoiToiDa() != null) ? p.getLoaiPhong().getSoNguoiToiDa() : 4;
        if (soNguoiHienTai >= soNguoiToiDa) {
            throw new RuntimeException("Lỗi: Phòng " + p.getSoPhong() + " đã đủ số lượng người (" + soNguoiToiDa + "/" + soNguoiToiDa + ").");
        }

        // 3. Khởi tạo tài khoản SinhVien nếu chưa có
        SinhVien sv = dk.getSinhVien();
        String plainPassword = "123456";
        boolean isNewStudent = false;

        if (sv == null) {
            // Thử tìm theo email hoặc cccd
            Optional<SinhVien> existingOpt = Optional.empty();
            if (dk.getEmail() != null && !dk.getEmail().trim().isEmpty()) {
                existingOpt = sinhVienRepository.findByEmail(dk.getEmail().trim());
            }
            if (existingOpt.isEmpty() && dk.getCccd() != null && !dk.getCccd().trim().isEmpty()) {
                existingOpt = sinhVienRepository.findAll().stream()
                        .filter(s -> dk.getCccd().trim().equalsIgnoreCase(s.getCccd()))
                        .findFirst();
            }

            if (existingOpt.isPresent()) {
                sv = existingOpt.get();
            } else {
                isNewStudent = true;
                sv = new SinhVien();
                sv.setMaSV(sinhVienService.generateNextMaSV());
                sv.setHoTen(dk.getHoTen());
                sv.setEmail(dk.getEmail());
                sv.setSdt(dk.getSdt());
                sv.setCccd(dk.getCccd());
                sv.setGioiTinh(dk.getGioiTinh());
                sv.setNgaySinh(dk.getNgaySinh());
                sv.setQueQuan(dk.getQueQuan());
                sv.setTruongDaiHoc(dk.getTruongDaiHoc());
                sv.setKhoa(dk.getKhoa());
                sv.setNamHoc(dk.getNamHoc());
                sv.setDienUuTien(dk.getDienUuTien());
                sv.setAnhTheSinhVien(dk.getAnhTheSinhVien());
                sv.setMatKhau(passwordEncoder.encode(plainPassword));
                sv.setTrangThai("Hoạt động");
                sv = sinhVienRepository.save(sv);
            }
            dk.setSinhVien(sv);
        }

        // 4. Xử lý các phân phòng và hợp đồng cũ của sinh viên nếu có
        if (sv.getMaSV() != null) {
            // Hết hạn tất cả hợp đồng cũ còn hiệu lực của sinh viên
            List<HopDong> oldHopDongs = hopDongRepository.findByPhanPhong_SinhVien_MaSV(sv.getMaSV());
            for (HopDong oldHd : oldHopDongs) {
                if ("Còn hạn".equals(oldHd.getTrangThai())) {
                    oldHd.setTrangThai("Đã hết hạn");
                    hopDongRepository.save(oldHd);
                }
            }

            // Kết thúc phân phòng cũ nếu sinh viên đang có phòng
            List<PhanPhong> oldPhanPhongs = phanPhongRepository.findBySinhVien_MaSV(sv.getMaSV());
            for (PhanPhong oldPp : oldPhanPhongs) {
                if ("Đang ở".equals(oldPp.getTrangThai())) {
                    oldPp.setTrangThai("Đã kết thúc");
                    phanPhongRepository.save(oldPp);

                    if (oldPp.getPhong() != null && !oldPp.getPhong().getMaPhong().equals(p.getMaPhong())) {
                        Phong oldP = oldPp.getPhong();
                        long cntOld = phanPhongRepository.countByPhong_MaPhongAndTrangThai(oldP.getMaPhong(), "Đang ở");
                        int maxOld = (oldP.getLoaiPhong() != null && oldP.getLoaiPhong().getSoNguoiToiDa() != null) 
                                ? oldP.getLoaiPhong().getSoNguoiToiDa() : 4;
                        if (cntOld == 0) {
                            oldP.setTrangThai("Còn trống");
                        } else if (cntOld < maxOld) {
                            oldP.setTrangThai("Đang ở");
                        } else {
                            oldP.setTrangThai("Đã đầy");
                        }
                        phongRepository.save(oldP);
                    }
                }
            }
        }

        // 5. Tạo bản ghi Phân phòng
        PhanPhong pp = new PhanPhong();
        pp.setMaPhanPhong(UUID.randomUUID().toString());
        pp.setSinhVien(sv);
        pp.setPhong(p);
        pp.setDangKyKTX(dk);
        pp.setNgayBatDau(ngayBatDau);
        pp.setNgayKetThuc(ngayKetThuc);
        pp.setTrangThai("Đang ở");
        phanPhongRepository.save(pp);

        // 6. Tạo Hợp đồng lưu trú
        HopDong hd = new HopDong();
        hd.setMaHopDong("HD" + (System.currentTimeMillis() % 10000000));
        hd.setPhanPhong(pp);
        hd.setNgayLap(LocalDate.now());
        hd.setNgayBatDau(ngayBatDau);
        hd.setNgayKetThuc(ngayKetThuc);
        hd.setGiaTien((p.getLoaiPhong() != null && p.getLoaiPhong().getDonGia() != null) ? p.getLoaiPhong().getDonGia() : 0.0);
        hd.setTrangThai("Còn hạn");
        hopDongRepository.save(hd);

        // 6. Cập nhật trạng thái đơn đăng ký & phòng
        dk.setTrangThai("Đã duyệt");
        dangKyKTXRepository.save(dk);

        if (soNguoiHienTai + 1 >= soNguoiToiDa) {
            p.setTrangThai("Đã đầy");
        } else {
            p.setTrangThai("Đang ở");
        }
        phongRepository.save(p);

        // 7. Gửi thông báo trong hệ thống
        thongBaoService.taoThongBao(sv.getMaSV(), "Đơn đăng ký phòng được duyệt",
                "Bạn đã được phân vào phòng " + p.getSoPhong() + " (" + (p.getKhu() != null ? p.getKhu().getTenKhu() : "") + ").",
                "DangKy", "/sinhvien/phong");

        // 8. TỰ ĐỘNG GỬI EMAIL CHÚC MỪNG TRÚNG TUYỂN
        if (dk.getEmail() != null && !dk.getEmail().isEmpty()) {
            Double giaPhong = p.getLoaiPhong() != null ? p.getLoaiPhong().getGiaPhong() : null;
            String tenLoaiPhong = p.getLoaiPhong() != null ? p.getLoaiPhong().getTenLoaiPhong() : "Tiêu chuẩn";
            String tenKhuStr = p.getKhu() != null ? p.getKhu().getTenKhu() : "";
            try {
                emailService.sendTrungTuyenEmail(
                        dk.getEmail(),
                        (dk.getHoTen() != null ? dk.getHoTen() : sv.getHoTen()),
                        sv.getMaSV(),
                        isNewStudent ? plainPassword : "Mật khẩu hiện tại của bạn",
                        p.getSoPhong(),
                        tenKhuStr,
                        tenLoaiPhong,
                        giaPhong,
                        ngayBatDau
                );
            } catch (Exception ex) {
                // Log and ignore to prevent transaction rollback
            }
        }
    }

    public void rejectRegistration(String maDangKy, String lyDoTuChoi) {
        DangKyKTX dk = dangKyKTXRepository.findById(maDangKy)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn đăng ký: " + maDangKy));

        dk.setTrangThai("Từ chối");
        dk.setLyDoTuChoi(lyDoTuChoi);
        dangKyKTXRepository.save(dk);

        if (dk.getSinhVien() != null) {
            thongBaoService.taoThongBao(dk.getSinhVien().getMaSV(), "Đơn đăng ký phòng bị từ chối",
                    "Rất tiếc, đơn đăng ký của bạn không được chấp thuận. Lý do: " + lyDoTuChoi,
                    "DangKy", "/sinhvien/dang-ky");
        }

        // TỰ ĐỘNG GỬI EMAIL TỪ CHỐI
        if (dk.getEmail() != null && !dk.getEmail().isEmpty()) {
            emailService.sendTuChoiEmail(dk.getEmail(), dk.getHoTen(), lyDoTuChoi);
        }
    }

    public void updateStatus(String maDangKy, String status) {
        dangKyKTXRepository.findById(maDangKy).ifPresent(dk -> {
            dk.setTrangThai(status);
            dangKyKTXRepository.save(dk);
            
            if ("Từ chối".equals(status)) {
                if (dk.getSinhVien() != null) {
                    thongBaoService.taoThongBao(dk.getSinhVien().getMaSV(), "Đơn đăng ký phòng bị từ chối",
                            "Rất tiếc, đơn đăng ký của bạn không được chấp thuận.", "DangKy", "/sinhvien/dang-ky");
                }
                if (dk.getEmail() != null && !dk.getEmail().isEmpty()) {
                    emailService.sendTuChoiEmail(dk.getEmail(), dk.getHoTen(), "Chưa đủ điều kiện xét duyệt đợt này.");
                }
            }
        });
    }
}
