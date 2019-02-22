package org.endeavourhealth.codesetmanager.models.database;

import org.endeavourhealth.codesetmanager.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "code_set_codes", schema = "subscriber_transform_pcr")
public class CodeSetCodesEntity {

    private static final Logger LOG = LoggerFactory.getLogger(CodeSetCodesEntity.class);

    private int codeSetId;
    private String read2ConceptId;
    private String ctv3ConceptId;
    private String sctConceptId;

    @Id
    @Column(name = "code_set_id")
    public int getCodeSetId() {
        return codeSetId;
    }

    public void setCodeSetId(int codeSetId) {
        this.codeSetId = codeSetId;
    }

    @Basic
    @Column(name = "read2_concept_id")
    public String getRead2ConceptId() {
        return read2ConceptId;
    }

    public void setRead2ConceptId(String read2ConceptId) {
        this.read2ConceptId = read2ConceptId;
    }

    @Basic
    @Column(name = "ctv3_concept_id")
    public String getCtv3ConceptId() {
        return ctv3ConceptId;
    }

    public void setCtv3ConceptId(String ctv3ConceptId) {
        this.ctv3ConceptId = ctv3ConceptId;
    }

    @Basic
    @Column(name = "sct_concept_id")
    public String getSctConceptId() {
        return sctConceptId;
    }

    public void setSctConceptId(String sctConceptId) {
        this.sctConceptId = sctConceptId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeSetCodesEntity that = (CodeSetCodesEntity) o;
        return codeSetId == that.codeSetId &&
                Objects.equals(read2ConceptId, that.read2ConceptId) &&
                Objects.equals(ctv3ConceptId, that.ctv3ConceptId) &&
                Objects.equals(sctConceptId, that.sctConceptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeSetId, read2ConceptId, ctv3ConceptId, sctConceptId);
    }

    public static List<CodeSetCodesEntity> getCodeSetCode(int codeSetId) throws Exception {

        EntityManager entityManager = PersistenceManager.getEntityManager();

        String sql = "select * from subscriber_transform_pcr.code_set_codes where code_set_id = :codeSetId";
        Query query = entityManager.createNativeQuery(sql).setParameter("codeSetId", codeSetId);
        List<Object[]> list = query.getResultList();
        entityManager.close();

        List<CodeSetCodesEntity> ret = new ArrayList<>(list.size());
        CodeSetCodesEntity entity = null;
        for (Object[] obj : list) {
            entity = new CodeSetCodesEntity();
            entity.setCodeSetId((Integer) obj[0]);
            entity.setRead2ConceptId(obj[1].toString());
            entity.setCtv3ConceptId(obj[2].toString());
            entity.setSctConceptId(obj[3].toString());
            ret.add(entity);
        }

        return ret;
    }

    public static void createCodeSetCodes(int codeSetId, ArrayList<CodeSetCodesEntity> codeSetCodes) throws Exception {

        EntityManager entityManager = PersistenceManager.getEntityManager();

        entityManager.getTransaction().begin();
        for (CodeSetCodesEntity entity : codeSetCodes) {
            String sql = "insert into subscriber_transform_pcr.code_set_codes values (:codeSetId, :read2ConceptId, :ctv3ConceptId, :sctConceptId);";
            Query query = entityManager.createNativeQuery(sql).
                    setParameter("codeSetId", codeSetId).
                    setParameter("read2ConceptId", entity.read2ConceptId).
                    setParameter("ctv3ConceptId", entity.ctv3ConceptId).
                    setParameter("sctConceptId", entity.sctConceptId);
            query.executeUpdate();
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static void deleteCodeSetCodes(int codeSetId) throws Exception {

        EntityManager entityManager = PersistenceManager.getEntityManager();

        String sql = "delete from subscriber_transform_pcr.code_set_codes where code_set_id = :codeSetId";
        Query query = entityManager.createNativeQuery(sql).setParameter("codeSetId", codeSetId);

        entityManager.getTransaction().begin();
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}