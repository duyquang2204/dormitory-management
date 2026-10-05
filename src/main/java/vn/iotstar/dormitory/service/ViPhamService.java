package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.dto.ViPhamDTO;
import vn.iotstar.dormitory.entity.SinhVien;
import vn.iotstar.dormitory.entity.ViPham;
import vn.iotstar.dormitory.repository.SinhVienRepository;
import vn.iotstar.dormitory.repository.ViPhamRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ViPhamService {

    @Autowired
    private ViPhamRepository viPhamRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    public Page<ViPham> findAll(Pageable pageable) {
        return viPhamRepository.findAll(pageable);
    }

    public Page<ViPham> searchByMaSV(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return viPhamRepository.findBySinhVien_MaSVContainingIgnoreCase(keyword, pageable);
        }
        return viPhamRepository.findAll(pageable);
    }

    public List<ViPham> findByMaSV(String maSV) {
        return viPhamRepository.findBySinhVien_MaSV(maSV);
    }

    public long countByMaSV(String maSV) {
        return viPhamRepository.countBySinhVien_MaSV(maSV);
    }

    public void save(ViPhamDTO dto) {
        Optional<SinhVien> svOpt = sinhVienRepository.findById(dto.getMaSV());
        if (!svOpt.isPresent()) {
            throw new RuntimeException("Không tìm thấy sinh viên có mã: " + dto.getMaSV());
        }

        ViPham vp;
        if (dto.getMaViPham() != null && !dto.getMaViPham().isEmpty()) {
            vp = viPhamRepository.findById(dto.getMaViPham()).orElse(new ViPham());
        } else {
            vp = new ViPham();
            vp.setMaViPham(UUID.randomUUID().toString());
            vp.setNgayLapBienBan(LocalDate.now());
        }

        vp.setSinhVien(svOpt.get());
        vp.setNgayViPham(dto.getNgayViPham());
        vp.setNoiDung(dto.getNoiDung());
        vp.setDiaDiem(dto.getDiaDiem());
        vp.setHinhThucXuLy(dto.getHinhThucXuLy());

        if (dto.getTrangThaiXuLy() != null && !dto.getTrangThaiXuLy().isEmpty()) {
            vp.setTrangThaiXuLy(dto.getTrangThaiXuLy());
        } else if (vp.getTrangThaiXuLy() == null) {
            vp.setTrangThaiXuLy("Chờ xử lý");
        }

        viPhamRepository.save(vp);
    }

    public void xuLy(String id, String hinhThuc) {
        Optional<ViPham> opt = viPhamRepository.findById(id);
        if (opt.isPresent()) {
            ViPham vp = opt.get();
            vp.setHinhThucXuLy(hinhThuc);
            vp.setTrangThaiXuLy("Đã xử lý");
            viPhamRepository.save(vp);
        }
    }
}
