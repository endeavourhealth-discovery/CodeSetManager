package org.endeavourhealth.codesetmanager.api.logic;

import org.endeavourhealth.codesetmanager.models.database.CodeSetCodesEntity;
import org.endeavourhealth.codesetmanager.models.database.CodeSetEntity;
import org.endeavourhealth.codesetmanager.models.json.JsonCodeSet;
import org.endeavourhealth.codesetmanager.models.json.JsonCodeSetCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CodeSetManagerLogic {

    private static final Logger LOG = LoggerFactory.getLogger(CodeSetManagerLogic.class);

    public CodeSetManagerLogic() {
    }

    public static List<JsonCodeSet> getAllCodeSets() throws Exception {

        ArrayList<JsonCodeSet> jsonCodeSets = new ArrayList();
        JsonCodeSet jsonCodeSet = null;
        JsonCodeSetCodes[] jsonCodeSetCodes = null;
        List<CodeSetCodesEntity> codeSetCodes = null;

        String read2ConceptIds;
        String ctv3ConceptIds;
        String sctConceptIds;

        List<CodeSetEntity> codeSets = CodeSetEntity.getAllCodeSets();

        for (CodeSetEntity codeSet : codeSets) {

            codeSetCodes = CodeSetCodesEntity.getCodeSetCode(codeSet.getId());
            jsonCodeSetCodes = new JsonCodeSetCodes[codeSetCodes.size()];

            read2ConceptIds = "";
            ctv3ConceptIds = "";
            sctConceptIds = "";

            for (int i = 0; i < codeSetCodes.size(); i++) {
                jsonCodeSetCodes[i] = new JsonCodeSetCodes();
                jsonCodeSetCodes[i].setCodeSetId(codeSetCodes.get(i).getCodeSetId());

                if (codeSetCodes.get(i).getRead2ConceptId() != null &&
                        codeSetCodes.get(i).getRead2ConceptId().length() > 0 &&
                        read2ConceptIds.indexOf(codeSetCodes.get(i).getRead2ConceptId()) == -1 ) {

                    jsonCodeSetCodes[i].setRead2ConceptId(codeSetCodes.get(i).getRead2ConceptId());
                    read2ConceptIds += codeSetCodes.get(i).getRead2ConceptId() + "; ";
                }

                if (codeSetCodes.get(i).getCtv3ConceptId() != null &&
                        codeSetCodes.get(i).getCtv3ConceptId().length() > 0 &&
                        ctv3ConceptIds.indexOf(codeSetCodes.get(i).getCtv3ConceptId()) == -1 ) {

                    jsonCodeSetCodes[i].setCtv3ConceptId(codeSetCodes.get(i).getCtv3ConceptId());
                    ctv3ConceptIds += codeSetCodes.get(i).getCtv3ConceptId() + "; ";
                }

                if (codeSetCodes.get(i).getSctConceptId() != null &&
                        codeSetCodes.get(i).getSctConceptId().length() > 0 &&
                        sctConceptIds.indexOf(codeSetCodes.get(i).getSctConceptId()) == -1 ) {
                    jsonCodeSetCodes[i].setSctConceptId(codeSetCodes.get(i).getSctConceptId());
                    sctConceptIds += codeSetCodes.get(i).getSctConceptId() + "; ";
                }
            }

            jsonCodeSet = new JsonCodeSet();
            jsonCodeSet.setId(codeSet.getId());
            jsonCodeSet.setCodeSetName(codeSet.getCodeSetName());
            jsonCodeSet.setCodeSetCodes(jsonCodeSetCodes);

            jsonCodeSet.setRead2ConceptIds(trimLastChar(read2ConceptIds));
            jsonCodeSet.setCtv3ConceptIds(trimLastChar(ctv3ConceptIds));
            jsonCodeSet.setSctConceptIds(trimLastChar(sctConceptIds));

            jsonCodeSets.add(jsonCodeSet);
        }

        return jsonCodeSets;
    }

    private static String trimLastChar(String value) {
        if (value.endsWith("; ")) {
            value = value.substring(0, (value.length() - 2));
        }
        return value;
    }
}
