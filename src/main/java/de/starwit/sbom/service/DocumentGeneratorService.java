package de.starwit.sbom.service;

import org.cyclonedx.model.Bom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Document;

import de.starwit.sbom.generator.DocumentConfiguration;
import de.starwit.sbom.generator.ReportGenerator;
import de.starwit.sbom.parser.JSONParser;

public class DocumentGeneratorService {
        static final Logger log = LoggerFactory.getLogger(DocumentGeneratorService.class);

        public static void main(String[] args) {

        JSONParser jsonParser = new JSONParser();
        ReportGenerator reportGenerator = new ReportGenerator();

        String jsonFilePath = "src/test/java/de/starwit/sbom/sbom-backend.json";
        String reportFilename = "report.pdf";

        DocumentConfiguration dc =  new DocumentConfiguration();
        dc.setBaseFontSize(10);
        dc.setTitle("Starwit's AI Cockpit");
        dc.setLogoPath("starwit.png");


        Bom bom = jsonParser.fileBasedParser(jsonFilePath);
        Document report = reportGenerator.renderPDF(bom, dc);
        
    }
}
