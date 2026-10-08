package vn.iotstar.dormitory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.dormitory.dto.HoiThoaiDTO;
import vn.iotstar.dormitory.dto.TinNhanDTO;
import vn.iotstar.dormitory.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    /**
     * Endpoint STOMP nhận tin nhắn từ client qua WebSocket destination "/app/chat.send"
     */
    @MessageMapping("/chat.send")
    public void receiveMessageWs(@Payload TinNhanDTO dto) {
        chatService.guiTinNhan(dto);
    }

    /**
     * API REST lấy lịch sử tin nhắn của 1 sinh viên
     */
    @GetMapping("/history")
    public ResponseEntity<List<TinNhanDTO>> getHistory(@RequestParam("maSV") String maSV) {
        return ResponseEntity.ok(chatService.layLichSuChat(maSV));
    }

    /**
     * API REST gửi tin nhắn (dùng cho AJAX fallback)
     */
    @PostMapping("/send")
    public ResponseEntity<TinNhanDTO> sendMessageRest(@RequestBody TinNhanDTO dto, Authentication auth) {
        if (dto.getNguoiGui() == null || dto.getNguoiGui().isBlank()) {
            if (auth != null) {
                dto.setNguoiGui(auth.getName());
            }
        }
        TinNhanDTO saved = chatService.guiTinNhan(dto);
        return ResponseEntity.ok(saved);
    }

    /**
     * API REST đánh dấu đã đọc
     */
    @PostMapping("/read")
    public ResponseEntity<Void> markAsRead(@RequestParam("maSV") String maSV,
                                           @RequestParam("vaiTro") String vaiTro) {
        chatService.danhDauDaDoc(maSV, vaiTro);
        return ResponseEntity.ok().build();
    }

    /**
     * API REST lấy danh sách các cuộc hội thoại cho Quản sinh
     */
    @GetMapping("/conversations")
    public ResponseEntity<List<HoiThoaiDTO>> getConversations() {
        return ResponseEntity.ok(chatService.layDanhSachHoiThoai());
    }

    /**
     * API REST lấy số tin nhắn chưa đọc
     */
    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(@RequestParam(value = "maSV", required = false) String maSV,
                                               @RequestParam(value = "vaiTro", defaultValue = "SINH_VIEN") String vaiTro,
                                               Authentication auth) {
        if ("SINH_VIEN".equalsIgnoreCase(vaiTro)) {
            String studentId = (maSV != null && !maSV.isBlank()) ? maSV : (auth != null ? auth.getName() : "");
            return ResponseEntity.ok(chatService.laySoTinNhanChuaDocChoSinhVien(studentId));
        } else {
            return ResponseEntity.ok(chatService.laySoTinNhanChuaDocChoQuanSinh());
        }
    }
}
