package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.dto.HopDongDTO;
import vn.iotstar.dormitory.entity.HopDong;
import vn.iotstar.dormitory.entity.PhanPhong;
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

    public List<HopDong> findAll() {
        return hopDongRepository.findAll();
    }
    
    public List<HopDong> findBySinhVienId(String maSV) {
        return hopDongRepository.findAll().stream()
                .filter(hd -> hd.getPhanPhong() != null 
                           && hd.getPhanPhong().getSinhVien() != null 
                           && hd.getPhanPhong().getSinhVien().getMaSV().equals(maSV))
                .toList();
    }
    
    public Optional<HopDong> findById(String id) {
        return hopDongRepository.findById(id);
    }

    public void createHopDong(HopDongDTO dto, String maNhanVien) {
        Optional<PhanPhong> ppOpt = phanPhongRepository.findById(dto.getMaPhanPhong());
        if (ppOpt.isPresent()) {
            HopDong hd = new HopDong();
            hd.setMaHopDong(UUID.randomUUID().toString());
            hd.setPhanPhong(ppOpt.get());
            hd.setNgayLap(LocalDate.now());
            hd.setNgayBatDau(dto.getNgayBatDau());
            hd.setNgayKetThuc(dto.getNgayKetThuc());
            hd.setGiaTien(dto.getGiaTien());
            hd.setTrangThai("Còn hạn");
            
            nguoiDungRepository.findById(maNhanVien).ifPresent(hd::setNhanVienLap);
            
            hopDongRepository.save(hd);
        }
    }

    public void guiYeuCau(String maHopDong, String loaiYeuCau) {
        Optional<HopDong> hdOpt = hopDongRepository.findById(maHopDong);
        if (hdOpt.isPresent()) {
            HopDong hd = hdOpt.get();
            hd.setYeuCauCuaSV(loaiYeuCau);
            hopDongRepository.save(hd);
        }
    }

    public void duyetGiaHan(String maHopDong, int soThang) {
        Optional<HopDong> hdOpt = hopDongRepository.findById(maHopDong);
        if (hdOpt.isPresent()) {
            HopDong hd = hdOpt.get();
            if (hd.getNgayKetThuc() != null) {
                hd.setNgayKetThuc(hd.getNgayKetThuc().plusMonths(soThang));
            } else {
                hd.setNgayKetThuc(LocalDate.now().plusMonths(soThang));
            }
            hd.setTrangThai("Còn hạn");
            hd.setYeuCauCuaSV(null); // Clear the request after approval
            hopDongRepository.save(hd);
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
            }
            hopDongRepository.save(hd);
        }
    }
}
