package org.endeavourhealth.codesetmanager.models.json;

public class JsonCodeSet {

    private Integer id;
    private String codeSetName;
    private JsonCodeSetCodes[] codeSetCodes;

    private String read2ConceptIds;
    private String ctv3ConceptIds;
    private String sctConceptIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodeSetName() {
        return codeSetName;
    }

    public void setCodeSetName(String codeSetName) {
        this.codeSetName = codeSetName;
    }

    public JsonCodeSetCodes[] getCodeSetCodes() {
        return codeSetCodes;
    }

    public void setCodeSetCodes(JsonCodeSetCodes[] codeSetCodes) {
        this.codeSetCodes = codeSetCodes;
    }

    public String getRead2ConceptIds() {
        return read2ConceptIds;
    }

    public void setRead2ConceptIds(String read2ConceptIds) {
        this.read2ConceptIds = read2ConceptIds;
    }

    public String getCtv3ConceptIds() {
        return ctv3ConceptIds;
    }

    public void setCtv3ConceptIds(String ctv3ConceptIds) {
        this.ctv3ConceptIds = ctv3ConceptIds;
    }

    public String getSctConceptIds() {
        return sctConceptIds;
    }

    public void setSctConceptIds(String sctConceptIds) {
        this.sctConceptIds = sctConceptIds;
    }
}
