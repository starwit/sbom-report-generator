package de.starwit.sbom.service;

import java.io.OutputStream;

import org.cyclonedx.model.Bom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.sbom.generator.DocumentDesignConfig;
import de.starwit.sbom.generator.ReportGenerator;
import de.starwit.sbom.rest.ReportRequestDTO;

@Service
public class DocumentGeneratorService {
    static final Logger log = LoggerFactory.getLogger(DocumentGeneratorService.class);

    @Autowired
    JSONParser jsonParser;

    @Autowired
    ReportGenerator reportGenerator;

    @Autowired
    DocumentDesignConfigService configService;

    public void generateReport(ReportRequestDTO dto, OutputStream out) {
        Bom bom = jsonParser.parseJsonToBOM(dto.getSbom());
        DocumentDesignConfig dc = configService.getDocumentDesignConfig(dto.getDcId());
        dc.setCompact(dto.isCompact());
        reportGenerator.renderPDF(bom, dc, out);        
    }
    
}
