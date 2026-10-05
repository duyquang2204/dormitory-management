package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.entity.DangKyKTX;
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

    public List<PhanPhong> findAll() {
        return phanPhongRepository.findAll();
    }
    
    public Optional<PhanPhong> findBySinhVienId(String maSV) {
        return phanPhongRepository.findAll().stream()
                .filter(p -> p.getSinhVien() != null && p.getSinhVien().getMaSV().equals(maSV))
                .findFirst();
    }

    public void assignRoom(String maDangKy, String maPhong, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        Optional<DangKyKTX> dkOpt = dangKyKTXRepository.findById(maDangKy);
        Optional<Phong> pOpt = phongRepository.findById(maPhong);
        
        if (dkOpt.isPresent() && pOpt.isPresent()) {
            DangKyKTX dk = dkOpt.get();
            Phong p = pOpt.get();
            
            // Validate Giới Tính (Nam/Nữ) theo tên Khu
            String gioiTinhSV = dk.getSinhVien().getGioiTinh();
            String tenKhu = p.getKhu().getTenKhu().toLowerCase();
            if (tenKhu.contains("nam") && !"Nam".equalsIgnoreCase(gioiTinhSV)) {
                throw new RuntimeException("Lỗi: Khu Nam chỉ dành cho sinh viên Nam.");
            }
            if (tenKhu.contains("nữ") && !"Nữ".equalsIgnoreCase(gioiTinhSV)) {
                throw new RuntimeException("Lỗi: Khu Nữ chỉ dành cho sinh viên Nữ.");
            }
            
            // Validate Số lượng người
            long soNguoiHienTai = phanPhongRepository.countByPhong_MaPhongAndTrangThai(maPhong, "Đang ở");
            int soNguoiToiDa = p.getLoaiPhong() != null ? p.getLoaiPhong().getSoNguoiToiDa() : 0;
            
            if (soNguoiHienTai >= soNguoiToiDa) {
                throw new RuntimeException("Lỗi: Phòng đã đủ số lượng người (" + soNguoiToiDa + "/" + soNguoiToiDa + ").");
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
            
            // Validate Giới Tính (Nam/Nữ) theo tên Khu
            String gioiTinhSV = sv.getGioiTinh();
            String tenKhu = p.getKhu().getTenKhu().toLowerCase();
            if (tenKhu.contains("nam") && !"Nam".equalsIgnoreCase(gioiTinhSV)) {
                throw new RuntimeException("Lỗi: Khu Nam chỉ dành cho sinh viên Nam.");
            }
            if (tenKhu.contains("nữ") && !"Nữ".equalsIgnoreCase(gioiTinhSV)) {
                throw new RuntimeException("Lỗi: Khu Nữ chỉ dành cho sinh viên Nữ.");
            }
            
            // Validate Số lượng người
            long soNguoiHienTai = phanPhongRepository.countByPhong_MaPhongAndTrangThai(maPhong, "Đang ở");
            int soNguoiToiDa = p.getLoaiPhong() != null ? p.getLoaiPhong().getSoNguoiToiDa() : 0;
            
            if (soNguoiHienTai >= soNguoiToiDa) {
                throw new RuntimeException("Lỗi: Phòng đã đủ số lượng người (" + soNguoiToiDa + "/" + soNguoiToiDa + ").");
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
