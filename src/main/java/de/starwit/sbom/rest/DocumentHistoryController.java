package de.starwit.sbom.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.sbom.service.DocumentHistory;
import de.starwit.sbom.service.DocumentHistoryService;

@RestController
@RequestMapping(path = "api/history")
public class DocumentHistoryController {
    
    @Autowired
    DocumentHistoryService dhs;

    @GetMapping
    public ResponseEntity<List<DocumentHistory>> getDocHistory() {
        return ResponseEntity.ok(dhs.getDocumentHistory());
    }
}
