package de.starwit.sbom.generator;

public class DocumentDesignConfig {
    String title;
    String logoPath;
    int baseFontSize;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLogoPath() {
        return logoPath;
    }
    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
    public int getBaseFontSize() {
        return baseFontSize;
    }
    public void setBaseFontSize(int baseFontSize) {
        this.baseFontSize = baseFontSize;
    }

    @Override
    public String toString() {
        return "DocumentConfiguration [title=" + title + ", logoPath=" + logoPath + ", baseFontSize=" + baseFontSize
                + "]";
    }
}
