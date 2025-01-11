package de.starwit.sbom.rest;

public class ReportRequestDTO {
    private String sbomURI;
    private int dcId;
    
    public String getSbomURI() {
        return sbomURI;
    }
    public void setSbomURI(String sbomURI) {
        this.sbomURI = sbomURI;
    }
    public int getDcId() {
        return dcId;
    }
    public void setDcId(int dcId) {
        this.dcId = dcId;
    }
}
