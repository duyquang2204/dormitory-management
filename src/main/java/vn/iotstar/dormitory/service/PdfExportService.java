package vn.iotstar.dormitory.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;
import vn.iotstar.dormitory.entity.HoaDon;
import vn.iotstar.dormitory.entity.HopDong;
import vn.iotstar.dormitory.entity.PhanPhong;
import vn.iotstar.dormitory.entity.SinhVien;

import java.awt.Color;
import java.io.File;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PdfExportService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat CURRENCY_FORMATTER = new DecimalFormat("#,###");

    private BaseFont getBaseFont(boolean bold) {
        try {
            String regularPath = "C:/Windows/Fonts/arial.ttf";
            String boldPath = "C:/Windows/Fonts/arialbd.ttf";
            String chosenPath = bold ? boldPath : regularPath;
            if (new File(chosenPath).exists()) {
                return BaseFont.createFont(chosenPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            } else if (new File(regularPath).exists()) {
                return BaseFont.createFont(regularPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            }
        } catch (Exception ignored) {
        }
        try {
            return BaseFont.createFont(bold ? BaseFont.HELVETICA_BOLD : BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
        } catch (Exception ex) {
            throw new RuntimeException("Could not initialize PDF BaseFont", ex);
        }
    }

    private Font getFont(float size, int style, Color color) {
        boolean isBold = (style & Font.BOLD) != 0;
        BaseFont bf = getBaseFont(isBold);
        return new Font(bf, size, style, color);
    }

    /**
     * Xuất Hợp Đồng Ký Túc Xá ra file PDF
     */
    public void exportHopDongPdf(HopDong hd, OutputStream out) throws Exception {
        Document document = new Document(PageSize.A4, 40, 40, 40, 40);
        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = getFont(15, Font.BOLD, new Color(20, 50, 95));
        Font subtitleFont = getFont(11, Font.BOLD, Color.BLACK);
        Font boldFont = getFont(10, Font.BOLD, Color.BLACK);
        Font normalFont = getFont(10, Font.NORMAL, new Color(40, 40, 40));
        Font italicFont = getFont(9, Font.ITALIC, new Color(90, 90, 90));
        Font sectionFont = getFont(11, Font.BOLD, new Color(25, 90, 140));

        // 1. Tiêu ngữ Quốc gia
        Paragraph pQuocHieu = new Paragraph("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM", subtitleFont);
        pQuocHieu.setAlignment(Element.ALIGN_CENTER);
        document.add(pQuocHieu);

        Paragraph pTieuNgu = new Paragraph("Độc lập - Tự do - Hạnh phúc", getFont(10, Font.BOLD, Color.BLACK));
        pTieuNgu.setAlignment(Element.ALIGN_CENTER);
        document.add(pTieuNgu);

        Paragraph pLine = new Paragraph("----------------------------------------", italicFont);
        pLine.setAlignment(Element.ALIGN_CENTER);
        pLine.setSpacingAfter(15);
        document.add(pLine);

        // 2. Tiêu đề Hợp đồng
        Paragraph pTitle = new Paragraph("HỢP ĐỒNG THUÊ CHỖ Ở KÝ TÚC XÁ", titleFont);
        pTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(pTitle);

        Paragraph pSoHD = new Paragraph("Mã hợp đồng: " + (hd.getMaHopDong() != null ? hd.getMaHopDong() : "HD-KTX"), italicFont);
        pSoHD.setAlignment(Element.ALIGN_CENTER);
        pSoHD.setSpacingAfter(15);
        document.add(pSoHD);

        // 3. Căn cứ pháp lý
        Paragraph pCanCu = new Paragraph("Hôm nay, ngày " + LocalDate.now().getDayOfMonth() + " tháng " + LocalDate.now().getMonthValue() + " năm " + LocalDate.now().getYear() + ", tại Ban Quản Lý Ký Túc Xá, chúng tôi gồm có:", normalFont);
        pCanCu.setSpacingAfter(10);
        document.add(pCanCu);

        // 4. BÊN A
        Paragraph pBenA = new Paragraph("BÊN A: BAN QUẢN LÝ KÝ TÚC XÁ (Bên cho thuê)", sectionFont);
        pBenA.setSpacingAfter(4);
        document.add(pBenA);

        PdfPTable tableA = new PdfPTable(2);
        tableA.setWidthPercentage(100);
        tableA.setWidths(new float[]{30f, 70f});
        tableA.setSpacingAfter(10);

        addTableRow(tableA, "Đại diện cơ sở:", "Ban Quản Lý Ký Túc Xá Tư Nhân Sinh Viên", boldFont, normalFont);
        addTableRow(tableA, "Địa chỉ:", "Khu đô thị Đại học, TP. Hồ Chí Minh", boldFont, normalFont);
        addTableRow(tableA, "Hotline hỗ trợ:", "028.3896.8888 - 0909.123.456", boldFont, normalFont);
        addTableRow(tableA, "Email liên hệ:", "dormitory.mgmt.system@gmail.com", boldFont, normalFont);
        document.add(tableA);

        // 5. BÊN B
        PhanPhong pp = hd.getPhanPhong();
        SinhVien sv = pp != null ? pp.getSinhVien() : null;

        Paragraph pBenB = new Paragraph("BÊN B: SINH VIÊN (Bên thuê)", sectionFont);
        pBenB.setSpacingAfter(4);
        document.add(pBenB);

        PdfPTable tableB = new PdfPTable(2);
        tableB.setWidthPercentage(100);
        tableB.setWidths(new float[]{30f, 70f});
        tableB.setSpacingAfter(10);

        addTableRow(tableB, "Họ và tên sinh viên:", sv != null ? sv.getHoTen() : "N/A", boldFont, normalFont);
        addTableRow(tableB, "Mã số sinh viên:", sv != null ? sv.getMaSV() : "N/A", boldFont, normalFont);
        addTableRow(tableB, "Trường Đại học:", sv != null && sv.getTruongDaiHoc() != null ? sv.getTruongDaiHoc() : "Đại học chính quy", boldFont, normalFont);
        addTableRow(tableB, "Số CCCD / CMND:", sv != null && sv.getCccd() != null ? sv.getCccd() : "N/A", boldFont, normalFont);
        addTableRow(tableB, "Số điện thoại:", sv != null && sv.getSdt() != null ? sv.getSdt() : "N/A", boldFont, normalFont);
        addTableRow(tableB, "Email sinh viên:", sv != null && sv.getEmail() != null ? sv.getEmail() : "N/A", boldFont, normalFont);
        document.add(tableB);

        // 6. ĐIỀU KHOẢN HỢP ĐỒNG
        Paragraph pDieu1 = new Paragraph("ĐIỀU 1: ĐỐI TƯỢNG VÀ THỜI HẠN THUÊ", sectionFont);
        document.add(pDieu1);

        String soPhong = (pp != null && pp.getPhong() != null) ? pp.getPhong().getSoPhong() : "N/A";
        String tenKhu = (pp != null && pp.getPhong() != null && pp.getPhong().getKhu() != null) ? pp.getPhong().getKhu().getTenKhu() : "N/A";
        String loaiPhong = (pp != null && pp.getPhong() != null && pp.getPhong().getLoaiPhong() != null) ? pp.getPhong().getLoaiPhong().getTenLoaiPhong() : "Tiêu chuẩn";

        String ngayBD = hd.getNgayBatDau() != null ? hd.getNgayBatDau().format(DATE_FORMATTER) : "N/A";
        String ngayKT = hd.getNgayKetThuc() != null ? hd.getNgayKetThuc().format(DATE_FORMATTER) : "N/A";

        Paragraph pNoiDung1 = new Paragraph("- Bên A đồng ý bố trí cho Bên B một chỗ ở nội trú tại Phòng: " + soPhong + " (Khu: " + tenKhu + "), Loại phòng: " + loaiPhong + ".\n"
                + "- Thời hạn hợp đồng: Từ ngày " + ngayBD + " đến ngày " + ngayKT + ".\n"
                + "- Mục đích sử dụng: Lưu trú sinh hoạt phục vụ học tập, không được chuyển nhượng hoặc cho người khác ở nhờ.", normalFont);
        pNoiDung1.setSpacingAfter(8);
        document.add(pNoiDung1);

        Paragraph pDieu2 = new Paragraph("ĐIỀU 2: GIÁ THUÊ VÀ NGHĨA VỤ TÀI CHÍNH", sectionFont);
        document.add(pDieu2);

        String giaTienStr = hd.getGiaTien() != null ? CURRENCY_FORMATTER.format(hd.getGiaTien()) + " VNĐ" : "Theo quy định";
        Paragraph pNoiDung2 = new Paragraph("- Giá thuê lưu trú: " + giaTienStr + " / tháng.\n"
                + "- Chi phí điện, nước: Tính theo chỉ số thực tế tiêu thụ hàng tháng của phòng và chia đều cho các sinh viên nội trú.\n"
                + "- Thời hạn nộp: Hóa đơn dịch vụ hàng tháng được phát hành trên hệ thống và Bên B có trách nhiệm thanh toán qua cổng VNPAY hoặc chuyển khoản trước ngày 25 hàng tháng.", normalFont);
        pNoiDung2.setSpacingAfter(8);
        document.add(pNoiDung2);

        Paragraph pDieu3 = new Paragraph("ĐIỀU 3: QUYỀN VÀ TRÁCH NHIỆM CHUNG", sectionFont);
        document.add(pDieu3);

        String dieuKhoanRieng = (hd.getDieuKhoan() != null && !hd.getDieuKhoan().isBlank()) ? hd.getDieuKhoan() : "Chấp hành tuyệt đối Nội quy Ký túc xá, bảo quản tài sản chung, giữ gìn trật tự và phòng cháy chữa cháy.";
        Paragraph pNoiDung3 = new Paragraph("- Điều khoản thỏa thuận: " + dieuKhoanRieng + "\n"
                + "- Hết thời hạn hợp đồng, nếu Bên B có nguyện vọng tiếp tục ở lại phải gửi yêu cầu 'Xin gia hạn' trên hệ thống trước ít nhất 15 ngày.\n"
                + "- Hợp đồng được lập thành 02 bản có giá trị pháp lý như nhau, mỗi bên giữ 01 bản để thực hiện.", normalFont);
        pNoiDung3.setSpacingAfter(25);
        document.add(pNoiDung3);

        // 7. Chữ ký 2 bên
        PdfPTable tableKy = new PdfPTable(2);
        tableKy.setWidthPercentage(100);

        PdfPCell cellKyB = new PdfPCell();
        cellKyB.setBorder(Rectangle.NO_BORDER);
        cellKyB.setHorizontalAlignment(Element.ALIGN_CENTER);
        Paragraph pKyB = new Paragraph("ĐẠI DIỆN BÊN B (SINH VIÊN)\n(Ký và ghi rõ họ tên)\n\n\n\n", boldFont);
        pKyB.setAlignment(Element.ALIGN_CENTER);
        cellKyB.addElement(pKyB);
        if (sv != null) {
            Paragraph pTenB = new Paragraph(sv.getHoTen(), boldFont);
            pTenB.setAlignment(Element.ALIGN_CENTER);
            cellKyB.addElement(pTenB);
        }

        PdfPCell cellKyA = new PdfPCell();
        cellKyA.setBorder(Rectangle.NO_BORDER);
        cellKyA.setHorizontalAlignment(Element.ALIGN_CENTER);
        Paragraph pKyA = new Paragraph("ĐẠI DIỆN BÊN A (BAN QUẢN LÝ)\n(Ký tên và đóng dấu)\n\n\n\n", boldFont);
        pKyA.setAlignment(Element.ALIGN_CENTER);
        cellKyA.addElement(pKyA);
        Paragraph pTenA = new Paragraph("BAN QUẢN LÝ KÝ TÚC XÁ", boldFont);
        pTenA.setAlignment(Element.ALIGN_CENTER);
        cellKyA.addElement(pTenA);

        tableKy.addCell(cellKyB);
        tableKy.addCell(cellKyA);
        document.add(tableKy);

        document.close();
    }

    /**
     * Xuất Hóa Đơn Điện Nước & Dịch Vụ ra file PDF
     */
    public void exportHoaDonPdf(HoaDon hd, OutputStream out) throws Exception {
        Document document = new Document(PageSize.A4, 40, 40, 40, 40);
        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = getFont(16, Font.BOLD, new Color(20, 50, 95));
        Font boldFont = getFont(10, Font.BOLD, Color.BLACK);
        Font normalFont = getFont(10, Font.NORMAL, new Color(40, 40, 40));
        Font italicFont = getFont(9, Font.ITALIC, new Color(90, 90, 90));
        Font thFont = getFont(10, Font.BOLD, Color.WHITE);

        // 1. Header cơ sở
        Paragraph pBrand = new Paragraph("BAN QUẢN LÝ KÝ TÚC XÁ SINH VIÊN", getFont(11, Font.BOLD, new Color(20, 50, 95)));
        pBrand.setAlignment(Element.ALIGN_LEFT);
        document.add(pBrand);

        Paragraph pAddr = new Paragraph("Địa chỉ: Khu Đô Thị Đại Học, TP.HCM | Hotline: 028.3896.8888", italicFont);
        pAddr.setAlignment(Element.ALIGN_LEFT);
        pAddr.setSpacingAfter(15);
        document.add(pAddr);

        // 2. Tiêu đề Hóa Đơn
        Paragraph pTitle = new Paragraph("HÓA ĐƠN TIỀN PHÒNG & DỊCH VỤ ĐIỆN NƯỚC", titleFont);
        pTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(pTitle);

        Paragraph pKy = new Paragraph("Kỳ thanh toán: Tháng " + hd.getThang() + " / Năm " + hd.getNam(), getFont(11, Font.BOLD, new Color(220, 53, 69)));
        pKy.setAlignment(Element.ALIGN_CENTER);
        document.add(pKy);

        Paragraph pMa = new Paragraph("Mã hóa đơn: " + hd.getMaHoaDon(), italicFont);
        pMa.setAlignment(Element.ALIGN_CENTER);
        pMa.setSpacingAfter(15);
        document.add(pMa);

        // 3. Thông tin người nộp & phòng
        PdfPTable tableInfo = new PdfPTable(2);
        tableInfo.setWidthPercentage(100);
        tableInfo.setWidths(new float[]{50f, 50f});
        tableInfo.setSpacingAfter(15);

        String hoTenSV = hd.getSinhVien() != null ? hd.getSinhVien().getHoTen() : "Toàn bộ phòng";
        String maSV = hd.getSinhVien() != null ? hd.getSinhVien().getMaSV() : "N/A";
        String phongStr = (hd.getPhong() != null ? "Phòng " + hd.getPhong().getSoPhong() : "N/A")
                + (hd.getPhong() != null && hd.getPhong().getKhu() != null ? " (Khu " + hd.getPhong().getKhu().getTenKhu() + ")" : "");
        String ngayTaoStr = hd.getNgayTao() != null ? hd.getNgayTao().format(DATE_FORMATTER) : LocalDate.now().format(DATE_FORMATTER);

        addInfoCell(tableInfo, "Sinh viên:", hoTenSV + " (" + maSV + ")", boldFont, normalFont);
        addInfoCell(tableInfo, "Phòng lưu trú:", phongStr, boldFont, normalFont);
        addInfoCell(tableInfo, "Ngày phát hành:", ngayTaoStr, boldFont, normalFont);
        addInfoCell(tableInfo, "Hạn thanh toán:", "25/" + hd.getThang() + "/" + hd.getNam(), boldFont, normalFont);
        addInfoCell(tableInfo, "Trạng thái:", hd.getTrangThai(), boldFont, normalFont);
        addInfoCell(tableInfo, "Phương thức:", hd.getPhuongThucThanhToan() != null ? hd.getPhuongThucThanhToan() : "Cổng VNPAY / Chuyển khoản", boldFont, normalFont);

        document.add(tableInfo);

        // 4. Bảng kê chi phí
        PdfPTable tableChiTiet = new PdfPTable(6);
        tableChiTiet.setWidthPercentage(100);
        tableChiTiet.setWidths(new float[]{8f, 32f, 15f, 15f, 15f, 15f});
        tableChiTiet.setSpacingAfter(15);

        // Header cells
        Color headerBg = new Color(20, 50, 95);
        addHeaderCell(tableChiTiet, "STT", thFont, headerBg);
        addHeaderCell(tableChiTiet, "Dịch vụ", thFont, headerBg);
        addHeaderCell(tableChiTiet, "Chỉ số cũ", thFont, headerBg);
        addHeaderCell(tableChiTiet, "Chỉ số mới", thFont, headerBg);
        addHeaderCell(tableChiTiet, "Tiêu thụ", thFont, headerBg);
        addHeaderCell(tableChiTiet, "Thành tiền", thFont, headerBg);

        // Dòng 1: Tiền phòng
        double tienPhong = hd.getTienPhong() != null ? hd.getTienPhong() : 0.0;
        addDataCell(tableChiTiet, "1", normalFont, Element.ALIGN_CENTER);
        addDataCell(tableChiTiet, "Tiền thuê chỗ ở KTX", normalFont, Element.ALIGN_LEFT);
        addDataCell(tableChiTiet, "-", normalFont, Element.ALIGN_CENTER);
        addDataCell(tableChiTiet, "-", normalFont, Element.ALIGN_CENTER);
        addDataCell(tableChiTiet, "1 tháng", normalFont, Element.ALIGN_CENTER);
        addDataCell(tableChiTiet, CURRENCY_FORMATTER.format(tienPhong) + " đ", boldFont, Element.ALIGN_RIGHT);

        // Dòng 2: Tiền điện
        double chiSoDienCu = hd.getChiSoDienCu() != null ? hd.getChiSoDienCu() : 0.0;
        double chiSoDienMoi = hd.getChiSoDienMoi() != null ? hd.getChiSoDienMoi() : 0.0;
        double soDien = Math.max(0, chiSoDienMoi - chiSoDienCu);
        double tienDien = hd.getTienDien() != null ? hd.getTienDien() : 0.0;

        addDataCell(tableChiTiet, "2", normalFont, Element.ALIGN_CENTER);
        addDataCell(tableChiTiet, "Tiền điện (3.500 đ/kWh)", normalFont, Element.ALIGN_LEFT);
        addDataCell(tableChiTiet, String.valueOf(chiSoDienCu), normalFont, Element.ALIGN_CENTER);
        addDataCell(tableChiTiet, String.valueOf(chiSoDienMoi), normalFont, Element.ALIGN_CENTER);
        addDataCell(tableChiTiet, soDien + " kWh", normalFont, Element.ALIGN_CENTER);
        addDataCell(tableChiTiet, CURRENCY_FORMATTER.format(tienDien) + " đ", boldFont, Element.ALIGN_RIGHT);

        // Dòng 3: Tiền nước
        double chiSoNuocCu = hd.getChiSoNuocCu() != null ? hd.getChiSoNuocCu() : 0.0;
        double chiSoNuocMoi = hd.getChiSoNuocMoi() != null ? hd.getChiSoNuocMoi() : 0.0;
        double soNuoc = Math.max(0, chiSoNuocMoi - chiSoNuocCu);
        double tienNuoc = hd.getTienNuoc() != null ? hd.getTienNuoc() : 0.0;

        addDataCell(tableChiTiet, "3", normalFont, Element.ALIGN_CENTER);
        addDataCell(tableChiTiet, "Tiền nước (15.000 đ/m³)", normalFont, Element.ALIGN_LEFT);
        addDataCell(tableChiTiet, String.valueOf(chiSoNuocCu), normalFont, Element.ALIGN_CENTER);
        addDataCell(tableChiTiet, String.valueOf(chiSoNuocMoi), normalFont, Element.ALIGN_CENTER);
        addDataCell(tableChiTiet, soNuoc + " m³", normalFont, Element.ALIGN_CENTER);
        addDataCell(tableChiTiet, CURRENCY_FORMATTER.format(tienNuoc) + " đ", boldFont, Element.ALIGN_RIGHT);

        // Dòng Tổng cộng
        double tongTien = hd.getTongTien() != null ? hd.getTongTien() : (tienPhong + tienDien + tienNuoc);
        PdfPCell cellTotalLabel = new PdfPCell(new Phrase("TỔNG CỘNG THANH TOÁN:", getFont(10, Font.BOLD, new Color(20, 50, 95))));
        cellTotalLabel.setColspan(5);
        cellTotalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellTotalLabel.setPadding(8);
        cellTotalLabel.setBackgroundColor(new Color(245, 245, 245));
        tableChiTiet.addCell(cellTotalLabel);

        PdfPCell cellTotalValue = new PdfPCell(new Phrase(CURRENCY_FORMATTER.format(tongTien) + " VNĐ", getFont(11, Font.BOLD, new Color(220, 53, 69))));
        cellTotalValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellTotalValue.setPadding(8);
        cellTotalValue.setBackgroundColor(new Color(245, 245, 245));
        tableChiTiet.addCell(cellTotalValue);

        document.add(tableChiTiet);

        // 5. Ghi chú và hướng dẫn nộp tiền
        Paragraph pNote = new Paragraph("Ghi chú: Tiền điện và tiền nước được tính theo chỉ số thực tế của phòng và chia đều cho các sinh viên trong phòng.\nSinh viên vui lòng thanh toán đúng thời hạn trước ngày 25 hàng tháng để tránh gián đoạn dịch vụ.", italicFont);
        pNote.setSpacingAfter(25);
        document.add(pNote);

        // 6. Chữ ký
        PdfPTable tableKy = new PdfPTable(2);
        tableKy.setWidthPercentage(100);

        PdfPCell cellKyNguoiNop = new PdfPCell();
        cellKyNguoiNop.setBorder(Rectangle.NO_BORDER);
        Paragraph pNop = new Paragraph("NGƯỜI NỘP TIỀN\n(Ký và ghi rõ họ tên)\n\n\n\n", boldFont);
        pNop.setAlignment(Element.ALIGN_CENTER);
        cellKyNguoiNop.addElement(pNop);

        PdfPCell cellKyNguoiLap = new PdfPCell();
        cellKyNguoiLap.setBorder(Rectangle.NO_BORDER);
        Paragraph pLap = new Paragraph("NGƯỜI LẬP HÓA ĐƠN\n(Ký và đóng dấu xác nhận)\n\n\n\n", boldFont);
        pLap.setAlignment(Element.ALIGN_CENTER);
        cellKyNguoiLap.addElement(pLap);
        Paragraph pBanQL = new Paragraph("BAN QUẢN LÝ KÝ TÚC XÁ", boldFont);
        pBanQL.setAlignment(Element.ALIGN_CENTER);
        cellKyNguoiLap.addElement(pBanQL);

        tableKy.addCell(cellKyNguoiNop);
        tableKy.addCell(cellKyNguoiLap);
        document.add(tableKy);

        document.close();
    }

    private void addTableRow(PdfPTable table, String label, String val, Font boldFont, Font normalFont) {
        PdfPCell c1 = new PdfPCell(new Phrase(label, boldFont));
        c1.setBorder(Rectangle.NO_BORDER);
        c1.setPadding(3);
        table.addCell(c1);

        PdfPCell c2 = new PdfPCell(new Phrase(val, normalFont));
        c2.setBorder(Rectangle.NO_BORDER);
        c2.setPadding(3);
        table.addCell(c2);
    }

    private void addInfoCell(PdfPTable table, String label, String val, Font boldFont, Font normalFont) {
        Paragraph p = new Paragraph();
        p.add(new Chunk(label + " ", boldFont));
        p.add(new Chunk(val, normalFont));
        PdfPCell cell = new PdfPCell(p);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(4);
        table.addCell(cell);
    }

    private void addHeaderCell(PdfPTable table, String text, Font font, Color bg) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bg);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6);
        table.addCell(cell);
    }

    private void addDataCell(PdfPTable table, String text, Font font, int align) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(align);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6);
        table.addCell(cell);
    }
}
