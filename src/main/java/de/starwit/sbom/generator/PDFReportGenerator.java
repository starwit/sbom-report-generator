package de.starwit.sbom.generator;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.cyclonedx.model.Bom;
import org.cyclonedx.model.Component;
import org.cyclonedx.model.License;
import org.cyclonedx.model.LicenseChoice;
import org.cyclonedx.model.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.openpdf.text.BadElementException;
import org.openpdf.text.Chunk;
import org.openpdf.text.Document;
import org.openpdf.text.DocumentException;
import org.openpdf.text.Element;
import org.openpdf.text.Font;
import org.openpdf.text.FontFactory;
import org.openpdf.text.Image;
import org.openpdf.text.PageSize;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;

import de.starwit.sbom.rest.ReportRequestDTO;
import de.starwit.sbom.service.JSONParser;

@Service
public class PDFReportGenerator {

    static final Logger log = LoggerFactory.getLogger(PDFReportGenerator.class);

    public void createPDFReport(ReportRequestDTO dto, DocumentDesignConfig dc, OutputStream out) {

        JSONParser jp = new JSONParser();
        List<Bom> boms = jp.parseJsonToBOM(dto.getSbom());

        Document document = new Document(PageSize.A4);      
        Font baseFont = FontFactory.getFont(FontFactory.HELVETICA, dc.getBaseFontSize());

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            for(Bom bom: boms) {
                addHeader(document, dc.getTitle());
                document.add(addMetadata(bom, document, baseFont)); 
                document.newPage();
                document.add(buildComponentTable(bom, document, baseFont, dc.isCompact()));
                document.newPage();
            }
            document.close();
            writer.close();
        } catch (DocumentException e) {
            log.info("Can't write document " + e.getMessage());
        } 
    }

    public void renderPDF(Bom bom, DocumentDesignConfig dc, OutputStream out) {
        Document document = new Document(PageSize.A4);      
        Font baseFont = FontFactory.getFont(FontFactory.HELVETICA, dc.getBaseFontSize());
        
        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            addHeader(document, dc.getTitle());
            document.add(addMetadata(bom, document, baseFont)); 
            document.newPage();
            document.add(buildComponentTable(bom, document, baseFont, dc.isCompact()));
            document.close();
        } catch (DocumentException e) {
            log.info("Can't write document " + e.getMessage());
        }        
    }

    private void addHeader(Document document,String title) {
        try {
            Image img = Image.getInstance(getClass().getClassLoader().getResource("starwit.png"));
            img.scalePercent(10);
            document.add(Image.getInstance(img));
        } catch (BadElementException | IOException e) {
            log.info("Can't add header image " + e.getMessage());
        }
        Font font12 = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Paragraph p = new Paragraph("Component Report for " + title, font12);
        p.setAlignment(Element.ALIGN_CENTER);        
        document.add(p);
        document.add(Chunk.NEWLINE);
    }

    private PdfPTable addMetadata(Bom bom, Document document, Font font) {
        float width = document.getPageSize().getWidth();
        PdfPTable table = new PdfPTable(2);
        table.getDefaultCell().setBorder(1);
        table.setHorizontalAlignment(0);
        table.setTotalWidth(width - 72);
        table.setLockedWidth(true);

        Metadata data = bom.getMetadata();
        table.addCell(new Phrase("Creation Date", font));
        table.addCell(new Phrase(data.getTimestamp()+"", font));
        Component c = data.getComponent();
        table.addCell(new Phrase("Component ID", font));
        table.addCell(new Phrase(c.getGroup() + " " + c.getName(), font));
        table.addCell(new Phrase("Version", font));
        table.addCell(new Phrase(c.getVersion()+"", font));
        table.addCell(new Phrase("Description", font));
        table.addCell(new Phrase(c.getDescription()+"", font));
        table.addCell(new Phrase("License", font));
        table.addCell(new Phrase(CycloneTools.getLicenseName(c.getLicenses()), font));
        
        return table;
    }

    private PdfPTable buildComponentTable(Bom bom, Document document, Font font, boolean isCompact) {
        float width = document.getPageSize().getWidth();
        document.open();

        PdfPTable table;
        if(isCompact) {
            table = new PdfPTable(5);
        } else {
            table = new PdfPTable(6);
        }
        table.getDefaultCell().setBorder(1);
        table.setHorizontalAlignment(0);
        table.setTotalWidth(width - 72);
        table.setLockedWidth(true);

        PdfPCell cell = new PdfPCell(new Phrase("List of Components"));
        cell.setColspan(6);
        table.addCell(cell);

        table.addCell(new Phrase("Name", font));
        table.addCell(new Phrase("Author", font));
        table.addCell(new Phrase("Version", font));
        table.addCell(new Phrase("License", font));
        table.addCell(new Phrase("Type", font));
        if(!isCompact) {
            table.addCell(new Phrase("Description", font));
        }

        for (Component c : bom.getComponents()) {
            table.addCell(new Phrase(c.getName(), font));
            
            String publisher = "";
            if (c.getPublisher() != null) {
                publisher = c.getPublisher();
            }

            String author = "";
            if (c.getAuthor() != null) {
                author = c.getAuthor();
            }            
            table.addCell(new Phrase(publisher + " " + author, font));

            String version = "";
            if (c.getVersion() != null) {
                version = c.getVersion();
            }            
            table.addCell(new Phrase(version, font));            

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

                table.addCell(new Phrase(sb.toString(), font));
            } else {
                table.addCell(new Phrase("no license", font));
            }

            if (c.getType() != null) {
                table.addCell(new Phrase(c.getType().name().toLowerCase(), font));
            } else {
                table.addCell(new Phrase("", font));
            }
            
            if(!isCompact) {
                if(c.getDescription() != null) {
                    table.addCell(new Phrase(c.getDescription(), FontFactory.getFont(FontFactory.HELVETICA, font.getSize()-2)));
                } else {
                    table.addCell(new Phrase("", font));
                }
            }
            
        }

        return table;
    }
}
