package de.starwit.sbom.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.sbom.generator.DocumentDesignConfig;
import de.starwit.sbom.service.DocumentDesignConfigService;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping(path = "api/design")
public class DocumentDesignController {

    @Autowired
    DocumentDesignConfigService configService;

    @Operation(summary = "Get all document design configs")
    @GetMapping("/")
    public ResponseEntity<List<DocumentDesignConfig>> getAllDesignConfig() {
        return ResponseEntity.ok(configService.getDocumentDesignConfigs());
    }

    @Operation(summary = "Get a document design config")
    @GetMapping("/{dcId}")
    public ResponseEntity<DocumentDesignConfig> getDesignConfig(@PathVariable int dcId) {
        DocumentDesignConfig dc = configService.getDocumentDesignConfig(dcId);
        if(dc == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(configService.getDocumentDesignConfig(dcId));
        }
    }    

    @Operation(summary = "Add document design config")
    @PostMapping("/")
    public int addDesignConfig(@RequestBody DocumentDesignConfig dc) {
        return configService.addDocumentDesign(dc);
    }

    @Operation(summary = "Delete a document design config")
    @DeleteMapping("/{dcId}")
    public void deleteDesignConfig(@PathVariable int dcId) {
        configService.deleteDocumentDesign(dcId);
    }    
}
