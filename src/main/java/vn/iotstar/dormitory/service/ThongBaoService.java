package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.dto.ThongBaoDTO;
import vn.iotstar.dormitory.entity.ThongBao;
import vn.iotstar.dormitory.repository.ThongBaoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ThongBaoService {

    @Autowired
    private ThongBaoRepository thongBaoRepository;

    @Autowired
    private vn.iotstar.dormitory.repository.DangKyKTXRepository dangKyRepo;
    
    @Autowired
    private vn.iotstar.dormitory.repository.YeuCauSuaChuaRepository suaChuaRepo;
    
    @Autowired
    private vn.iotstar.dormitory.repository.HoaDonRepository hoaDonRepo;
    
    @Autowired
    private vn.iotstar.dormitory.repository.HopDongRepository hopDongRepo;

    @Autowired
    private vn.iotstar.dormitory.repository.KetQuaSuaChuaRepository ketQuaRepo;

    @Autowired
    private vn.iotstar.dormitory.repository.ViPhamRepository viPhamRepo;
    
    public void notifySinhViensInRoom(String maPhong, String tieuDe, String noiDung, String loaiThongBao, String link) {
        hopDongRepo.findAll().stream()
            .filter(hd -> "Còn hạn".equals(hd.getTrangThai()) 
                       && hd.getPhanPhong() != null 
                       && hd.getPhanPhong().getPhong() != null 
                       && hd.getPhanPhong().getPhong().getMaPhong().equals(maPhong))
            .forEach(hd -> {
                taoThongBao(hd.getPhanPhong().getSinhVien().getMaSV(), tieuDe, noiDung, loaiThongBao, link);
            });
    }

    // Lấy thông báo cho người dùng
    public ThongBaoDTO getThongBaoForUser(String username, String role) {
        ThongBaoDTO dto = new ThongBaoDTO();
        
        List<String> nguoiNhans = new ArrayList<>();
        nguoiNhans.add(username);
        if (role != null) {
            nguoiNhans.add(role); // Vd: ROLE_QUAN_SINH
        }

        List<ThongBaoDTO.TodoItem> todos = new ArrayList<>();
        
        if ("ROLE_QUAN_SINH".equals(role)) { 
            long countDangKy = dangKyRepo.countByTrangThai("Chờ duyệt");
            if (countDangKy > 0) todos.add(new ThongBaoDTO.TodoItem(countDangKy + " đơn đăng ký phòng chờ duyệt", "/quansinh/dang-ky", "fas fa-file-signature text-primary"));
            
            long countSuaChua = suaChuaRepo.countByTrangThai("Chờ xử lý");
            if (countSuaChua > 0) todos.add(new ThongBaoDTO.TodoItem(countSuaChua + " yêu cầu sửa chữa chờ phân công", "/quansinh/sua-chua", "fas fa-tools text-warning"));
            
            long countHoaDon = hoaDonRepo.countByTrangThaiContaining("Chờ duyệt");
            if (countHoaDon > 0) todos.add(new ThongBaoDTO.TodoItem(countHoaDon + " biên lai hóa đơn chờ xác nhận", "/quansinh/hoa-don", "fas fa-file-invoice-dollar text-success"));
            
            long countHopDong = hopDongRepo.countByYeuCauCuaSVNotNull();
            if (countHopDong > 0) todos.add(new ThongBaoDTO.TodoItem(countHopDong + " yêu cầu gia hạn/trả phòng", "/quansinh/hop-dong", "fas fa-file-contract text-info"));

            long countViPhamChuaXuLy = viPhamRepo.countByTrangThaiXuLy("Chờ xử lý");
            if (countViPhamChuaXuLy > 0) todos.add(new ThongBaoDTO.TodoItem(countViPhamChuaXuLy + " biên bản vi phạm chờ xử lý", "/quansinh/vi-pham", "fas fa-gavel text-danger"));
        }
        
        // Của Nhân viên
        if ("ROLE_NHAN_VIEN".equals(role)) {
            long countViec = ketQuaRepo.findAll().stream()
                .filter(kq -> kq.getNgayHoanThanh() == null
                        && kq.getYeuCauSuaChua() != null
                        && "Đang xử lý".equals(kq.getYeuCauSuaChua().getTrangThai())
                        && kq.getDanhSachNhanVien() != null
                        && kq.getDanhSachNhanVien().stream().anyMatch(nv -> username.equals(nv.getTenDangNhap())))
                .count();
            if (countViec > 0) todos.add(new ThongBaoDTO.TodoItem(countViec + " công việc sửa chữa đang chờ bạn xử lý", "/nhanvien/sua-chua", "fas fa-tools text-warning"));
        }

        // Của Sinh viên
        if ("ROLE_SINH_VIEN".equals(role)) {
            long countChuaTra = hoaDonRepo.countBySinhVien_MaSVAndTrangThai(username, "Chưa thanh toán");
            if (countChuaTra > 0) todos.add(new ThongBaoDTO.TodoItem(countChuaTra + " hóa đơn chưa thanh toán", "/sinhvien/hoa-don", "fas fa-file-invoice-dollar text-danger"));

            long countVpChuaXuLy = viPhamRepo.countBySinhVien_MaSVAndTrangThaiXuLy(username, "Chờ xử lý");
            if (countVpChuaXuLy > 0) todos.add(new ThongBaoDTO.TodoItem(countVpChuaXuLy + " biên bản vi phạm đang chờ xử lý", "/sinhvien/vi-pham", "fas fa-exclamation-triangle text-danger"));

            // Tự động đồng bộ thông báo cho các hồ sơ vi phạm của sinh viên nếu chưa có thông báo
            List<vn.iotstar.dormitory.entity.ViPham> listVp = viPhamRepo.findBySinhVien_MaSV(username);
            for (vn.iotstar.dormitory.entity.ViPham vp : listVp) {
                String tieuDe = "Hồ sơ vi phạm kỷ luật: " + vp.getNoiDung();
                if (!thongBaoRepository.existsByNguoiNhanAndLoaiThongBaoAndTieuDe(username, "ViPham", tieuDe)) {
                    String hinhThuc = (vp.getHinhThucXuLy() != null && !vp.getHinhThucXuLy().isEmpty()) ? vp.getHinhThucXuLy() : "Chờ xử lý";
                    String noiDung = "Hình thức: " + hinhThuc + " - Trạng thái: " + vp.getTrangThaiXuLy();
                    taoThongBao(username, tieuDe, noiDung, "ViPham", "/sinhvien/vi-pham");
                }
            }
        }
        
        dto.setTodos(todos);

        // Lấy 5 cập nhật mới nhất
        List<ThongBao> updates = thongBaoRepository.findTop10ByNguoiNhans(nguoiNhans, org.springframework.data.domain.PageRequest.of(0, 5));
        dto.setUpdates(updates);

        dto.setUnreadUpdatesCount(thongBaoRepository.countUnread(nguoiNhans));

        return dto;
    }

    public void taoThongBao(String nguoiNhan, String tieuDe, String noiDung, String loaiThongBao, String link) {
        ThongBao tb = new ThongBao();
        tb.setMaThongBao(UUID.randomUUID().toString());
        tb.setNguoiNhan(nguoiNhan);
        tb.setTieuDe(tieuDe);
        tb.setNoiDung(noiDung);
        tb.setLoaiThongBao(loaiThongBao);
        tb.setLink(link);
        tb.setNgayTao(LocalDateTime.now());
        tb.setDaDoc(false);
        thongBaoRepository.save(tb);
    }

    public String markAsRead(String maThongBao) {
        java.util.Optional<ThongBao> tbOpt = thongBaoRepository.findById(maThongBao);
        if (tbOpt.isPresent()) {
            ThongBao tb = tbOpt.get();
            tb.setDaDoc(true);
            thongBaoRepository.save(tb);
            return tb.getLink() != null ? tb.getLink() : "/";
        }
        return "/";
    }
}
