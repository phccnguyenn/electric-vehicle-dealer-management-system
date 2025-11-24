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
            document.add(new Paragraph("BÊN GIAO ĐẠI LÝ (gọi tắt là Bên A): " + (manufacturer.getFullName() == null ? "………………" : manufacturer.getFullName()), boldFont));
            document.add(new Paragraph("Giấy phép Đăng ký Kinh doanh: " + "…………………………..………………….", normalFont));
            document.add(new Paragraph("Trụ sở: " + (manufacturer.getAddress() == null ? "…………………………………………" : manufacturer.getAddress()), normalFont));
            document.add(new Paragraph("Tài khoản số: ………………………………", normalFont));
            document.add(new Paragraph("Điện thoại: " + (manufacturer.getPhone() == null ? "……………" : manufacturer.getPhone()) + "    Fax: " + "………………………..", normalFont));
            document.add(new Paragraph("Đại diện: Ông (Bà): " + (manufacturer.getFullName() == null ? "…………………………………….." : manufacturer.getFullName()), normalFont));
            document.add(Chunk.NEWLINE);

            // Bên B - Bên đại lý
            document.add(new Paragraph("BÊN ĐẠI LÝ (gọi tắt là Bên B): " + (dealerInfo.getDealerName() == null ? "……………." : dealerInfo.getDealerName()), boldFont));
            document.add(new Paragraph("Giấy phép Đăng ký Kinh doanh: " + "……………………….……………….", normalFont));
            document.add(new Paragraph("Trụ sở: " + (dealerInfo.getLocation() == null ? "…………………………………………" : dealerInfo.getLocation()), normalFont));
            document.add(new Paragraph("Tài khoản số: ………………………………", normalFont));
            document.add(new Paragraph("Điện thoại: " + (dealerInfo.getDealerPhone() == null ? "……………" : dealerInfo.getDealerPhone()) + "    Fax: " + "………………………….", normalFont));
            document.add(new Paragraph("Đại diện: Ông (Bà): " + (dealer.getFullName() == null ? "……………………………." : dealer.getFullName()), normalFont));

            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Sau khi thỏa thuận, hai bên nhất trí và cùng nhau ký kết hợp đồng đại lý thương mại với các điều khoản sau đây:", normalFont));
            document.add(Chunk.NEWLINE);

            // Điều 1: Nội dung
            addSection(document, "Điều 1: Nội dung của hợp đồng",
                    "Bên B nhận làm đại lý cho Bên A các sản phẩm: xe điện, mang nhãn hiệu: EVD. Bên B tự trang bị cơ sở vật chất, địa điểm kinh doanh, kho bãi và hoàn toàn chịu trách nhiệm tất cả hàng hóa đã giao trong việc tồn trữ, trưng bày, vận chuyển. Bên B bảo đảm thực hiện đúng các biện pháp tồn trữ, giữ được phẩm chất hàng hóa như Bên A đã cung cấp, đến khi giao cho người tiêu thụ. Bên A không chấp nhận hoàn trả hàng hóa do bất kỳ lý do gì (ngoại trừ trường hợp có sai sót về sản phẩm).");

            // Điều 2: Quyền và nghĩa vụ bên A
            addSection(document, "Điều 2: Quyền và nghĩa vụ của bên giao đại lý (Bên A)",
                    "1. Quyền của bên A:\n\n- Ấn định giá giao đại lý;\n\n- Yêu cầu bên B thực hiện biện pháp bảo đảm theo quy định pháp luật;\n\n- Yêu cầu bên B thanh toán tiền theo hợp đồng đại lý;\n\n- Kiểm tra, giám sát việc thực hiện hợp đồng của bên đại lý.\n\n2. Nghĩa vụ của bên A:\n\n- Giao hàng cho bên B đúng thời gian và địa điểm đã thỏa thuận trong hợp đồng này;\n\n- Hướng dẫn, cung cấp thông tin, tạo điều kiện cho bên B thực hiện hợp đồng đại lý;\n\n- Chịu trách nhiệm về chất lượng của sản phẩm;\n\n- Hoàn trả cho bên B tài sản đảm bảo (nếu có) khi kết thúc hợp đồng đại lý;\n\n- Liên đới chịu trách nhiệm về hành vi vi phạm của bên B nếu nguyên nhân vi phạm có lỗi của bên A.");

            // Điều 3: Quyền và nghĩa vụ bên B
            addSection(document, "Điều 3: Quyền và nghĩa vụ của bên đại lý (Bên B)",
                    "1. Quyền của bên B:\n\n- Có quyền ấn định giá bán mặt hàng trên thị trường;\n\n- Yêu cầu bên A giao hàng theo hợp đồng đại lý; nhận tài sản dùng để bảo đảm khi kết thúc hợp đồng;\n\n- Yêu cầu bên A hướng dẫn, cung cấp thông tin và các điều kiện khác có liên quan để thực hiện hợp đồng;\n\n- Được là chủ sở hữu của hàng hóa.\n\n2. Nghĩa vụ của bên B:\n\n- Thực hiện đúng các thỏa thuận về giao tiền, nhận hàng với bên A;\n\n- Thực hiện các biện pháp bảo đảm thực hiện nghĩa vụ dân sự theo quy định của pháp luật;\n\n- Liên đới chịu trách nhiệm về chất lượng hàng hóa trong trường hợp do lỗi của mình gây ra.");

            // Điều 4: Phương thức giao nhận hàng
            addSection(document, "Điều 4: Phương thức giao nhận hàng",
                    "1. Bên A giao hàng đến cửa kho của Bên B hoặc tại địa điểm thuận tiện do Bên B chỉ định. Bên B đặt hàng với số lượng, loại sản phẩm cụ thể bằng thư, fax, điện tín.\n\n2. Chi phí xếp dỡ từ xe vào kho của Bên B do Bên B chi trả (kể cả chi phí lưu xe do xếp dỡ chậm).\n\n3. Số lượng hàng hóa thực tế Bên A cung cấp cho bên B có thể chênh lệch với đơn đặt hàng nếu Bên A xét thấy đơn đặt hàng đó không hợp lý. Khi đó hai bên phải có sự thỏa thuận về khối lượng, thời gian cung cấp.\n\n4. Thời gian giao hàng: ………………………");

            // Điều 5: Phương thức thanh toán
            addSection(document, "Điều 5: Phương thức thanh toán",
                    "1. Bên B thanh toán cho Bên A tương ứng với giá trị số lượng hàng giao ghi trong mỗi hóa đơn trong vòng … ngày kể từ ngày cuối của tháng Bên B đặt hàng.\n\n2. Giới hạn mức nợ: Bên B được nợ tối đa là …………………..\n\n3. Thời điểm thanh toán được tính là ngày Bên A nhận được tiền.\n\n4. Số tiền chậm trả ngoài thời gian đã quy định, phải chịu lãi theo mức lãi suất cho vay của ngân hàng trong cùng thời điểm. Nếu việc chậm trả kéo dài hơn 3 tháng thì bên B phải chịu thêm lãi suất quá hạn của ngân hàng.\n\n5. Trong trường hợp cần thiết, Bên A có thể yêu cầu Bên B thế chấp tài sản để bảo đảm cho việc thanh toán.");

            // Điều 6: Giá cả
            addSection(document, "Điều 6: Giá cả",
                    "1. Các sản phẩm cung cấp cho Bên B được tính theo giá bán sỉ, do Bên A công bố thống nhất trong khu vực.\n\n2. Giá cung cấp có thể thay đổi theo thời gian và Bên A sẽ thông báo trước cho Bên B ít nhất là … ngày.\n\nTỷ lệ hoa hồng: …………………………………………….");

            // Điều 7: Bảo hành
            addSection(document, "Điều 7: Bảo hành",
                    "1. Bên A bảo hành riêng biệt cho từng sản phẩm cung cấp cho bên B trong trường hợp bên B tiến hành việc tồn trữ, vận chuyển, hướng dẫn sử dụng và giám sát, nghiệm thu đúng với nội dung đã huấn luyện và phổ biến của bên A.\n\nThời gian bảo hành là …. tháng tính từ thời điểm bên A giao hàng cho bên B.\n\n2. Bên A chịu trách nhiệm bảo hành đối với bên thứ 3 (khách hàng) đối với các lỗi liên quan đến yếu tố kỹ thuật của máy. Nếu lỗi có lỗi của bên B thì bên A và B sẽ liên đới cùng chịu trách nhiệm.\n\nThời gian bảo hành đối với bên thứ 3 là…. tháng kể từ thời điểm chuyển giao sản phẩm");

            // Điều 8: Hỗ trợ
            addSection(document, "Điều 8: Hỗ trợ",
                    "1. Bên A cung cấp cho Bên B các tư liệu thông tin khuếch trương thương mại.\n\n2. Bên A hướng dẫn cho nhân viên của Bên B những kỹ thuật cơ bản để có thể thực hiện việc bảo quản đúng cách.\n\n3. Mọi hoạt động quảng cáo do Bên B tự thực hiện, nếu có sử dụng đến logo hay nhãn hiệu hàng hóa của Bên A phải được sự đồng ý của Bên A.");

            // Điều 9: Độc quyền
            addSection(document, "Điều 9: Độc quyền",
                    "– Hợp đồng này …… mang tính độc quyền trên khu vực.");

            // Điều 10: Thời hạn
            addSection(document, "Điều 10: Thời hạn hiệu lực, kéo dài và chấm dứt hợp đồng",
                    "Hợp đồng này có giá trị kể từ ngày ký đến hết ngày………………. Nếu cả hai bên mong muốn tiếp tục hợp đồng, các thủ tục gia hạn phải được thỏa thuận trước khi hết hạn hợp đồng trong thời gian tối thiểu là … ngày.\n\nTrong thời gian hiệu lực, một bên có thể đơn phương chấm dứt hợp đồng nhưng phải báo trước cho Bên kia biết trước tối thiểu là … ngày.\n\nBên A có quyền đình chỉ ngay hợp đồng khi Bên B vi phạm một trong các vấn đề sau đây: ...");

            // Điều 11: Bồi thường
            addSection(document, "Điều 11: Bồi thường thiệt hại",
                    "1. Phạt vi phạm hợp đồng: Nếu một trong các bên vi phạm nghĩa vụ quy định tại điều 2 và 3 hợp đồng này thì phải chịu mức phạt vi phạm là …. giá trị phần nghĩa vụ vi phạm.\n\n2. Bồi thường thiệt hại: ...");

            // Điều 12: Tranh chấp
            addSection(document, "Điều 12: Xử lý tranh chấp phát sinh",
                    "Trong khi thực hiện nếu có vấn đề phát sinh hai bên cùng nhau bàn bạc thỏa thuận giải quyết. Những chi tiết không ghi cụ thể trong hợp đồng này, nếu có xảy ra, sẽ thực hiện theo quy định chung của Luật Thương mại và pháp luật hiện hành.\n\nNếu hai bên không tự giải quyết được, việc tranh chấp sẽ được phân xử tại ……….\n\nQuyết định của ……. là cuối cùng mà các bên phải thi hành. Phí ……. sẽ do bên có lỗi chịu trách nhiệm thanh toán.\n\nHợp đồng này được lập thành………bản, mỗi bên giữ……..bản có giá trị như nhau.");

            // Signature table
            PdfPTable signatureTable = new PdfPTable(2);
            signatureTable.setWidthPercentage(100);

            PdfPCell cellA = new PdfPCell();
            cellA.setBorder(Rectangle.NO_BORDER);
            cellA.addElement(new Phrase("BÊN A\nChữ ký:\n(Đại diện)", normalFont));

            PdfPCell cellB = new PdfPCell();
            cellB.setBorder(Rectangle.NO_BORDER);
            cellB.addElement(new Phrase("BÊN B\nChữ ký:\n(Đại diện)", normalFont));

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
