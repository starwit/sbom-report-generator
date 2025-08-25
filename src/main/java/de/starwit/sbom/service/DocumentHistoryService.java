package de.starwit.sbom.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class DocumentHistoryService {

    @Autowired
    List<DocumentHistory> documentHistoryList;

    @PostConstruct
    private void init() {
        DocumentHistory documentHistory =  new DocumentHistory();
        documentHistory.setCreationDate(new Date());
        documentHistory.setName("Test");
        documentHistoryList.add(documentHistory);
    }    

    public List<DocumentHistory> getDocumentHistory() {
        return documentHistoryList;
    }

    public void addDocumentHistory(String name) {
        DocumentHistory documentHistory =  new DocumentHistory();
        documentHistory.setCreationDate(new Date());
        documentHistory.setName(name);
        documentHistoryList.add(documentHistory);        
    }    
}
