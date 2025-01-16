package de.starwit.sbom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.cyclonedx.model.Bom;
import org.cyclonedx.model.Metadata;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import de.starwit.sbom.generator.DocumentDesignConfig;
import de.starwit.sbom.generator.PDFReportGenerator;
import de.starwit.sbom.generator.SpreadSheetGenerator;
import de.starwit.sbom.rest.ReportRequestDTO;
import de.starwit.sbom.service.JSONParser;
import java.util.ArrayList;

@SpringBootTest
class SBomGeneratorApplicationTests {

	@Test
	void contextLoads() {
	}

    @Test
    public void parseSampleFiles() throws Exception {

        ClassPathResource sbomFile = new ClassPathResource("sbom-backend.json");
        byte[] binaryData = FileCopyUtils.copyToByteArray(sbomFile.getInputStream());
        String strJson = new String(binaryData, StandardCharsets.UTF_8);
		JSONParser jp = new JSONParser();
		Bom result = jp.parseJsonToBOM(strJson);
		Metadata md =  result.getMetadata();
		Date createdTimeStamp = md.getTimestamp();
		String validationTimeString = "2024-06-18T09:14:33Z";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		assertEquals(createdTimeStamp, sdf.parse(validationTimeString));


        sbomFile = new ClassPathResource("sbom-generator.json");
        binaryData = FileCopyUtils.copyToByteArray(sbomFile.getInputStream());
        strJson = new String(binaryData, StandardCharsets.UTF_8);
		jp = new JSONParser();
		result = jp.parseJsonToBOM(strJson);
		md =  result.getMetadata();
		createdTimeStamp = md.getTimestamp();
		validationTimeString = "2025-01-11T19:56:19Z";
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

		assertEquals(createdTimeStamp, sdf.parse(validationTimeString));		
    }

	@Test
	public void generateSpreadSheetTest() throws Exception {
		ClassPathResource sbomFile = new ClassPathResource("sbom-backend.json");
        byte[] binaryData = FileCopyUtils.copyToByteArray(sbomFile.getInputStream());
		List<String> jsons = new ArrayList<>();
		jsons.add(new String(binaryData, StandardCharsets.UTF_8));
		ReportRequestDTO dto = new ReportRequestDTO();
		dto.setSbom(jsons);

		SpreadSheetGenerator ssg =  new SpreadSheetGenerator();
		ssg.createSpreadSheetReport(dto, new FileOutputStream("report.xls"));

		sbomFile = new ClassPathResource("aic-sbom-backend.json");
        binaryData = FileCopyUtils.copyToByteArray(sbomFile.getInputStream());
		jsons = new ArrayList<>();
		jsons.add(new String(binaryData, StandardCharsets.UTF_8));
		dto = new ReportRequestDTO();
		dto.setSbom(jsons);

		ssg.createSpreadSheetReport(dto, new FileOutputStream("report2.xls"));		
	}

	@Test
	public void generateMultipleSpreadSheetTest() throws Exception {
		ClassPathResource sbomFile = new ClassPathResource("sbom-backend.json");
        byte[] binaryData = FileCopyUtils.copyToByteArray(sbomFile.getInputStream());		
		List<String> jsons = new ArrayList<>();
		jsons.add(new String(binaryData, StandardCharsets.UTF_8));
		
		sbomFile = new ClassPathResource("aic-sbom-backend.json");
        binaryData = FileCopyUtils.copyToByteArray(sbomFile.getInputStream());
		jsons.add(new String(binaryData, StandardCharsets.UTF_8));
		
		ReportRequestDTO dto = new ReportRequestDTO();
		dto.setSbom(jsons);

		SpreadSheetGenerator ssg =  new SpreadSheetGenerator();
		ssg.createSpreadSheetReport(dto, new FileOutputStream("report-multi.xls"));		
	}	

	@Test
	public void generatePDFTest() throws Exception {

		DocumentDesignConfig dc = new DocumentDesignConfig();
		dc.setBaseFontSize(10);
        dc.setTitle("Starwit's AI Cockpit");
        dc.setLogoPath("starwit.png");

		ClassPathResource sbomFile = new ClassPathResource("sbom-backend.json");
        byte[] binaryData = FileCopyUtils.copyToByteArray(sbomFile.getInputStream());
        String strJson = new String(binaryData, StandardCharsets.UTF_8);
		JSONParser jp = new JSONParser();
		Bom bom = jp.parseJsonToBOM(strJson);

		PDFReportGenerator pdf = new PDFReportGenerator();
		pdf.renderPDF(bom, dc, new FileOutputStream("report.pdf"));

		sbomFile = new ClassPathResource("aic-sbom-backend.json");
        binaryData = FileCopyUtils.copyToByteArray(sbomFile.getInputStream());
        strJson = new String(binaryData, StandardCharsets.UTF_8);
		jp = new JSONParser();
		bom = jp.parseJsonToBOM(strJson);

		pdf.renderPDF(bom, dc, new FileOutputStream("report2.pdf"));
	}	
}
