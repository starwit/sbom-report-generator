package de.starwit.sbom.rest;

public class ReportRequestDTO {
    private String sbomURI;
    private int dcId;
    private boolean compact;
    private String sbom;

    public String getSbomURI() {
        return sbomURI;
    }

    public String getSbom() {
        return sbom;
    }

    public void setSbom(String sbom) {
        this.sbom = sbom;
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

    public boolean isCompact() {
        return compact;
    }

    public void setCompact(boolean compact) {
        this.compact = compact;
    }
}
