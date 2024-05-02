package com.freightfox.demo.rest;

import com.freightfox.demo.domain.Invoice;
import com.freightfox.demo.service.PdfGeneratorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/pdf-generator")
@Slf4j
@AllArgsConstructor
public class PdfGeneratorResource {

    private PdfGeneratorService pdfGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generatePdf(@RequestBody Invoice invoice) throws IOException {
        log.info("Request to generate PDF");
        byte[] pdfBytes = pdfGeneratorService.generatePdf(invoice);
        // Save the PDF to local storage here
        // Return the generated PDF bytes in the response
        return createPdfResponse(pdfBytes);
    }

    @GetMapping("/download/{pdfId}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long pdfId) throws IOException {
        log.info("Request to download PDF for ID: {}", pdfId);
        // Retrieve the PDF from local storage based on the PDF ID
        byte[] file = pdfGeneratorService.downloadPdf(pdfId);
        return createPdfResponse(file);
    }

    private ResponseEntity<byte[]> createPdfResponse(byte[] pdfBytes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "generated.pdf");
        headers.setContentLength(pdfBytes.length);
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
