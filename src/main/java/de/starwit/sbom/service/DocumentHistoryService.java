package de.starwit.sbom.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class DocumentHistoryService {

    @Autowired
    List<DocumentHistory> documentHistory;

    @PostConstruct
    private void init() {
        DocumentHistory dh =  new DocumentHistory();
        dh.setCreationDate(new Date());
        dh.setName("Test");
        documentHistory.add(dh);
    }    

    public List<DocumentHistory> getDocumentHistory() {
        return documentHistory;
    }

    public void addDocumentHistory(String name) {
        DocumentHistory dh =  new DocumentHistory();
        dh.setCreationDate(new Date());
        dh.setName(name);
        documentHistory.add(dh);        
    }    
}
