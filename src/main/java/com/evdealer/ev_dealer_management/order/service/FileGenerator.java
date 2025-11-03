package com.evdealer.ev_dealer_management.order.service;

import com.evdealer.ev_dealer_management.order.model.Order;
import com.evdealer.ev_dealer_management.order.repository.OrderRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class FileGenerator {

    private final OrderRepository orderRepository;

    @Value("${file.directory}")
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

        // Create PDF
        Document document = new Document();
        try (FileOutputStream fos = new FileOutputStream(fullPath)) {
            PdfWriter.getInstance(document, fos);
            document.open();
            document.add(new Paragraph("Customer: " + order.getCustomer().getFullName()));
            document.add(new Paragraph("Total Amount: " + order.getTotalAmount()));
            document.add(new Paragraph("Generated at: " + LocalDateTime.now()));
        } finally {
            document.close();
        }

        // Save URL in DB
        order.setQuotationUrl("/files/" + fileName);
        orderRepository.save(order);
    }

    public void generateContract(Order order) throws IOException, DocumentException {
        String fileName = "contract-" + order.getId() + ".pdf";
        String fullPath = uploadDir + File.separator + fileName;

        // Ensure directory exists
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // Create PDF
        Document document = new Document();
        try (FileOutputStream fos = new FileOutputStream(fullPath)) {
            PdfWriter.getInstance(document, fos);
            document.open();
            document.add(new Paragraph("Manager/Dealer: " + order.getStaff().getParent().getFullName()));
            document.add(new Paragraph("Staff: " + order.getStaff().getFullName()));
            document.add(new Paragraph("Customer: " + order.getCustomer().getFullName()));
            document.add(new Paragraph("Signed on: " + LocalDateTime.now().toLocalDate()));
        } finally {
            document.close();
        }

        // Save URL in DB
        order.setContractUrl("/files/" + fileName);
        orderRepository.save(order);
    }
}
