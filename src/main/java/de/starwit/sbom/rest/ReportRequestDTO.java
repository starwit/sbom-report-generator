package de.starwit.sbom.rest;

import java.util.List;

public class ReportRequestDTO {
    private List<String> sbomURI;
    private int dcId;
    private boolean compact;
    private List<String> sbom;

    public List<String> getSbom() {
        return sbom;
    }

    public void setSbom(List<String> sbom) {
        this.sbom = sbom;
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

    public List<String> getSbomURI() {
        return sbomURI;
    }

    public void setSbomURI(List<String> sbomURI) {
        this.sbomURI = sbomURI;
    }
}
