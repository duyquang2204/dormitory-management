package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.dto.YeuCauSuaChuaDTO;
import vn.iotstar.dormitory.entity.Phong;
import vn.iotstar.dormitory.entity.SinhVien;
import vn.iotstar.dormitory.entity.YeuCauSuaChua;
import vn.iotstar.dormitory.repository.PhongRepository;
import vn.iotstar.dormitory.repository.SinhVienRepository;
import vn.iotstar.dormitory.repository.YeuCauSuaChuaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class YeuCauSuaChuaService {

    @Autowired
    private YeuCauSuaChuaRepository yeuCauRepository;
    
    @Autowired
    private PhongRepository phongRepository;
    
    @Autowired
    private SinhVienRepository sinhVienRepository;
    
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ThongBaoService thongBaoService;

    public List<YeuCauSuaChua> findAll() {
        return yeuCauRepository.findAll();
    }
    
    public List<YeuCauSuaChua> findBySinhVienId(String maSV) {
        return yeuCauRepository.findAll().stream()
                .filter(yc -> yc.getSinhVien() != null && yc.getSinhVien().getMaSV().equals(maSV))
                .toList();
    }
    
    public Optional<YeuCauSuaChua> findById(String id) {
        return yeuCauRepository.findById(id);
    }

    public void createYeuCau(YeuCauSuaChuaDTO dto, String maSV) {
        YeuCauSuaChua yc = new YeuCauSuaChua();
        yc.setMaYeuCau(UUID.randomUUID().toString());
        yc.setNgayGui(LocalDateTime.now());
        yc.setNoiDungSuCo(dto.getNoiDungSuCo());
        yc.setTrangThai("Chờ xử lý");
        
        phongRepository.findById(dto.getMaPhong()).ifPresent(yc::setPhong);
        sinhVienRepository.findById(maSV).ifPresent(yc::setSinhVien);
        
        if (dto.getHinhAnhFile() != null && !dto.getHinhAnhFile().isEmpty()) {
            String url = cloudinaryService.uploadImage(dto.getHinhAnhFile());
            if (url != null) {
                yc.setHinhAnh(url);
            }
        }
        
        yeuCauRepository.save(yc);
        
        // Notify Quản Sinh
        thongBaoService.taoThongBao("ROLE_QUAN_SINH", 
                "Yêu cầu sửa chữa mới", 
                "Có một yêu cầu sửa chữa mới từ phòng " + yc.getPhong().getSoPhong(), 
                "SuaChua", "/quansinh/sua-chua");
    }
}
