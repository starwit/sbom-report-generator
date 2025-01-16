package de.starwit.sbom.generator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.cyclonedx.model.Bom;
import org.cyclonedx.model.Component;
import org.cyclonedx.model.License;
import org.cyclonedx.model.LicenseChoice;
import org.cyclonedx.model.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.starwit.sbom.rest.ReportRequestDTO;
import de.starwit.sbom.service.JSONParser;
import jakarta.servlet.ServletOutputStream;

@Service
public class SpreadSheetGenerator {

    static final Logger log = LoggerFactory.getLogger(SpreadSheetGenerator.class);

    public void createSpreadSheetReport(ReportRequestDTO reportData, ServletOutputStream out) {
        JSONParser jp = new JSONParser();
        Bom bom = jp.parseJsonToBOM(reportData.getSbom());
        createSpreadSheetReport(bom, out);
    }    

    public void createSpreadSheetReport(Bom bom, OutputStream out) {
        Metadata md = bom.getMetadata();
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet(md.getComponent().getName());

        Font font = wb.createFont();
        font.setFontHeightInPoints((short)24);
        CellStyle headerStyle = wb.createCellStyle();
        headerStyle.setFont(font);
        
        Row r = sheet.createRow(0);
        r.setHeight((short)550);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        Cell cell = r.createCell(0);
        cell.setCellValue("Software Bill of Material");
        cell.setCellStyle(headerStyle);

    
        addDataItem("Creation Date", md.getTimestamp().toString(), sheet, 2);
        addDataItem("Component ID", md.getComponent().getGroup(), sheet, 3);
        addDataItem("Version", md.getComponent().getVersion(), sheet, 4);
        addDataItem("Description", md.getComponent().getDescription(), sheet, 5);
        addDataItem("License", CycloneTools.getLicenseName(md.getComponent().getLicenses()), sheet, 6);
        
        // Components
        
        int rowID = 9;
        addComponentListHeader(sheet,rowID);
        rowID++;
        for(Component c : bom.getComponents()) {
            addComponentData(c, sheet, rowID);
            rowID += 1;
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);

        try {
            wb.write(out);
            wb.close();
        } catch (IOException e) {
            log.error("Can't write to output stream " + e.getMessage(), e);
        }
    }

    private void addDataItem(String title, String value, Sheet sheet, int row) {
        Row r = sheet.createRow(row);
        r.createCell(0).setCellValue(title);
        r.createCell(1).setCellValue(value);
    }

    private void addComponentListHeader(Sheet sheet,int rowID) {
        Row r = sheet.createRow(rowID);
        r.createCell(0).setCellValue("Name");
        r.createCell(1).setCellValue("Publisher");
        r.createCell(2).setCellValue("Version");
        r.createCell(3).setCellValue("License");
        r.createCell(4).setCellValue("Description");
    }

    private void addComponentData(Component c, Sheet sheet, int row) {
        Row r = sheet.createRow(row);
        r.createCell(0).setCellValue(c.getName());
        r.createCell(1).setCellValue(c.getPublisher());
        r.createCell(2).setCellValue(c.getVersion());
        StringBuffer sb = new StringBuffer();
        if(c.getLicenses() != null) {
            LicenseChoice lc = c.getLicenses();
            if (lc.getLicenses() != null) {
                for (License l : lc.getLicenses()) {
                    sb.append(l.getId());
                }
            }
            if(lc.getExpression() != null) {
                sb.append(lc.getExpression().getValue());
            }
            r.createCell(3).setCellValue(sb.toString());
        }         
        r.createCell(4).setCellValue(c.getDescription());
    }    

    public static void main(String[] args) throws Exception {
        SpreadSheetGenerator ssg =  new SpreadSheetGenerator();
        JSONParser jp = new JSONParser();
        Bom bom = jp.fileBasedParser("src/test/resources/sbom-generator.json");

        ssg.createSpreadSheetReport(bom, new FileOutputStream("report.xls"));
    }  
}
