package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.entity.KetQuaSuaChua;
import vn.iotstar.dormitory.entity.NguoiDung;
import vn.iotstar.dormitory.entity.YeuCauSuaChua;
import vn.iotstar.dormitory.repository.KetQuaSuaChuaRepository;
import vn.iotstar.dormitory.repository.NguoiDungRepository;
import vn.iotstar.dormitory.repository.YeuCauSuaChuaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class KetQuaSuaChuaService {

    @Autowired
    private KetQuaSuaChuaRepository ketQuaRepository;
    
    @Autowired
    private YeuCauSuaChuaRepository yeuCauRepository;
    
    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private ThongBaoService thongBaoService;

    public List<KetQuaSuaChua> findAll() {
        return ketQuaRepository.findAll();
    }
    
    public List<KetQuaSuaChua> findByNhanVienTenDangNhap(String tenDangNhap) {
        return ketQuaRepository.findAll().stream()
                .filter(kq -> kq.getDanhSachNhanVien() != null && kq.getDanhSachNhanVien().stream().anyMatch(nv -> nv.getTenDangNhap().equals(tenDangNhap)))
                .toList();
    }

    public void assignTask(String maYeuCau, List<String> maNhanVienList) {
        Optional<YeuCauSuaChua> ycOpt = yeuCauRepository.findById(maYeuCau);
        
        if (ycOpt.isPresent() && maNhanVienList != null && !maNhanVienList.isEmpty()) {
            YeuCauSuaChua yc = ycOpt.get();
            
            KetQuaSuaChua kq = ketQuaRepository.findAll().stream()
                    .filter(k -> k.getYeuCauSuaChua() != null && maYeuCau.equals(k.getYeuCauSuaChua().getMaYeuCau()))
                    .findFirst().orElse(null);
            if (kq == null) {
                kq = new KetQuaSuaChua();
                kq.setMaPhanCong(UUID.randomUUID().toString());
                kq.setYeuCauSuaChua(yc);
            } else {
                // Phân công lại: xóa kết quả báo cáo lần trước
                kq.setNgayHoanThanh(null);
                kq.setNoiDungXuLy(null);
                kq.setKetQua(null);
                kq.setHinhAnhMinhChung(null);
            }
            kq.setNgayTiepNhan(LocalDate.now());
            
            List<NguoiDung> nvs = nguoiDungRepository.findAllById(maNhanVienList);
            kq.setDanhSachNhanVien(nvs);

            String soPhong = yc.getPhong() != null ? yc.getPhong().getSoPhong() : "";
            for (NguoiDung nv : nvs) {
                thongBaoService.taoThongBao(nv.getTenDangNhap(), "Bạn được giao việc sửa chữa mới",
                        "Phòng " + soPhong + ": " + yc.getNoiDungSuCo(), "SuaChua", "/nhanvien/sua-chua");
            }
            if (yc.getSinhVien() != null) {
                thongBaoService.taoThongBao(yc.getSinhVien().getMaSV(), "Yêu cầu sửa chữa đã được tiếp nhận",
                        "Yêu cầu của bạn đã được giao cho nhân viên xử lý.", "SuaChua", "/sinhvien/sua-chua");
            }
            
            ketQuaRepository.save(kq);
            
            yc.setTrangThai("Đang xử lý");
            yeuCauRepository.save(yc);
        }
    }
    
    @Autowired
    private CloudinaryService cloudinaryService;

    public void updateResult(String maPhanCong, String noiDungXuLy, String ketQua, org.springframework.web.multipart.MultipartFile hinhAnhFile) {
        Optional<KetQuaSuaChua> kqOpt = ketQuaRepository.findById(maPhanCong);
        if (kqOpt.isPresent()) {
            KetQuaSuaChua kq = kqOpt.get();
            kq.setNgayHoanThanh(LocalDate.now());
            kq.setNoiDungXuLy(noiDungXuLy);
            kq.setKetQua(ketQua);
            
            if (hinhAnhFile != null && !hinhAnhFile.isEmpty()) {
                String prefix = "Phong" + (kq.getYeuCauSuaChua().getPhong() != null ? kq.getYeuCauSuaChua().getPhong().getSoPhong() : "Unknown");
                String url = cloudinaryService.uploadImage(hinhAnhFile, prefix);
                if (url != null) {
                    kq.setHinhAnhMinhChung(url);
                }
            }
            
            ketQuaRepository.save(kq);
            
            YeuCauSuaChua yc = kq.getYeuCauSuaChua();
            String soPhongHT = yc.getPhong() != null ? yc.getPhong().getSoPhong() : "";
            if (yc.getSinhVien() != null) {
                thongBaoService.taoThongBao(yc.getSinhVien().getMaSV(), "Yêu cầu sửa chữa đã hoàn thành",
                        "Yêu cầu sửa chữa phòng " + soPhongHT + " đã được xử lý xong.", "SuaChua", "/sinhvien/sua-chua");
            }
            thongBaoService.taoThongBao("ROLE_QUAN_SINH", "Nhân viên đã hoàn thành sửa chữa",
                    "Phòng " + soPhongHT + " đã sửa chữa xong.", "SuaChua", "/quansinh/sua-chua");
            yc.setTrangThai("Đã hoàn thành");
            yeuCauRepository.save(yc);
        }
    }

    public void cancelTask(String maYeuCau) {
        Optional<YeuCauSuaChua> ycOpt = yeuCauRepository.findById(maYeuCau);
        if (ycOpt.isPresent()) {
            YeuCauSuaChua yc = ycOpt.get();
            if (!"Đã hoàn thành".equals(yc.getTrangThai())) {
                yc.setTrangThai("Đã hủy");
                yeuCauRepository.save(yc);
                
                ketQuaRepository.findAll().stream()
                    .filter(kq -> kq.getYeuCauSuaChua().getMaYeuCau().equals(maYeuCau))
                    .findFirst()
                    .ifPresent(kq -> {
                        if (kq.getDanhSachNhanVien() != null) {
                            for (NguoiDung nv : kq.getDanhSachNhanVien()) {
                                thongBaoService.taoThongBao(nv.getTenDangNhap(), "Công việc sửa chữa đã bị hủy",
                                        "Một công việc sửa chữa được giao cho bạn đã bị gỡ bỏ.", "SuaChua", "/nhanvien/sua-chua");
                            }
                        }
                        ketQuaRepository.delete(kq);
                    });
                if (yc.getSinhVien() != null) {
                    thongBaoService.taoThongBao(yc.getSinhVien().getMaSV(), "Yêu cầu sửa chữa đã bị hủy",
                            "Yêu cầu sửa chữa của bạn đã bị hủy bởi quản sinh.", "SuaChua", "/sinhvien/sua-chua");
                }
            }
        }
    }
}
