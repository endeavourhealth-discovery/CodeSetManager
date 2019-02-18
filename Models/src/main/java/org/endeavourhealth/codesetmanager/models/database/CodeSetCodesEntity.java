package org.endeavourhealth.codesetmanager.models.database;

import org.endeavourhealth.codesetmanager.PersistenceManager;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "code_set_codes", schema = "subscriber_transform_pcr")
public class CodeSetCodesEntity {

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

    public void setSctConceptId(String read2ConceptId) {
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

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CodeSetCodesEntity> query = builder.createQuery(CodeSetCodesEntity.class);
        Root<CodeSetCodesEntity> root = query.from(CodeSetCodesEntity.class);
        List<Predicate> predicates = new ArrayList();
        predicates.add(builder.equal(root.get("codeSetId"), codeSetId));

        query.where(predicates.toArray(new Predicate[0]));
        TypedQuery<CodeSetCodesEntity> tq = entityManager.createQuery(query);
        List<CodeSetCodesEntity> ret = tq.getResultList();
        entityManager.close();

        return ret;
    }
}