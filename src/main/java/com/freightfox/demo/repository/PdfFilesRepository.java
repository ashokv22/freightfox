package com.freightfox.demo.repository;

import com.freightfox.demo.domain.PdfFiles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PdfFilesRepository extends JpaRepository<PdfFiles, Long> {

    Optional<PdfFiles> findBySellerGstinAndBuyerGstin(String buyerGstin, String buyerGstin1);

}
