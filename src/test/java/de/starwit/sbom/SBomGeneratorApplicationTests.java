package de.starwit.sbom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.cyclonedx.model.Bom;
import org.cyclonedx.model.Metadata;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import de.starwit.sbom.service.JSONParser;

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

}
