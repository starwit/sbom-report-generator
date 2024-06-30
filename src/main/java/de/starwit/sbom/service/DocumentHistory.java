package de.starwit.sbom.service;

import java.util.Date;

public class DocumentHistory {
    String name;
    Date creationDate;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "DocumentHistory [name=" + name + ", creationDate=" + creationDate + "]";
    }
}
