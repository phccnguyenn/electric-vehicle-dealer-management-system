package com.evdealer.ev_dealer_management.order.service;

import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.order.model.Order;
import com.evdealer.ev_dealer_management.order.repository.OrderRepository;
import com.evdealer.ev_dealer_management.user.model.Customer;
import com.evdealer.ev_dealer_management.user.model.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@Service
public class FileGenerator {

    private final static String URL_BASE = "http://54.206.57.206:8000/evdealer";
    private final static String FONT_PATH = URL_BASE + "/uploads/arial-font/arial.ttf";
    private final OrderRepository orderRepository;

    @Value("${file.contract}")
    private String uploadDir;

    public FileGenerator(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void generateQuotation(Order order) throws IOException, DocumentException {
        String fileName = "quotation-" + order.getId() + ".pdf";
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

            Paragraph company = new Paragraph("CÔNG TY TNHH VINFAST", boldFont);
            company.setAlignment(Element.ALIGN_CENTER);
            document.add(company);

            Paragraph address = new Paragraph(
                    "Địa chỉ: 123 Nguyễn Văn Linh, Quận 7, TP. HCM\n" +
                            "Điện thoại: (028) 1234 5678 | Email: contact@vinfast.vn\nMST: 0312345678",
                    normalFont
            );
            address.setAlignment(Element.ALIGN_CENTER);
            document.add(address);

            document.add(Chunk.NEWLINE);

            Paragraph title = new Paragraph("BÁO GIÁ XE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            Customer customer = order.getCustomer();
            User staff = order.getStaff();
            document.add(new Paragraph("Khách hàng: " + customer.getFullName(), normalFont));
            document.add(new Paragraph("Số điện thoại: " + customer.getPhone(), normalFont));
            document.add(new Paragraph("Nhân viên tư vấn: " + staff.getFullName(), normalFont));
            document.add(new Paragraph("Ngày báo giá: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), normalFont));
            document.add(Chunk.NEWLINE);

            CarDetail car = order.getCarDetail();
            document.add(new Paragraph("Thông tin xe:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13)));
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            table.addCell(new PdfPCell(new Phrase("Tên xe", boldFont)));
            table.addCell(new PdfPCell(new Phrase(car.getCarName(), normalFont)));

            table.addCell(new PdfPCell(new Phrase("Màu sắc", boldFont)));
            table.addCell(new PdfPCell(new Phrase(car.getColor(), normalFont)));

            if (car.getCarModel() != null) {
                table.addCell(new PdfPCell(new Phrase("Dòng xe", boldFont)));
                table.addCell(new PdfPCell(new Phrase(car.getCarModel().getCarModelName(), normalFont)));
            }

//            if (carDetail.getPerformance() != null) {
//                table.addCell(new PdfPCell(new Phrase("Hiệu suất", boldFont)));
//                table.addCell(new PdfPCell(new Phrase(carDetail.getPerformance().toString(), normalFont)));
//            }

            table.addCell(new PdfPCell(new Phrase("Giá niêm yết", boldFont)));
            table.addCell(new PdfPCell(new Phrase(String.format("%,.0f VNĐ", order.getTotalAmount().doubleValue()), normalFont)));

            document.add(table);

            document.add(Chunk.NEWLINE);
            Font italicFont = new Font(baseFont, 12, Font.ITALIC, BaseColor.GRAY);
            Paragraph note = new Paragraph(
                    "Ghi chú: Báo giá có hiệu lực trong 07 ngày kể từ ngày phát hành.",
                    normalFont
            );
            note.setAlignment(Element.ALIGN_LEFT);
            document.add(note);

            document.add(Chunk.NEWLINE);

            document.add(new Paragraph(
                    "Trân trọng cảm ơn Quý khách!",
                    italicFont
            ));

            document.close();
        }
        order.setQuotationUrl(URL_BASE + fullPath.substring(1).replace("\\", "/"));
        orderRepository.save(order);
    }

//    public void generateContract(Order order) throws IOException, DocumentException {
//        String fileName = "contract-" + order.getId() + ".pdf";
//        String fullPath = uploadDir + File.separator + fileName;
//
//        // Ensure directory exists
//        File dir = new File(uploadDir);
//        if (!dir.exists()) dir.mkdirs();
//
//        // Create PDF
//        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
//        try (FileOutputStream fos = new FileOutputStream(fullPath)) {
//            PdfWriter.getInstance(document, fos);
//            document.open();
//
//            BaseFont baseFont = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//            Font titleFont = new Font(baseFont, 16, Font.BOLD);
//            Font normalFont = new Font(baseFont, 12);
//            Font boldFont = new Font(baseFont, 12, Font.BOLD);
//
//            Paragraph company = new Paragraph("CÔNG TY TNHH VINFAST", boldFont);
//            company.setAlignment(Element.ALIGN_CENTER);
//            document.add(company);
//
//            Paragraph address = new Paragraph(
//                    "Địa chỉ: 123 Nguyễn Văn Linh, Quận 7, TP. HCM\n" +
//                            "Điện thoại: (028) 1234 5678 | Email: contact@vinfast.vn\nMST: 0312345678",
//                    normalFont
//            );
//            address.setAlignment(Element.ALIGN_CENTER);
//            document.add(address);
//            document.add(Chunk.NEWLINE);
//
//            Paragraph title = new Paragraph("HỢP ĐỒNG MUA BÁN XE Ô TÔ", titleFont);
//            title.setAlignment(Element.ALIGN_CENTER);
//            document.add(title);
//            document.add(Chunk.NEWLINE);
//
//            // --- Thông tin hợp đồng ---
//            document.add(new Paragraph("Số hợp đồng: HD-" + order.getId(), normalFont));
//            document.add(new Paragraph("Ngày ký: " + LocalDate.now(), normalFont));
//            document.add(Chunk.NEWLINE);
//
//            // --- Bên bán ---
//            document.add(new Paragraph("BÊN BÁN (BÊN A): CÔNG TY TNHH VINFAST", boldFont));
//            document.add(new Paragraph("Đại diện: " + order.getStaff().getParent().getFullName(), normalFont));
//            document.add(new Paragraph("Nhân viên kinh doanh: " + order.getStaff().getFullName(), normalFont));
//            document.add(Chunk.NEWLINE);
//
//            // --- Bên mua ---
//            document.add(new Paragraph("BÊN MUA (BÊN B): " + order.getCustomer().getFullName(), boldFont));
//            document.add(new Paragraph("Số điện thoại: " + order.getCustomer().getPhone(), normalFont));
//            document.add(Chunk.NEWLINE);
//
//            // --- Thông tin xe ---
//            CarDetail carDetail = order.getCarDetail();
//            document.add(new Paragraph("Thông tin xe:", boldFont));
//
//            PdfPTable table = new PdfPTable(2);
//            table.setWidthPercentage(100);
//            table.setSpacingBefore(10f);
//            table.setSpacingAfter(10f);
//
//            table.addCell("Tên xe");
//            table.addCell(carDetail.getCarName());
//
//            table.addCell("Màu sắc");
//            table.addCell(carDetail.getColor());
//
//            if (carDetail.getCarModel() != null) {
//                table.addCell("Dòng xe");
//                table.addCell(carDetail.getCarModel().getCarModelName());
//            }
//
//            table.addCell("Giá bán");
//            table.addCell(String.format("%,.0f VNĐ", order.getTotalAmount().doubleValue()));
//
//            document.add(table);
//
//            // --- Điều khoản thanh toán ---
//            document.add(new Paragraph("Điều khoản thanh toán:", boldFont));
//            document.add(new Paragraph(
//                    "Bên B thanh toán 30% giá trị xe khi ký hợp đồng. " +
//                            "Phần còn lại được thanh toán trước khi nhận xe.", normalFont));
//            document.add(Chunk.NEWLINE);
//
//            // --- Điều khoản giao xe ---
//            document.add(new Paragraph("Điều khoản giao xe:", boldFont));
//            document.add(new Paragraph(
//                    "Bên A sẽ giao xe trong vòng 07 ngày làm việc kể từ khi nhận đủ thanh toán.", normalFont));
//            document.add(Chunk.NEWLINE);
//
//            // --- Cam kết ---
//            document.add(new Paragraph(
//                    "Hai bên cam kết thực hiện đúng các điều khoản đã thỏa thuận. " +
//                            "Hợp đồng có hiệu lực kể từ ngày ký.", normalFont));
//            document.add(Chunk.NEWLINE);
//
//            // --- Chỗ để ký ở đây nha anh ---
//            PdfPTable signatureTable = new PdfPTable(2);
//            signatureTable.setWidthPercentage(100);
//            signatureTable.addCell(new PdfPCell(new Phrase("ĐẠI DIỆN BÊN A\n(Ký, ghi rõ họ tên)")));
//            signatureTable.addCell(new PdfPCell(new Phrase("ĐẠI DIỆN BÊN B\n(Ký, ghi rõ họ tên)")));
//            document.add(signatureTable);
//
//            document.close();
//        }
//
//        // Save URL in DB
//        order.setContractUrl(URL_BASE + fullPath.substring(1).replace("\\", "/"));
//        orderRepository.save(order);
//    }

    public void generateContract(Order order) throws IOException, DocumentException {
        String fileName = "contract-" + order.getId() + ".pdf";
        String fullPath = uploadDir + File.separator + fileName;

        // Ensure directory exists
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        try (FileOutputStream fos = new FileOutputStream(fullPath)) {
            PdfWriter.getInstance(document, fos);
            document.open();

            // --- Font Unicode hỗ trợ tiếng Việt ---
            BaseFont baseFont = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 16, Font.BOLD);
            Font normalFont = new Font(baseFont, 12);
            Font boldFont = new Font(baseFont, 12, Font.BOLD);
            Font italicFont = new Font(baseFont, 12, Font.ITALIC, BaseColor.GRAY);
            Font noteFont = new Font(baseFont, 12, Font.NORMAL, BaseColor.DARK_GRAY);

            // --- Header công ty ---
            Paragraph company = new Paragraph("CÔNG TY TNHH VINFAST", boldFont);
            company.setAlignment(Element.ALIGN_CENTER);
            document.add(company);

            Paragraph address = new Paragraph(
                    "Địa chỉ: 123 Nguyễn Văn Linh, Quận 7, TP. HCM\n" +
                            "Điện thoại: (028) 1234 5678 | Email: contact@vinfast.vn\nMST: 0312345678",
                    normalFont
            );
            address.setAlignment(Element.ALIGN_CENTER);
            document.add(address);
            document.add(Chunk.NEWLINE);

            Paragraph title = new Paragraph("HỢP ĐỒNG MUA BÁN XE Ô TÔ", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // --- Thông tin hợp đồng ---
            document.add(new Paragraph("Số hợp đồng: HD-" + order.getId(), normalFont));
            document.add(new Paragraph("Ngày ký: " +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), normalFont));
            document.add(Chunk.NEWLINE);

            // --- Bên bán ---
            document.add(new Paragraph("BÊN BÁN (BÊN A): CÔNG TY TNHH VINFAST", boldFont));
            document.add(new Paragraph("Đại diện: " + order.getStaff().getParent().getFullName(), normalFont));
            document.add(new Paragraph("Nhân viên kinh doanh: " + order.getStaff().getFullName(), normalFont));
            document.add(Chunk.NEWLINE);

            // --- Bên mua ---
            document.add(new Paragraph("BÊN MUA (BÊN B): " + order.getCustomer().getFullName(), boldFont));
            document.add(new Paragraph("Số điện thoại: " + order.getCustomer().getPhone(), normalFont));
            document.add(Chunk.NEWLINE);

            // --- Thông tin xe ---
            CarDetail car = order.getCarDetail();
            document.add(new Paragraph("Thông tin xe:", boldFont));

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            table.addCell(new PdfPCell(new Phrase("Tên xe", boldFont)));
            table.addCell(new PdfPCell(new Phrase(car.getCarName(), normalFont)));

            table.addCell(new PdfPCell(new Phrase("Màu sắc", boldFont)));
            table.addCell(new PdfPCell(new Phrase(car.getColor(), normalFont)));

            if (car.getCarModel() != null) {
                table.addCell(new PdfPCell(new Phrase("Dòng xe", boldFont)));
                table.addCell(new PdfPCell(new Phrase(car.getCarModel().getCarModelName(), normalFont)));
            }

            table.addCell(new PdfPCell(new Phrase("Giá bán", boldFont)));
            table.addCell(new PdfPCell(new Phrase(String.format("%,.0f VNĐ", order.getTotalAmount().doubleValue()), normalFont)));

            document.add(table);

            // --- Điều khoản thanh toán ---
            document.add(new Paragraph("Điều khoản thanh toán:", boldFont));
            document.add(new Paragraph(
                    "Bên B thanh toán 30% giá trị xe khi ký hợp đồng. Phần còn lại được thanh toán trước khi nhận xe.",
                    normalFont
            ));
            document.add(Chunk.NEWLINE);

            // --- Điều khoản giao xe ---
            document.add(new Paragraph("Điều khoản giao xe:", boldFont));
            document.add(new Paragraph(
                    "Bên A sẽ giao xe trong vòng 07 ngày làm việc kể từ khi nhận đủ thanh toán.",
                    normalFont
            ));
            document.add(Chunk.NEWLINE);

            // --- Cam kết ---
            document.add(new Paragraph(
                    "Hai bên cam kết thực hiện đúng các điều khoản đã thỏa thuận. Hợp đồng có hiệu lực kể từ ngày ký.",
                    normalFont
            ));
            document.add(Chunk.NEWLINE);

            // --- Bảng ký ---
            PdfPTable signatureTable = new PdfPTable(2);
            signatureTable.setWidthPercentage(100);
            signatureTable.addCell(new PdfPCell(new Phrase("ĐẠI DIỆN BÊN A\n(Ký, ghi rõ họ tên)", normalFont)));
            signatureTable.addCell(new PdfPCell(new Phrase("ĐẠI DIỆN BÊN B\n(Ký, ghi rõ họ tên)", normalFont)));
            document.add(signatureTable);

            document.close();
        }

        // Save URL in DB
        order.setContractUrl(URL_BASE + fullPath.substring(1).replace("\\", "/"));
        orderRepository.save(order);
    }

}
