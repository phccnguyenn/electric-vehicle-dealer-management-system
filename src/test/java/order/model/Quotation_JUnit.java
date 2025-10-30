package order.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class QuotationTest_JUnit {

    private Quotation quotation;
    private QuotationItem item1;
    private QuotationItem item2;

    @BeforeEach
    void setUp() {
        quotation = new Quotation();
        item1 = new QuotationItem();
        item2 = new QuotationItem();
    }

    @Test
    void subTotal_emptyIsZero() {
        assertEquals(0, BigDecimal.ZERO.compareTo(quotation.getSubTotal()));
    }

    @Test
    void subTotal_withItems_sumsLineTotals() {
        // Set up item1: lineTotal = 100.00
        item1.setQuantity(2);
        item1.setUnitPrice(new BigDecimal("50.00"));
        item1.setLineDiscount(BigDecimal.ZERO);
        item1.computeLineTotal(); // Phải gọi thủ công vì không có database

        // Set up item2: lineTotal = 200.50
        item2.setQuantity(1);
        item2.setUnitPrice(new BigDecimal("200.50"));
        item2.setLineDiscount(BigDecimal.ZERO);
        item2.computeLineTotal(); // Phải gọi thủ công vì không có database

        quotation.addItem(item1);
        quotation.addItem(item2);

        // subtotal = 300.50
        BigDecimal expected = new BigDecimal("300.50");
        assertEquals(0, expected.compareTo(quotation.getSubTotal()));
    }

    @Test
    void addItem_setsBackReference() {
        item1.setQuantity(1);
        item1.setUnitPrice(new BigDecimal("100.00"));

        quotation.addItem(item1);

        // verify back-reference set during add
        assertSame(quotation, item1.getQuotation());
    }

    @Test
    void discountTaxAndTotal_computedCorrectly() {
        // Set up item: lineTotal = 1000.00
        item1.setQuantity(10);
        item1.setUnitPrice(new BigDecimal("100.00"));
        item1.setLineDiscount(BigDecimal.ZERO);  // THÊM DÒNG NÀY
        item1.computeLineTotal();                 // THÊM DÒNG NÀY
        quotation.addItem(item1);

        // 10% discount, 5% tax
        quotation.setDiscountPercent(new BigDecimal("10"));
        quotation.setTaxPercent(new BigDecimal("5"));

        BigDecimal subtotal = new BigDecimal("1000.00");
        BigDecimal expectedDiscount = new BigDecimal("100.00"); // 10% of 1000
        BigDecimal expectedTax = new BigDecimal("45.00"); // (1000 - 100) * 5% = 45
        BigDecimal expectedTotal = new BigDecimal("945.00"); // 1000 - 100 + 45

        assertEquals(0, subtotal.compareTo(quotation.getSubTotal()));
        assertEquals(0, expectedDiscount.compareTo(quotation.getDiscountComputed()));
        assertEquals(0, expectedTax.compareTo(quotation.getTaxComputed()));
        assertEquals(0, expectedTotal.compareTo(quotation.getTotal()));
    }
}
