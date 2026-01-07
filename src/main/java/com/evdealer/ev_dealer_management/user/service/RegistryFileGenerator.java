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

//    private final static String URL_BASE = "http://3.107.14.223:8000/evdealer";
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

            BaseFont baseFont;
            try {
                baseFont = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            } catch (Exception e) {
                baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            }

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

            Paragraph contractTitle = new Paragraph("HỢP ĐỒNG ĐẠI LÝ THƯƠNG MẠI", titleFont);
            contractTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(contractTitle);

            document.add(Chunk.NEWLINE);

            OffsetDateTime now = OffsetDateTime.now();
            document.add(new Paragraph(
                    "Số: ………/…/ HĐĐL",
                    normalFont
            ));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("- Căn cứ vào Bộ luật dân sự được Quốc Hội Nước Cộng Hoà Xã Hội Chủ Nghĩa Việt Nam thông qua ngày 24/11/2015;", normalFont));
            document.add(new Paragraph("- Căn cứ Luật Thương mại được Quốc Hội Nước Cộng Hoà Xã Hội Chủ Nghĩa Việt Nam thông qua ngày 14/06/2005.", normalFont));
            document.add(new Paragraph("- Căn cứ vào khả năng và nhu cầu của hai bên.", normalFont));
            document.add(new Paragraph("- Dựa trên tinh thần trung thực và thiện chí hợp tác của các bên.", normalFont));

            document.add(Chunk.NEWLINE);

            document.add(new Paragraph(
                    "Hôm nay, ngày " + now.getDayOfMonth() + " tháng " + now.getMonthValue() + " năm " + now.getYear() + "  Tại " + (dealerInfo.getLocation() == null ? "................" : dealerInfo.getLocation()),
                    normalFont
            ));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Chúng tôi gồm có:", normalFont));
            document.add(Chunk.NEWLINE);

            // Bên A - Bên giao đại lý
            document.add(new Paragraph("BÊN GIAO ĐẠI LÝ (gọi tắt là Bên A): HÃNG XE ĐIỆN EV", boldFont));
            document.add(new Paragraph("Giấy phép Đăng ký Kinh doanh: " + "…………………………..………………….", normalFont));
            document.add(new Paragraph("Trụ sở: " + (manufacturer.getAddress() == null ? "…………………………………………" : manufacturer.getAddress()), normalFont));
            document.add(new Paragraph("Tài khoản số: ………………………………", normalFont));
            document.add(new Paragraph("Điện thoại: " + (manufacturer.getPhone() == null ? "……………" : manufacturer.getPhone()), normalFont));
            document.add(new Paragraph("Đại diện: Ông (Bà): " + (manufacturer.getFullName() == null ? "…………………………………….." : manufacturer.getFullName()), normalFont));
            document.add(Chunk.NEWLINE);

            // Bên B - Bên đại lý
            document.add(new Paragraph("BÊN ĐẠI LÝ (gọi tắt là Bên B): " + (dealerInfo.getDealerName() == null ? "……………." : dealerInfo.getDealerName()), boldFont));
            document.add(new Paragraph("Giấy phép Đăng ký Kinh doanh: " + "……………………….……………….", normalFont));
            document.add(new Paragraph("Trụ sở: " + (dealerInfo.getLocation() == null ? "…………………………………………" : dealerInfo.getLocation()), normalFont));
            document.add(new Paragraph("Tài khoản số: ………………………………", normalFont));
            document.add(new Paragraph("Điện thoại: " + (dealerInfo.getDealerPhone() == null ? "……………" : dealerInfo.getDealerPhone()), normalFont));
            document.add(new Paragraph("Đại diện: Ông (Bà): " + (dealer.getFullName() == null ? "……………………………." : dealer.getFullName()), normalFont));

            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Sau khi thỏa thuận, hai bên nhất trí và cùng nhau ký kết hợp đồng đại lý thương mại với các điều khoản sau đây:", normalFont));
            document.add(Chunk.NEWLINE);

            // Điều 1: Phạm vi ủy quyền (thay cho điều 1 hiện tại)
            addSection(document, "Điều 1: Phạm vi ủy quyền",
                    "Bên A bổ nhiệm Bên B làm Đại lý ủy quyền bán hàng theo mô hình Agency Model.\n"
                            + "Bên B được phép tiếp nhận, tư vấn khách hàng, hỗ trợ hoàn tất giao dịch giữa Khách hàng và Bên A.\n"
                            + "Bên B không phải là chủ sở hữu hàng hóa và không được tự ý thay đổi giá bán.\n");

            // Điều 2: Chính sách giá & sở hữu sản phẩm
            addSection(document, "Điều 2: Giá bán và sở hữu sản phẩm",
                    "1. Giá bán do Bên A công bố và áp dụng thống nhất trên toàn hệ thống.\n"
                            + "2. Bên B không được tự ý thay đổi giá hoặc cam kết các ưu đãi ngoài chính sách của Bên A.\n"
                            + "3. Sản phẩm thuộc sở hữu của Bên A cho đến khi bàn giao cho khách hàng cuối.");

            // Điều 3: Hoa hồng
            addSection(document, "Điều 3: Hoa hồng và chính sách thưởng",
                    "Bên B được nhận hoa hồng cho mỗi đơn hàng hoàn tất theo chính sách của Bên A. "
                            + "Mức hoa hồng hiện tại là: "
                            + String.format("%.2f%%", dealerInfo.getDealerHierarchy().getCommissionRate())
                            + ". Ngoài ra, Bên B có thể nhận thưởng doanh số theo từng chương trình.");

            // Điều 4: Quy trình bán hàng
            addSection(document, "Điều 4: Quy trình bán hàng",
                    "1. Bên B tiếp nhận khách hàng trực tiếp hoặc từ hệ thống.\n"
                            + "2. Bên B có trách nhiệm cập nhật thông tin khách hàng vào hệ thống của Bên A.\n"
                            + "3. Hợp đồng mua bán được ký giữa Khách hàng và Bên A.\n"
                            + "4. Bên B không được phép thu tiền khách hàng ngoài các khoản được Bên A cho phép.");

            // Điều 5: Trách nhiệm Bên A
            addSection(document, "Điều 5: Trách nhiệm của Bên A",
                    "1. Cung cấp thông tin sản phẩm, chính sách, hệ thống phần mềm.\n"
                            + "2. Trả hoa hồng đúng thời hạn.\n"
                            + "3. Đào tạo nhân viên Bên B.\n"
                            + "4. Giao xe theo hợp đồng ký giữa Bên A và khách hàng.");

            // Điều 6: Trách nhiệm Bên B
            addSection(document, "Điều 6: Trách nhiệm của Bên B",
                    "1. Tuân thủ quy trình bán hàng và giá bán do Bên A ban hành.\n"
                            + "2. Không được thu phí ngoài chính sách.\n"
                            + "3. Bảo mật thông tin khách hàng và thông tin đại lý.\n"
                            + "4. Đạt doanh số theo chính sách từng thời kỳ.");

            // Điều 7: Thời hạn
            addSection(document, "Điều 7: Thời hạn hợp đồng",
                    "Hợp đồng có hiệu lực kể từ ngày ký và kéo dài trong vòng ... tháng/năm. "
                            + "Gia hạn hợp đồng sẽ được thực hiện bằng văn bản.");

            // Điều 8: Chấm dứt hợp đồng
            addSection(document, "Điều 8: Chấm dứt hợp đồng",
                    "Bên A có quyền chấm dứt hợp đồng ngay lập tức nếu Bên B: "
                            + "vi phạm giá bán, thu tiền trái phép, không đạt KPI nhiều kỳ liên tiếp, "
                            + "hoặc gây ảnh hưởng đến uy tín thương hiệu.");

            // Signature table
            PdfPTable signatureTable = new PdfPTable(2);
            signatureTable.setWidthPercentage(100);

            PdfPCell cellA = new PdfPCell();
            cellA.setBorder(Rectangle.NO_BORDER);

            // căn giữa nội dung trong ô
            cellA.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellA.setVerticalAlignment(Element.ALIGN_MIDDLE);

            // dùng Paragraph để căn giữa text
            Paragraph pA = new Paragraph("BÊN A\nChữ ký:\n(Đại diện)", normalFont);
            pA.setAlignment(Element.ALIGN_CENTER);

            cellA.addElement(pA);


            PdfPCell cellB = new PdfPCell();
            cellB.setBorder(Rectangle.NO_BORDER);
            cellB.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellB.setVerticalAlignment(Element.ALIGN_MIDDLE);

            Paragraph pB = new Paragraph("BÊN B\nChữ ký:\n(Đại diện)", normalFont);
            pB.setAlignment(Element.ALIGN_CENTER);

            cellB.addElement(pB);

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
