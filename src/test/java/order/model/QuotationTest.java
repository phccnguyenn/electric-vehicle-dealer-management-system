package order.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuotationTest {

    private Quotation quotation;

    @Mock
    private QuotationItem item1;

    @Mock
    private QuotationItem item2;

    @BeforeEach
    void setUp() {
        quotation = new Quotation();
    }

    @Test
    void subTotal_emptyIsZero() {
        assertEquals(0, BigDecimal.ZERO.compareTo(quotation.getSubTotal()));
    }

    @Test
    void subTotal_withItems_sumsLineTotalsAndSetsBackReference() {
        when(item1.getLineTotal()).thenReturn(new BigDecimal("100.00"));
        when(item2.getLineTotal()).thenReturn(new BigDecimal("200.50"));

        quotation.addItem(item1);
        quotation.addItem(item2);

        // subtotal = 300.50
        BigDecimal expected = new BigDecimal("300.50");
        assertEquals(0, expected.compareTo(quotation.getSubTotal()));

        // verify back-reference set during add
        verify(item1).setQuotation(quotation);
        verify(item2).setQuotation(quotation);

        // remove item1 and verify it's removed and back-reference cleared
        quotation.removeItem(item1);
        assertEquals(1, quotation.getItems().size());
        verify(item1).setQuotation(null);
    }

    @Test
    void discountTaxAndTotal_computedCorrectly() {
        when(item1.getLineTotal()).thenReturn(new BigDecimal("1000.00"));
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
