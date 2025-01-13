package de.starwit.sbom.service;

import java.io.OutputStream;

import org.cyclonedx.model.Bom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.sbom.generator.DocumentDesignConfig;
import de.starwit.sbom.generator.PDFReportGenerator;
import de.starwit.sbom.generator.SpreadSheetGenerator;
import de.starwit.sbom.rest.ReportRequestDTO;
import jakarta.servlet.ServletOutputStream;

@Service
public class DocumentGeneratorService {
    static final Logger log = LoggerFactory.getLogger(DocumentGeneratorService.class);

    @Autowired
    JSONParser jsonParser;

    @Autowired
    PDFReportGenerator reportGenerator;

    @Autowired
    SpreadSheetGenerator sheetGenerator;

    @Autowired
    DocumentDesignConfigService configService;

    public void generateReport(ReportRequestDTO dto, OutputStream out) {
        Bom bom = jsonParser.parseJsonToBOM(dto.getSbom());
        DocumentDesignConfig dc = configService.getDocumentDesignConfig(dto.getDcId());
        dc.setCompact(dto.isCompact());
        reportGenerator.renderPDF(bom, dc, out);        
    }

    public void createSpreadSheetReport(ReportRequestDTO reportData, ServletOutputStream out) {
        JSONParser jp = new JSONParser();
        Bom bom = jp.parseJsonToBOM(reportData.getSbom());
        sheetGenerator.createSpreadSheetReport(bom, out);
    }     
    
}
