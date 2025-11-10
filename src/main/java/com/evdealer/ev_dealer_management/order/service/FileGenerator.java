package com.evdealer.ev_dealer_management.order.service;

import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.order.model.Order;
import com.evdealer.ev_dealer_management.order.repository.OrderRepository;
import com.evdealer.ev_dealer_management.user.model.Customer;
import com.evdealer.ev_dealer_management.user.model.User;
import com.itextpdf.text.*;
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

    private final static String URL_BASE = "http://localhost:8000/evdealer";
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

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

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
            document.add(new Paragraph("Khách hàng: " + customer.getFullName()));
            document.add(new Paragraph("Số điện thoại: " + customer.getPhone()));
            document.add(new Paragraph("Nhân viên tư vấn: " + staff.getFullName()));
            document.add(new Paragraph("Ngày báo giá: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            document.add(Chunk.NEWLINE);

            CarDetail car = order.getCarDetail();
            document.add(new Paragraph("Thông tin xe:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13)));
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            table.addCell("Tên xe");
            table.addCell(car.getCarName());

            table.addCell("Màu sắc");
            table.addCell(car.getColor());

            if (car.getCarModel() != null) {
                table.addCell("Dòng xe");
                table.addCell(car.getCarModel().getCarModelName());
            }

            if (car.getPerformance() != null) {
                table.addCell("Hiệu suất");
                table.addCell(car.getPerformance().toString());
            }
            table.addCell("Giá niêm yết");
            table.addCell(String.format("%,.0f VNĐ", order.getTotalAmount().doubleValue()));

            document.add(table);

            document.add(Chunk.NEWLINE);
            Paragraph note = new Paragraph("Ghi chú: Báo giá có hiệu lực trong 07 ngày kể từ ngày phát hành.",
                    FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.DARK_GRAY));
            note.setAlignment(Element.ALIGN_LEFT);
            document.add(note);

            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Trân trọng cảm ơn Quý khách!",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12, BaseColor.GRAY)));

            document.close();
        }
    }
    public void generateContract(Order order) throws IOException, DocumentException {
        String fileName = "contract-" + order.getId() + ".pdf";
        String fullPath = uploadDir + File.separator + fileName;

        // Ensure directory exists
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // Create PDF
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try (FileOutputStream fos = new FileOutputStream(fullPath)) {
            PdfWriter.getInstance(document, fos);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

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
            document.add(new Paragraph("Ngày ký: " + LocalDate.now(), normalFont));
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

            table.addCell("Tên xe");
            table.addCell(car.getCarName());

            table.addCell("Màu sắc");
            table.addCell(car.getColor());

            if (car.getCarModel() != null) {
                table.addCell("Dòng xe");
                table.addCell(car.getCarModel().getCarModelName());
            }

            table.addCell("Giá bán");
            table.addCell(String.format("%,.0f VNĐ", order.getTotalAmount().doubleValue()));

            document.add(table);

            // --- Điều khoản thanh toán ---
            document.add(new Paragraph("Điều khoản thanh toán:", boldFont));
            document.add(new Paragraph(
                    "Bên B thanh toán 30% giá trị xe khi ký hợp đồng. " +
                            "Phần còn lại được thanh toán trước khi nhận xe.", normalFont));
            document.add(Chunk.NEWLINE);

            // --- Điều khoản giao xe ---
            document.add(new Paragraph("Điều khoản giao xe:", boldFont));
            document.add(new Paragraph(
                    "Bên A sẽ giao xe trong vòng 07 ngày làm việc kể từ khi nhận đủ thanh toán.", normalFont));
            document.add(Chunk.NEWLINE);

            // --- Cam kết ---
            document.add(new Paragraph(
                    "Hai bên cam kết thực hiện đúng các điều khoản đã thỏa thuận. " +
                            "Hợp đồng có hiệu lực kể từ ngày ký.", normalFont));
            document.add(Chunk.NEWLINE);

            // --- Chỗ để ký ở đây nha anh ---
            PdfPTable signatureTable = new PdfPTable(2);
            signatureTable.setWidthPercentage(100);
            signatureTable.addCell(new PdfPCell(new Phrase("ĐẠI DIỆN BÊN A\n(Ký, ghi rõ họ tên)")));
            signatureTable.addCell(new PdfPCell(new Phrase("ĐẠI DIỆN BÊN B\n(Ký, ghi rõ họ tên)")));
            document.add(signatureTable);

            document.close();
        }

        // Save URL in DB
        order.setContractUrl(URL_BASE + fullPath.substring(1).replace("\\", "/"));
        orderRepository.save(order);
    }
}
