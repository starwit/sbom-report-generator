package de.starwit.sbom.generator;

import org.cyclonedx.model.License;
import org.cyclonedx.model.LicenseChoice;


public class CycloneTools {
    
    public static String getLicenseName(LicenseChoice lc) {
        StringBuffer sb = new StringBuffer();
        if(lc != null) {
            for (License l : lc.getLicenses()) {
                if(l.getId() != null) {
                    sb.append(l.getId());
                }
                if(l.getName() != null) {
                    sb.append(l.getName());
                }
            }
        } else {
            sb.append("no license found");
        }
        return sb.toString();
    }
}
