package de.starwit.sbom.rest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.sbom.service.DocumentGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "api/report")
public class ReportController {

    private Logger log = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    DocumentGeneratorService dgs;
    
    @Operation(summary = "Generate PDF report based on CycloneDX definition")
    @PostMapping("/{dcId}")
    public void getReport(HttpServletResponse response, @RequestBody String json, @PathVariable("dcId") int dcId) {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_sbom_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);        
        ServletOutputStream out;
        try {
            out = response.getOutputStream();
            dgs.generateReport(json, dcId, out);
        } catch (IOException e) {
            log.error("Can't create output stream for pdf");
            //TODO send 500 response code
        }

    }
}
