package com.freightfox.demo.service.impl;

import com.freightfox.demo.domain.Invoice;
import com.freightfox.demo.domain.PdfFiles;
import com.freightfox.demo.repository.PdfFilesRepository;
import com.freightfox.demo.service.PdfGeneratorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

    private PdfFilesRepository pdfFilesRepository;

    private TemplateEngine templateEngine;

    @Override
    public byte[] generatePdf(Invoice invoice) throws IOException {
        Optional<PdfFiles> existingPdf = pdfFilesRepository.findBySellerGstinAndBuyerGstin(invoice.getSellerGstin(), invoice.getBuyerGstin());
        if (existingPdf.isPresent()) {
            return loadFile(existingPdf.get().getFilePath());
        } else {
            byte[] file = generateFile(invoice);
            // If the file doesn't exist, create a new PDF file
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            String fileName = invoice.getBuyerGstin() + "_" + invoice.getSellerGstin() + ".pdf";
            String filePath = desktopPath + fileName;

            // Save the PDF file to the Desktop
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(file);
            }

            PdfFiles pdfFile = new PdfFiles();
            pdfFile.setBuyerGstin(invoice.getBuyerGstin());
            pdfFile.setSellerGstin(invoice.getSellerGstin());
            pdfFile.setFilePath(filePath);
            PdfFiles savedPdf = pdfFilesRepository.save(pdfFile);
            return loadFile(savedPdf.getFilePath());
        }
    }

    private byte[] generateFile(Invoice invoice) {
        // Create a Thymeleaf context and add the invoice object to it
        Context context = new Context();
        context.setVariable("invoice", invoice);

        // Process the Thymeleaf HTML template with the context
        String htmlContent = templateEngine.process("invoice.html", context);

        // Use flying-saucer-pdf library to convert HTML to PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(outputStream);

        // Return the generated PDF as a byte array
        return outputStream.toByteArray();
    }

    @Override
    public byte[] downloadPdf(Long pdfId) throws IOException {
        Optional<PdfFiles> optionalPdfFile = pdfFilesRepository.findById(pdfId);
        if (optionalPdfFile.isPresent()) {
            return loadFile(optionalPdfFile.get().getFilePath());
        } else {
            throw new IOException("PDF file not found");
        }
    }

    private byte[] loadFile(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }
}
