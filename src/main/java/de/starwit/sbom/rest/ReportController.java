package de.starwit.sbom.rest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import de.starwit.sbom.service.DocumentGeneratorService;
import de.starwit.sbom.service.DocumentHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "api/report")
public class ReportController {

    private Logger log = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    DocumentGeneratorService dgs;

    @Autowired
    DocumentHistoryService dhs;

    @Autowired
    private RestTemplate restTemplate;    
    
    @Operation(summary = "Generate PDF report based on provided CycloneDX definition")
    @PostMapping("/{dcId}/{compact}")
    public void getReport(HttpServletResponse response, @RequestBody String json, @PathVariable("dcId") int dcId, @PathVariable("compact") boolean compact) {
        ReportRequestDTO dto = new ReportRequestDTO();
        dto.setCompact(compact);
        dto.setDcId(dcId);
        List<String> jsonList = new ArrayList<String>();
        jsonList.add(json);
        dto.setSbom(jsonList);
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_sbom_" + currentDateTime + ".pdf";
        dhs.addDocumentHistory("filename=pdf_sbom_" + currentDateTime + ".pdf");
        response.setHeader(headerKey, headerValue);        
        ServletOutputStream out;
        try {
            out = response.getOutputStream();
            dgs.generateReport(dto, out);
        } catch (IOException e) {
            log.error("Can't create output stream for pdf");
            //TODO send 500 response code
        }
    }

    @Operation(summary = "Generate PDF report based on CycloneDX definition provided as URI")
    @PostMapping("/remote")
    public void generateReportFromRemoteURI(HttpServletResponse response, @RequestBody ReportRequestDTO reportData) {

        List<String> json = loadCycloneDXData(reportData.getSbomURI());
        if(!json.equals("")) {
            reportData.setSbom(json);
            response.setContentType("application/pdf");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=pdf_sbom_" + currentDateTime + ".pdf";
                    
            response.setHeader(headerKey, headerValue);        
            ServletOutputStream out;
            try {
                out = response.getOutputStream();
                dgs.generateReport(reportData, out);
            } catch (IOException e) {
                log.error("Can't create output stream for pdf");
                response.setStatus(500);
            }
        } else {
            log.error("Can't load CycloneDX data from remote URI");
            response.setStatus(404);
        }
    }

    @Operation(summary = "Generate Excel report based on CycloneDX definition provided as URI")
    @PostMapping("/excel/remote")
    public void generateExcel(HttpServletResponse response, @RequestBody ReportRequestDTO reportData) {
        
        List<String> json = loadCycloneDXData(reportData.getSbomURI());
        if(!json.equals("")) {
            reportData.setSbom(json);
            response.setContentType("application/vnd.ms-excel");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=excel_sbom_" + currentDateTime + ".xls";            
            response.setHeader(headerKey, headerValue);        
            ServletOutputStream out;
            try {
                out = response.getOutputStream();
                dgs.createSpreadSheetReport(reportData, out);
            } catch (IOException e) {
                log.error("Can't create output stream for pdf");
                response.setStatus(500);
            }
        } else {
            log.error("Can't load CycloneDX data from remote URI");
            response.setStatus(404);
        }
    }    

    private List<String> loadCycloneDXData(List<String> sbomURI) {
        List<String> sboms = new ArrayList<>();
        for (String uri : sbomURI) {
            try {
                ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
                if (response.getStatusCode().is2xxSuccessful()) {
                    sboms.add(response.getBody());
                }
            } catch (Exception e) {
                log.error("Can't load CycloneDX data from remote URI " + uri + " " + e.getMessage());
                log.error("Skipping sbom");
            }
        }
        return sboms;
    }
}
