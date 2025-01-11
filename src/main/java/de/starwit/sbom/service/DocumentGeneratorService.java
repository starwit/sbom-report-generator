package de.starwit.sbom.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.cyclonedx.model.Bom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.sbom.generator.DocumentDesignConfig;
import de.starwit.sbom.generator.ReportGenerator;

@Service
public class DocumentGeneratorService {
    static final Logger log = LoggerFactory.getLogger(DocumentGeneratorService.class);

    @Autowired
    JSONParser jsonParser;

    @Autowired
    ReportGenerator reportGenerator;

    @Autowired
    DocumentDesignConfigService configService;

    public void generateReport(String bomJson, int dcId, OutputStream out) {
        Bom bom = jsonParser.parseJsonToBOM(bomJson);
        reportGenerator.renderPDF(bom, configService.getDocumentDesignConfig(dcId), out);
    }

    public static void main(String[] args) {

        // TODO -> unit tests
        JSONParser jsonParser = new JSONParser();
        ReportGenerator reportGenerator = new ReportGenerator();

        String jsonFilePathBackend = "src/test/java/de/starwit/sbom/sbom-backend.json";
        String jsonFilePathFrontend = "src/test/java/de/starwit/sbom/sbom-frontend.json";
        String reportFilename = "report.pdf";

        DocumentDesignConfig dc =  new DocumentDesignConfig();
        dc.setBaseFontSize(10);
        dc.setTitle("Starwit's AI Cockpit");
        dc.setLogoPath("starwit.png");     

        List<Bom> boms = new ArrayList<>();
        boms.add(jsonParser.fileBasedParser(jsonFilePathBackend));
        boms.add(jsonParser.fileBasedParser(jsonFilePathFrontend));

        try {
            reportGenerator.renderPDF(boms, dc, new FileOutputStream(reportFilename));
        } catch (FileNotFoundException e) {
            log.error("Can't create PDF " + e.getMessage());
        }        
    }    
}
