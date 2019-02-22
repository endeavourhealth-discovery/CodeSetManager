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

        List<CodeSetEntity> codeSets = CodeSetEntity.getAllCodeSets();
        for (CodeSetEntity entity : codeSets) {
            jsonCodeSets.add(parseEntityToJson(entity));
        }

        return jsonCodeSets;
    }

    public JsonCodeSet saveCodeSet(JsonCodeSet jsonCodeSet, boolean isEdit) throws Exception {

        if (isEdit) {

            CodeSetEntity entity = new CodeSetEntity();
            entity.setId(jsonCodeSet.getId());
            entity.setCodeSetName(jsonCodeSet.getCodeSetName());
            CodeSetEntity.updateCodeSet(entity);

            CodeSetCodesEntity.deleteCodeSetCodes(jsonCodeSet.getId());

            CodeSetCodesEntity.createCodeSetCodes(jsonCodeSet.getId(), parseCodeSetCodes(jsonCodeSet.getCodeSetCodes()));

            return parseEntityToJson(entity);

        } else {

            CodeSetEntity entity = new CodeSetEntity();
            entity.setCodeSetName(jsonCodeSet.getCodeSetName());
            entity = CodeSetEntity.createCodeSet(entity);

            CodeSetCodesEntity.createCodeSetCodes(entity.getId(),
                    parseCodeSetCodes(jsonCodeSet.getCodeSetCodes()));

            return parseEntityToJson(entity);
        }
    }

    public void deleteCodeSet(String id) throws Exception {
        CodeSetCodesEntity.deleteCodeSetCodes(Integer.valueOf(id));
        CodeSetEntity.deleteCodeSet(Integer.valueOf(id));
    }

    private static JsonCodeSet parseEntityToJson(CodeSetEntity codeSet) throws Exception {

        List<CodeSetCodesEntity> codeSetCodes = CodeSetCodesEntity.getCodeSetCode(codeSet.getId());
        JsonCodeSetCodes[] jsonCodeSetCodes = new JsonCodeSetCodes[codeSetCodes.size()];

        String read2ConceptIds = "";
        String ctv3ConceptIds = "";
        String sctConceptIds = "";

        for (int i = 0; i < codeSetCodes.size(); i++) {
            jsonCodeSetCodes[i] = new JsonCodeSetCodes();
            jsonCodeSetCodes[i].setCodeSetId(codeSetCodes.get(i).getCodeSetId());
            jsonCodeSetCodes[i].setRead2ConceptId(codeSetCodes.get(i).getRead2ConceptId());
            jsonCodeSetCodes[i].setCtv3ConceptId(codeSetCodes.get(i).getCtv3ConceptId());
            jsonCodeSetCodes[i].setSctConceptId(codeSetCodes.get(i).getSctConceptId());

            if (codeSetCodes.get(i).getRead2ConceptId() != null &&
                    codeSetCodes.get(i).getRead2ConceptId().length() > 0 &&
                    read2ConceptIds.indexOf(codeSetCodes.get(i).getRead2ConceptId()) == -1 ) {
                read2ConceptIds += codeSetCodes.get(i).getRead2ConceptId() + "; ";
            }

            if (codeSetCodes.get(i).getCtv3ConceptId() != null &&
                    codeSetCodes.get(i).getCtv3ConceptId().length() > 0 &&
                    ctv3ConceptIds.indexOf(codeSetCodes.get(i).getCtv3ConceptId()) == -1 ) {
                ctv3ConceptIds += codeSetCodes.get(i).getCtv3ConceptId() + "; ";
            }

            if (codeSetCodes.get(i).getSctConceptId() != null &&
                    codeSetCodes.get(i).getSctConceptId().length() > 0 &&
                    sctConceptIds.indexOf(codeSetCodes.get(i).getSctConceptId()) == -1 ) {
                sctConceptIds += codeSetCodes.get(i).getSctConceptId() + "; ";
            }
        }

        JsonCodeSet jsonCodeSet = new JsonCodeSet();
        jsonCodeSet.setId(codeSet.getId());
        jsonCodeSet.setCodeSetName(codeSet.getCodeSetName());
        jsonCodeSet.setCodeSetCodes(jsonCodeSetCodes);

        jsonCodeSet.setRead2ConceptIds(trimLastChar(read2ConceptIds));
        jsonCodeSet.setCtv3ConceptIds(trimLastChar(ctv3ConceptIds));
        jsonCodeSet.setSctConceptIds(trimLastChar(sctConceptIds));

        return jsonCodeSet;
    }

    private ArrayList<CodeSetCodesEntity> parseCodeSetCodes(JsonCodeSetCodes[] codes) {

        ArrayList<CodeSetCodesEntity> codeEntities = new ArrayList();
        for (JsonCodeSetCodes code : codes) {
            CodeSetCodesEntity codeEntity = new CodeSetCodesEntity();
            codeEntity.setCodeSetId(code.getCodeSetId());
            codeEntity.setRead2ConceptId(code.getRead2ConceptId());
            codeEntity.setCtv3ConceptId(code.getCtv3ConceptId());
            codeEntity.setSctConceptId(code.getSctConceptId());
            codeEntities.add(codeEntity);
        }
        return codeEntities;
    }

    private static String trimLastChar(String value) {
        if (value.endsWith("; ")) {
            value = value.substring(0, (value.length() - 2));
        }
        return value;
    }
}
