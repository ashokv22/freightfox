package com.freightfox.demo.rest;

import com.freightfox.demo.domain.Invoice;
import com.freightfox.demo.service.PdfGeneratorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PdfGeneratorResourceTest {

    @Mock
    private PdfGeneratorService pdfGeneratorService;

    @InjectMocks
    private PdfGeneratorResource pdfGeneratorResource;

    @Test
    public void testGeneratePdf() throws IOException {
        // Mock Invoice
        Invoice invoice = new Invoice();
        invoice.setBuyerGstin("buyerGstin");
        invoice.setSellerGstin("sellerGstin");
        byte[] pdfBytes = "mockPdfBytes".getBytes();

        when(pdfGeneratorService.generatePdf(any(Invoice.class))).thenReturn(pdfBytes);
        ResponseEntity<byte[]> response = pdfGeneratorResource.generatePdf(invoice);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_PDF, response.getHeaders().getContentType());
        assertEquals("generated.pdf", response.getHeaders().getContentDisposition().getFilename());
        assertEquals(pdfBytes.length, Objects.requireNonNull(response.getBody()).length);
    }

    @Test
    public void testDownloadPdf() throws IOException {
        // Mock PDF ID
        Long pdfId = 1L;

        byte[] pdfBytes = "mockPdfBytes".getBytes();
        when(pdfGeneratorService.downloadPdf(pdfId)).thenReturn(pdfBytes);

        ResponseEntity<byte[]> response = pdfGeneratorResource.downloadPdf(pdfId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_PDF, response.getHeaders().getContentType());
        assertEquals("generated.pdf", response.getHeaders().getContentDisposition().getFilename());
        assertEquals(pdfBytes.length, Objects.requireNonNull(response.getBody()).length);
    }

}
