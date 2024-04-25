package com.freightfox.demo.service;

import com.freightfox.demo.domain.Invoice;

import java.io.IOException;

public interface PdfGeneratorService {

    byte[] generatePdf(Invoice invoice) throws IOException;

    byte[] downloadPdf(Long pdfId) throws IOException;

}
