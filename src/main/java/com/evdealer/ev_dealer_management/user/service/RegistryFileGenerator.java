package com.evdealer.ev_dealer_management.user.service;

import com.evdealer.ev_dealer_management.user.model.DealerInfo;
import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.repository.DealerInfoRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class RegistryFileGenerator {

    @Value("${file.contract}")
    private String uploadDir;

    private final DealerInfoRepository dealerInfoRepository;

    private final static String URL_BASE = "http://localhost:8000/evdealer";
    private final static String FONT_PATH = URL_BASE + "/uploads/arial-font/arial.ttf";

    public DealerInfo generateContract(User manufacturer, User dealer, DealerInfo dealerInfo) throws IOException, DocumentException {

        String fileName = "registry-contract-" + dealerInfo.getId() + ".pdf";
        String fullPath = uploadDir + File.separator + fileName;

        // Ensure directory exists
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        try (FileOutputStream fos = new FileOutputStream(fullPath)) {
            PdfWriter.getInstance(document, fos);
            document.open();

            BaseFont baseFont = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 16, Font.BOLD);
            Font normalFont = new Font(baseFont, 12);
            Font boldFont = new Font(baseFont, 12, Font.BOLD);

            // Header
            Paragraph header = new Paragraph(
                    "CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM\nĐộc lập – Tự do – Hạnh phúc",
                    boldFont
            );
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(Chunk.NEWLINE);

            Paragraph contractTitle = new Paragraph("HỢP ĐỒNG HƯỞNG HOA HỒNG", titleFont);
            contractTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(contractTitle);
            document.add(Chunk.NEWLINE);

            OffsetDateTime now = OffsetDateTime.now();
            document.add(new Paragraph(
                    "Hôm nay, ngày " + now.getDayOfMonth() +
                            " tháng " + now.getMonthValue() +
                            " năm " + now.getYear() +
                            ", tại Thành phố Hồ Chí Minh",
                    normalFont
            ));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Chúng tôi gồm:", normalFont));
            document.add(Chunk.NEWLINE);

            // Bên A - Môi giới
            document.add(new Paragraph("BÊN MÔI GIỚI (Bên A): EVD", boldFont));
            document.add(new Paragraph("Giấy phép ĐKKD: ....................", normalFont));
            document.add(new Paragraph("Địa chỉ: " + manufacturer.getAddress(), normalFont));
            document.add(new Paragraph("Tài khoản: " + manufacturer.getUsername() +
                    "   Điện thoại/Fax: " + manufacturer.getPhone(), normalFont));
            document.add(new Paragraph("Đại diện: " + manufacturer.getFullName(), normalFont));
            document.add(Chunk.NEWLINE);

            // Bên B - Được môi giới
            document.add(new Paragraph("BÊN ĐƯỢC MÔI GIỚI (Bên B): " +
                    dealerInfo.getDealerName(), boldFont));
            document.add(new Paragraph("Giấy phép ĐKKD: ....................", normalFont));
            document.add(new Paragraph("Địa chỉ: " + dealerInfo.getLocation(), normalFont));
            document.add(new Paragraph("Tài khoản: " + dealer.getUsername() +
                    "   Điện thoại/Fax: " + dealerInfo.getDealerPhone(), normalFont));
            document.add(new Paragraph("Đại diện: " + dealer.getFullName(), normalFont));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph(
                    "Sau khi bàn bạc, thảo luận hai bên đi đến thống nhất ký hợp đồng môi giới hưởng hoa hồng với nội dung sau:",
                    normalFont));
            document.add(Chunk.NEWLINE);

            // ---------------------------
            // Điều khoản hợp đồng môi giới xe
            // ---------------------------

            addSection(document, "Điều 1: Nội dung công việc giao dịch",
                    "1. Bên A thực hiện môi giới khách hàng có nhu cầu mua xe ô tô để giới thiệu cho Bên B.\n" +
                            "2. Bên A kết nối khách hàng để Bên B đàm phán trực tiếp các điều khoản mua bán.\n" +
                            "3. Trong thời hạn 06 tháng kể từ ngày ký hợp đồng này, nếu Bên B bán xe hoặc thu được lợi nhuận từ khách hàng do Bên A giới thiệu thì giao dịch được xem là thành công.");

            addSection(document, "Điều 2: Mức thù lao và phương thức thanh toán",
                    "1. Bên B thanh toán cho Bên A phí môi giới là 1% trên tổng giá trị hợp đồng mua bán xe.\n" +
                            "2. Thanh toán bằng tiền Việt Nam, chuyển khoản hoặc tiền mặt.\n" +
                            "3. Bên B thanh toán cho Bên A ngay khi nhận được khoản thanh toán đầu tiên từ khách hàng.");

            addSection(document, "Điều 3: Quyền và nghĩa vụ của các bên",
                    "1. Bên A:\n" +
                            "- Thực hiện đúng nội dung môi giới.\n" +
                            "- Cung cấp thông tin khách hàng chính xác.\n" +
                            "- Hỗ trợ quá trình giao dịch giữa hai bên.\n" +
                            "- Được hưởng phí môi giới theo thỏa thuận.\n\n" +
                            "2. Bên B:\n" +
                            "- Thực hiện đúng thỏa thuận hợp đồng.\n" +
                            "- Thông báo tiến độ làm việc với khách hàng cho Bên A.\n" +
                            "- Không làm việc riêng với khách hàng do Bên A giới thiệu.\n" +
                            "- Thanh toán phí môi giới đầy đủ theo quy định.");

            addSection(document, "Điều 4: Giải quyết tranh chấp",
                    "1. Hai bên chủ động trao đổi để giải quyết các vấn đề phát sinh.\n" +
                            "2. Nếu không giải quyết được, tranh chấp sẽ được đưa ra Tòa án có thẩm quyền. Bên có lỗi chịu chi phí tố tụng.");

            addSection(document, "Điều 5: Hiệu lực hợp đồng",
                    "1. Hợp đồng có hiệu lực từ ngày ký.\n" +
                            "2. Hợp đồng gồm 02 bản có giá trị pháp lý như nhau, mỗi bên giữ 01 bản.");

            // Signatures
            PdfPTable signatureTable = new PdfPTable(2);
            signatureTable.setWidthPercentage(100);

            PdfPCell cellA = new PdfPCell();
            cellA.setBorder(Rectangle.NO_BORDER);
            cellA.addElement(new Phrase("ĐẠI DIỆN BÊN A\n(Ký tên, đóng dấu)", normalFont));

            PdfPCell cellB = new PdfPCell();
            cellB.setBorder(Rectangle.NO_BORDER);
            cellB.addElement(new Phrase("ĐẠI DIỆN BÊN B\n(Ký tên, đóng dấu)", normalFont));

            signatureTable.addCell(cellA);
            signatureTable.addCell(cellB);
            document.add(signatureTable);

            document.close();
        }

        // Save URL in DB
        dealerInfo.setContractFileUrl(URL_BASE + fullPath.substring(1).replace("\\", "/"));
        return dealerInfoRepository.save(dealerInfo);
    }

    private void addSection(Document doc, String title, String content, Font font) throws DocumentException {
        doc.add(new Paragraph(title, font));
        doc.add(new Paragraph(content, font));
        doc.add(Chunk.NEWLINE);
    }

    private void addSection(Document doc, String title, String content, Font font, boolean extra) throws DocumentException {
        // overload kept for future use
        addSection(doc, title, content, font);
    }

    // Convenience wrapper using default font
    private void addSection(Document doc, String title, String content) throws DocumentException {
        try {
            BaseFont bf = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font normalFont = new Font(bf, 12);
            addSection(doc, title, content, normalFont);
        } catch (Exception e) {
            // fallback
            addSection(doc, title, content, new Font(Font.FontFamily.HELVETICA, 12));
        }
    }

}
