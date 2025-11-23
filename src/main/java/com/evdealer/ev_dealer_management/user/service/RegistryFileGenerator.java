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

        String fileName = "registry-contract-" + dealer.getFullName() + ".pdf";
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

            // --- Header ---
            Paragraph header = new Paragraph("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM\nĐộc lập – Tự do – Hạnh phúc", boldFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(Chunk.NEWLINE);

            Paragraph contractTitle = new Paragraph("HỢP ĐỒNG ĐẠI LÝ ỦY QUYỀN", titleFont);
            contractTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(contractTitle);
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph(
                    "- Căn cứ Bộ luật dân sự 2015;\n" +
                    "- Căn cứ Luật Thương mại 2005;\n" +
                    "- Dựa vào khả năng và nhu cầu của hai bên;\n" +
                    "- Dựa trên tinh thần trung thực và thiện chí hợp tác của các bên;", normalFont));

            document.add(new Paragraph(
                    "Hôm nay, ngày " + OffsetDateTime.now().getDayOfMonth()
                    + " tháng " + OffsetDateTime.now().getMonth()
                    + " năm " + OffsetDateTime.now().getYear()
                    + ". Chúng tôi gồm có:", normalFont));

            // --- Contract parties ---
            document.add(new Paragraph("BÊN GIAO ĐẠI LÝ (Bên A): CÔNG TY TNHH EVD", boldFont));
            document.add(new Paragraph("Người đại diện: " + manufacturer.getFullName(), normalFont));
            document.add(new Paragraph("Nhân viên kinh doanh: " + manufacturer.getRole(), normalFont));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("BÊN ĐẠI LÝ (Bên B): " + dealerInfo.getDealerName(), boldFont));
            document.add(new Paragraph("Đại diện: " + dealer.getFullName(), normalFont));
            document.add(Chunk.NEWLINE);

            // --- Contract details ---
            document.add(new Paragraph("Hai bên thống nhất thoả thuận nội dung hợp đồng như sau:", normalFont));
            document.add(new Paragraph("Điều 1: Điều khoản chung", boldFont));
            document.add(new Paragraph("Bên B nhận làm đại lý ủy quyền cho bên A sản phẩm\n" +
                    "Bên B tự trang bị cơ sở vật chất, địa điểm kinh doanh, kho bãi và hoàn toàn chịu trách nhiệm tất cả hàng " +
                    "hóa đã giao trong việc tồn trữ, trưng bày, vận chuyển. Bên B bảo đảm thực hiện đúng các biện pháp tổn trữ, " +
                    "giữ được phẩm chất hàng hóa như bên A đã cung cấp, đến khi giao cho người tiêu thụ." +
                    "Bên A không chấp nhận hoàn trả hàng hóa do bất kỳ lý do gì " +
                    "(ngoại trừ trường hợp có sai sót về lỗi kỹ thuật của sản phẩm).", normalFont));

            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Điều 2: Quyền và nghĩa vụ của Bên A", boldFont));
            document.add(new Paragraph("2.1 Quyền của Bên A:", normalFont));
            document.add(new Paragraph("- Ấn định giá giao đại lý;", normalFont));
            document.add(new Paragraph("- Yêu cầu bên B thực hiện biện pháp bảo đảm theo quy định pháp luật", normalFont));
            document.add(new Paragraph("- Yêu cầu bên B thanh toán tiền theo hợp đồng đại lý", normalFont));
            document.add(new Paragraph("- Kiểm tra, giám sát việc thực hiện hợp đồng của bên đại lý.", normalFont));
            document.add(new Paragraph("2.2 Nghĩa vụ của Bên A:", normalFont));
            document.add(new Paragraph("- Giao hàng cho bên B đúng thời gian và địa điểm ghi đã thỏa thuận trong hợp đồng này", normalFont));
            document.add(new Paragraph("- Hướng dẫn, cung cấp thông tin, tạo điều kiện cho bên B thực hiện hợp đồng đại lý;", normalFont));
            document.add(new Paragraph("- Chịu trách nhiệm về chất lượng của sản phẩm;", normalFont));
            document.add(new Paragraph("- Hoàn trả cho bên B tài sản đảm bảo (nếu có) khi kết thúc hợp đồng đại lý;", normalFont));
            document.add(new Paragraph("- Liên đới chịu trách nhiệm về hành vi vi phạm của bên B nếu nguyên nhân vi phạm có lối của bên A", normalFont));

            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Điều 3: Quyền và nghĩa vụ của Bên B", boldFont));
            document.add(new Paragraph("3.1 Quyền của Bên B:", normalFont));
            document.add(new Paragraph("- Có quyền ấn định giá bán mặt hàng từ thị trường;", normalFont));
            document.add(new Paragraph("- Yêu cầu bên A giao hàng theo hợp đồng đại lý; nhận tài sản dùng để bảo đảm khi kết thúc hợp đồng", normalFont));
            document.add(new Paragraph("- Yêu cầu bên A hướng dẫn, cung cấp thông tin và các điều kiện khác có liên quan đề thực hiện hợp đồng", normalFont));
            document.add(new Paragraph("- Được là chủ sở hữu của hàng hóa", normalFont));

            document.add(new Paragraph("3.2 Nghĩa vụ của Bên B: ...", normalFont));
            document.add(new Paragraph("- Thực hiện đúng các thỏa thuận về giao tiền, nhận hàng với bên A;", normalFont));
            document.add(new Paragraph("- Thực hiện các biện pháp bảo đảm thực hiện nghĩa vụ dân sự theo quy định của pháp luật;", normalFont));
            document.add(new Paragraph("- Liên đới chịu trách nhiệm về chất lượng hàng hóa trong trường hợp do lỗi của mình gây ra", normalFont));

            document.add(Chunk.NEWLINE);

            // --- Signatures ---
            PdfPTable signatureTable = new PdfPTable(2);
            signatureTable.setWidthPercentage(100);
            signatureTable.addCell(new PdfPCell(new Phrase("ĐẠI DIỆN BÊN A\n(Ký, ghi rõ họ tên)", normalFont)));
            signatureTable.addCell(new PdfPCell(new Phrase("ĐẠI DIỆN BÊN B\n(Ký, ghi rõ họ tên)", normalFont)));
            document.add(signatureTable);

            document.close();
        }

        // Save URL in DB
        dealerInfo.setContractFileUrl(URL_BASE + fullPath.substring(1).replace("\\", "/"));
        return dealerInfoRepository.save(dealerInfo);
    }

}
