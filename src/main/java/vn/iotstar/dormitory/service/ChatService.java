package vn.iotstar.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.iotstar.dormitory.dto.HoiThoaiDTO;
import vn.iotstar.dormitory.dto.TinNhanDTO;
import vn.iotstar.dormitory.entity.PhanPhong;
import vn.iotstar.dormitory.entity.SinhVien;
import vn.iotstar.dormitory.entity.TinNhan;
import vn.iotstar.dormitory.repository.PhanPhongRepository;
import vn.iotstar.dormitory.repository.SinhVienRepository;
import vn.iotstar.dormitory.repository.TinNhanRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Autowired
    private TinNhanRepository tinNhanRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private PhanPhongRepository phanPhongRepository;

    @Autowired
    private ThongBaoService thongBaoService;

    @Autowired(required = false)
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Gửi tin nhắn mới & broadcast realtime qua WebSocket
     */
    @Transactional
    public TinNhanDTO guiTinNhan(TinNhanDTO dto) {
        if (dto.getNoiDung() == null || dto.getNoiDung().trim().isEmpty()) {
            throw new IllegalArgumentException("Nội dung tin nhắn không được để trống");
        }

        // Tự động gán tên hiển thị nếu chưa có
        if (dto.getTenNguoiGui() == null || dto.getTenNguoiGui().isBlank()) {
            if ("SINH_VIEN".equalsIgnoreCase(dto.getVaiTroGui())) {
                Optional<SinhVien> svOpt = sinhVienRepository.findById(dto.getMaSV());
                dto.setTenNguoiGui(svOpt.map(SinhVien::getHoTen).orElse("Sinh viên " + dto.getMaSV()));
            } else {
                dto.setTenNguoiGui("Ban Quản Sinh KTX");
            }
        }

        TinNhan tinNhan = new TinNhan();
        tinNhan.setMaSV(dto.getMaSV());
        tinNhan.setNguoiGui(dto.getNguoiGui());
        tinNhan.setTenNguoiGui(dto.getTenNguoiGui());
        tinNhan.setVaiTroGui(dto.getVaiTroGui());
        tinNhan.setNoiDung(dto.getNoiDung().trim());
        tinNhan.setThoiGian(LocalDateTime.now());
        tinNhan.setDaDoc(false);

        TinNhan saved = tinNhanRepository.save(tinNhan);

        TinNhanDTO resultDto = convertToDTO(saved);

        // Broadcast realtime qua WebSocket
        if (messagingTemplate != null) {
            try {
                // Kênh hội thoại của sinh viên này
                messagingTemplate.convertAndSend("/topic/chat/" + dto.getMaSV(), resultDto);

                // Nếu sinh viên gửi, bắn thêm thông báo vào kênh chung của ban quản sinh
                if ("SINH_VIEN".equalsIgnoreCase(dto.getVaiTroGui())) {
                    messagingTemplate.convertAndSend("/topic/chat/quansinh", resultDto);

                    thongBaoService.taoThongBao(
                            "ROLE_QUAN_SINH",
                            "Tin nhắn mới từ " + dto.getTenNguoiGui(),
                            dto.getNoiDung(),
                            "Chat",
                            "/quansinh/chat?maSV=" + dto.getMaSV()
                    );
                } else {
                    // Quản sinh trả lời sinh viên
                    thongBaoService.taoThongBao(
                            dto.getMaSV(),
                            "Ban Quản Sinh đã phản hồi tin nhắn",
                            dto.getNoiDung(),
                            "Chat",
                            "/sinhvien/dashboard"
                    );
                }
            } catch (Exception ignored) {
            }
        }

        return resultDto;
    }

    /**
     * Lấy toàn bộ lịch sử tin nhắn của 1 sinh viên
     */
    public List<TinNhanDTO> layLichSuChat(String maSV) {
        return tinNhanRepository.findByMaSVOrderByThoiGianAsc(maSV)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Đánh dấu các tin nhắn là đã đọc
     */
    @Transactional
    public void danhDauDaDoc(String maSV, String vaiTroNguoiDoc) {
        String vaiTroDoiPhuong = "QUAN_SINH".equalsIgnoreCase(vaiTroNguoiDoc) ? "SINH_VIEN" : "QUAN_SINH";
        List<TinNhan> list = tinNhanRepository.findByMaSVOrderByThoiGianAsc(maSV);
        for (TinNhan tn : list) {
            if (vaiTroDoiPhuong.equalsIgnoreCase(tn.getVaiTroGui()) && Boolean.FALSE.equals(tn.getDaDoc())) {
                tn.setDaDoc(true);
                tinNhanRepository.save(tn);
            }
        }
    }

    /**
     * Lấy danh sách các cuộc hội thoại cho Quản sinh
     */
    public List<HoiThoaiDTO> layDanhSachHoiThoai() {
        List<String> maSVList = tinNhanRepository.findDistinctMaSVList();
        List<HoiThoaiDTO> result = new ArrayList<>();

        for (String maSV : maSVList) {
            List<TinNhan> msgs = tinNhanRepository.findByMaSVOrderByThoiGianAsc(maSV);
            if (msgs.isEmpty()) continue;

            TinNhan lastMsg = msgs.get(msgs.size() - 1);
            long unread = msgs.stream()
                    .filter(m -> "SINH_VIEN".equalsIgnoreCase(m.getVaiTroGui()) && Boolean.FALSE.equals(m.getDaDoc()))
                    .count();

            Optional<SinhVien> svOpt = sinhVienRepository.findById(maSV);
            String tenSV = svOpt.map(SinhVien::getHoTen).orElse(maSV);

            String phongStr = "Chưa có phòng";
            Optional<PhanPhong> ppOpt = phanPhongRepository.findBySinhVien_MaSVAndTrangThai(maSV, "Đang ở");
            if (ppOpt.isPresent() && ppOpt.get().getPhong() != null) {
                phongStr = "P." + ppOpt.get().getPhong().getSoPhong();
            }

            String timeStr = lastMsg.getThoiGian() != null ? lastMsg.getThoiGian().format(TIME_FORMATTER) : "";

            result.add(new HoiThoaiDTO(maSV, tenSV, phongStr, lastMsg.getNoiDung(), timeStr, unread));
        }

        // Sắp xếp các cuộc hội thoại có tin mới lên đầu
        result.sort((a, b) -> b.getThoiGianCuoi().compareTo(a.getThoiGianCuoi()));
        return result;
    }

    /**
     * Lấy số lượng tin nhắn chưa đọc của một sinh viên từ Quản Sinh
     */
    public long laySoTinNhanChuaDocChoSinhVien(String maSV) {
        if (maSV == null || maSV.isBlank()) return 0;
        return tinNhanRepository.countByMaSVAndVaiTroGuiAndDaDocFalse(maSV, "QUAN_SINH");
    }

    /**
     * Lấy tổng số lượng tin nhắn chưa đọc từ tất cả sinh viên gửi cho Ban Quản Sinh
     */
    public long laySoTinNhanChuaDocChoQuanSinh() {
        return tinNhanRepository.countByVaiTroGuiAndDaDocFalse("SINH_VIEN");
    }

    private TinNhanDTO convertToDTO(TinNhan tn) {
        TinNhanDTO dto = new TinNhanDTO();
        dto.setId(tn.getId());
        dto.setMaSV(tn.getMaSV());
        dto.setNguoiGui(tn.getNguoiGui());
        dto.setTenNguoiGui(tn.getTenNguoiGui());
        dto.setVaiTroGui(tn.getVaiTroGui());
        dto.setNoiDung(tn.getNoiDung());
        dto.setThoiGian(tn.getThoiGian());
        dto.setThoiGianFormatted(tn.getThoiGian() != null ? tn.getThoiGian().format(TIME_FORMATTER) : "");
        dto.setDaDoc(tn.getDaDoc());
        return dto;
    }
}
