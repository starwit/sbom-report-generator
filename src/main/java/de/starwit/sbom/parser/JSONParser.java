package de.starwit.sbom.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.cyclonedx.exception.ParseException;
import org.cyclonedx.model.Bom;
import org.cyclonedx.model.Component;
import org.cyclonedx.parsers.BomParserFactory;
import org.cyclonedx.parsers.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JSONParser {

    static final Logger log = LoggerFactory.getLogger(JSONParser.class);

    public Bom fileBasedParser(String filePath) {
        Bom result = new Bom();
        List<Component> components = new ArrayList<Component>();
        result.setComponents(components);
        File f = new File(filePath);
        if(f.exists() && !f.isDirectory()) { 
            try {
                byte[] jsonBytes = Files.readAllBytes(Paths.get(filePath));
                result = parseBomFromBytes(jsonBytes);
            } catch (IOException e) {
                log.info("Can't access file " + e.getMessage());
            }
        }

        return result;
    }

    private Bom parseBomFromBytes(byte[] jsonBytes) {
        Bom bom = new Bom();

        try {
            Parser parser = BomParserFactory.createParser(jsonBytes);
            bom = parser.parse(jsonBytes);
            return bom;
        } catch (ParseException e) {
            log.info("Parsing didn't work " + e.getMessage());
        }

        return bom;
    }

    public Bom parseJsonToBOM(String json) {
        return parseBomFromBytes(json.getBytes());
    }
}
