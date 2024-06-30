package de.starwit.sbom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.sbom.generator.DocumentDesignConfig;
import jakarta.annotation.PostConstruct;

@Service
public class DocumentDesignConfigService {

    @Autowired
    List<DocumentDesignConfig> documentDesignConfigs;

    @PostConstruct
    private void init() {
        DocumentDesignConfig dc =  new DocumentDesignConfig();
        dc.setBaseFontSize(10);
        dc.setTitle("Starwit's AI Cockpit");
        dc.setLogoPath("starwit.png");
        documentDesignConfigs.add(dc);

        dc =  new DocumentDesignConfig();
        dc.setBaseFontSize(12);
        dc.setTitle("Starwit's AI Cockpit II - Starwit strikes back!");
        dc.setLogoPath("starwit.png"); 
        documentDesignConfigs.add(dc);
    }

    public List<DocumentDesignConfig> getDocumentDesignConfigs() {
        return documentDesignConfigs;
    }
    
    public DocumentDesignConfig getDocumentDesignConfig(int i) {
        if (i > documentDesignConfigs.size()) {
            return null;
        } else {
            return documentDesignConfigs.get(i);
        }
    }

    public int addDocumentDesign(DocumentDesignConfig dc) {
        documentDesignConfigs.add(dc);
        return documentDesignConfigs.indexOf(dc);
    }

    public void deleteDocumentDesign(DocumentDesignConfig dc) {
        int configIndex = documentDesignConfigs.indexOf(dc);
        documentDesignConfigs.remove(configIndex);
    }

    public void deleteDocumentDesign(int dcId) {
        documentDesignConfigs.remove(dcId);
    }    

    public void updateDocumentDesign(DocumentDesignConfig dc) {
        int configIndex = documentDesignConfigs.indexOf(dc);
        documentDesignConfigs.remove(configIndex);
        
    }    
}
