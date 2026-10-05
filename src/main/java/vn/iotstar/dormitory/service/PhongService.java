package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.dto.PhongDTO;
import vn.iotstar.dormitory.entity.Khu;
import vn.iotstar.dormitory.entity.LoaiPhong;
import vn.iotstar.dormitory.entity.Phong;
import vn.iotstar.dormitory.repository.KhuRepository;
import vn.iotstar.dormitory.repository.LoaiPhongRepository;
import vn.iotstar.dormitory.repository.PhongRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class PhongService {

    @Autowired
    private PhongRepository phongRepository;
    
    @Autowired
    private KhuRepository khuRepository;
    
    @Autowired
    private LoaiPhongRepository loaiPhongRepository;

    public List<Phong> findAll() {
        return phongRepository.findAll();
    }
    
    public Page<Phong> findAll(Pageable pageable) {
        return phongRepository.findAll(pageable);
    }
    
    public Page<Phong> findByKhu(String maKhu, Pageable pageable) {
        return phongRepository.findByKhu_MaKhu(maKhu, pageable);
    }
    
    public List<Khu> findAllKhu() {
        return khuRepository.findAll();
    }
    
    public List<LoaiPhong> findAllLoaiPhong() {
        return loaiPhongRepository.findAll();
    }

    public void saveKhu(Khu khu) {
        if (khu.getMaKhu() == null || khu.getMaKhu().isEmpty()) {
            khu.setMaKhu(UUID.randomUUID().toString());
        }
        khuRepository.save(khu);
    }

    public void saveLoaiPhong(LoaiPhong loaiPhong) {
        if (loaiPhong.getMaLoaiPhong() == null || loaiPhong.getMaLoaiPhong().isEmpty()) {
            loaiPhong.setMaLoaiPhong(UUID.randomUUID().toString());
        }
        loaiPhongRepository.save(loaiPhong);
    }

    public Optional<Phong> findById(String id) {
        return phongRepository.findById(id);
    }

    public void save(PhongDTO dto) {
        Phong p;
        if (dto.getMaPhong() != null && !dto.getMaPhong().isEmpty()) {
            p = phongRepository.findById(dto.getMaPhong()).orElse(new Phong());
        } else {
            p = new Phong();
            p.setMaPhong(UUID.randomUUID().toString());
            p.setTrangThai("Trống");
        }
        
        p.setSoPhong(dto.getSoPhong());
        
        if (dto.getTrangThai() != null && !dto.getTrangThai().isEmpty()) {
            p.setTrangThai(dto.getTrangThai());
        }
        
        khuRepository.findById(dto.getMaKhu()).ifPresent(p::setKhu);
        loaiPhongRepository.findById(dto.getMaLoaiPhong()).ifPresent(p::setLoaiPhong);
        
        phongRepository.save(p);
    }
}
