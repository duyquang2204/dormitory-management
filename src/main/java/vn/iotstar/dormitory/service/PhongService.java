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

    @Autowired
    private vn.iotstar.dormitory.repository.PhanPhongRepository phanPhongRepository;

    public void populateOccupancy(List<Phong> phongs) {
        if (phongs == null || phongs.isEmpty()) return;
        java.util.List<Object[]> counts = phanPhongRepository.countOccupantsByRoom();
        java.util.Map<String, Long> countMap = new java.util.HashMap<>();
        for (Object[] row : counts) {
            if (row != null && row.length >= 2 && row[0] != null && row[1] != null) {
                countMap.put((String) row[0], ((Number) row[1]).longValue());
            }
        }
        
        for (Phong p : phongs) {
            int max = (p.getLoaiPhong() != null && p.getLoaiPhong().getSoNguoiToiDa() != null) 
                    ? p.getLoaiPhong().getSoNguoiToiDa() : 0;
            long current = countMap.getOrDefault(p.getMaPhong(), 0L);
            
            // Xử lý dữ liệu demo nếu trạng thái phòng là Đang ở nhưng chưa có record PhanPhong
            if (current == 0 && "Đang ở".equalsIgnoreCase(p.getTrangThai())) {
                current = 1;
            } else if ("Đã đầy".equalsIgnoreCase(p.getTrangThai()) && current < max) {
                current = max;
            }
            
            p.setSoNguoiHienTai(current);
            p.setSoChoTrong(Math.max(0, max - current));
        }
    }

    public void populateOccupancy(Phong p) {
        if (p == null) return;
        populateOccupancy(java.util.Collections.singletonList(p));
    }

    public List<Phong> findAll() {
        List<Phong> list = phongRepository.findAll();
        populateOccupancy(list);
        return list;
    }
    
    public Page<Phong> findAll(Pageable pageable) {
        Page<Phong> page = phongRepository.findAll(pageable);
        populateOccupancy(page.getContent());
        return page;
    }
    
    public Page<Phong> findByKhu(String maKhu, Pageable pageable) {
        Page<Phong> page = phongRepository.findByKhu_MaKhu(maKhu, pageable);
        populateOccupancy(page.getContent());
        return page;
    }

    public Page<Phong> searchAndFilterPhong(String keyword, String maKhu, Integer tang, String gioiTinh, String trangThai, Pageable pageable) {
        List<Phong> all = phongRepository.findAll();
        populateOccupancy(all);

        all.sort((p1, p2) -> {
            String k1 = (p1.getKhu() != null && p1.getKhu().getTenKhu() != null) ? p1.getKhu().getTenKhu() : "";
            String k2 = (p2.getKhu() != null && p2.getKhu().getTenKhu() != null) ? p2.getKhu().getTenKhu() : "";
            int c = k1.compareToIgnoreCase(k2);
            if (c != 0) return c;
            int t1 = p1.getTang() != null ? p1.getTang() : 0;
            int t2 = p2.getTang() != null ? p2.getTang() : 0;
            if (t1 != t2) return Integer.compare(t1, t2);
            String s1 = p1.getSoPhong() != null ? p1.getSoPhong() : "";
            String s2 = p2.getSoPhong() != null ? p2.getSoPhong() : "";
            return s1.compareToIgnoreCase(s2);
        });

        List<Phong> filtered = all.stream().filter(p -> {
            if (keyword != null && !keyword.trim().isEmpty()) {
                if (p.getSoPhong() == null || !p.getSoPhong().toLowerCase().contains(keyword.trim().toLowerCase())) {
                    return false;
                }
            }
            if (maKhu != null && !maKhu.trim().isEmpty() && !"ALL".equalsIgnoreCase(maKhu.trim())) {
                if (p.getKhu() == null || !p.getKhu().getMaKhu().equalsIgnoreCase(maKhu.trim())) {
                    return false;
                }
            }
            if (tang != null && tang > 0) {
                if (p.getTang() == null || !p.getTang().equals(tang)) {
                    return false;
                }
            }
            if (gioiTinh != null && !gioiTinh.trim().isEmpty() && !"ALL".equalsIgnoreCase(gioiTinh.trim())) {
                if (p.getGioiTinh() == null || !p.getGioiTinh().equalsIgnoreCase(gioiTinh.trim())) {
                    return false;
                }
            }
            if (trangThai != null && !trangThai.trim().isEmpty() && !"ALL".equalsIgnoreCase(trangThai.trim())) {
                if ("CON_TRONG".equalsIgnoreCase(trangThai)) {
                    if (p.getSoNguoiHienTai() > 0) return false;
                } else if ("DANG_O".equalsIgnoreCase(trangThai)) {
                    if (p.getSoNguoiHienTai() <= 0 || p.getSoChoTrong() <= 0) return false;
                } else if ("DA_DAY".equalsIgnoreCase(trangThai)) {
                    if (p.getSoChoTrong() > 0) return false;
                } else if ("CON_CHO".equalsIgnoreCase(trangThai)) {
                    if (p.getSoChoTrong() <= 0) return false;
                }
            }
            return true;
        }).toList();

        int total = filtered.size();
        int start = (int) pageable.getOffset();
        if (start >= total) {
            return new org.springframework.data.domain.PageImpl<>(java.util.Collections.emptyList(), pageable, total);
        }
        int end = Math.min(start + pageable.getPageSize(), total);
        return new org.springframework.data.domain.PageImpl<>(filtered.subList(start, end), pageable, total);
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
        Optional<Phong> opt = phongRepository.findById(id);
        opt.ifPresent(this::populateOccupancy);
        return opt;
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
        
        khuRepository.findById(dto.getMaKhu()).ifPresent(p::setKhu);
        loaiPhongRepository.findById(dto.getMaLoaiPhong()).ifPresent(p::setLoaiPhong);

        if (p.getKhu() != null && dto.getSoPhong() != null) {
            String prefix = p.getKhu().getMaKhu().trim() + "-";
            if (!dto.getSoPhong().startsWith(prefix)) {
                String cleanNum = dto.getSoPhong().replace(p.getKhu().getMaKhu().trim(), "").replace("-", "").trim();
                p.setSoPhong(prefix + cleanNum);
            } else {
                p.setSoPhong(dto.getSoPhong().trim());
            }
        } else {
            p.setSoPhong(dto.getSoPhong());
        }

        if (dto.getTrangThai() != null && !dto.getTrangThai().isEmpty()) {
            p.setTrangThai(dto.getTrangThai());
        }
        
        phongRepository.save(p);
    }
}
