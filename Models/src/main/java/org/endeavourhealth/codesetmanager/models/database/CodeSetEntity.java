package org.endeavourhealth.codesetmanager.models.database;

import org.endeavourhealth.codesetmanager.PersistenceManager;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "code_set", schema = "subscriber_transform_pcr")
public class CodeSetEntity {

    private int id;
    private String codeSetName;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code_set_name")
    public String getCodeSetName() {
        return codeSetName;
    }

    public void setCodeSetName(String codeSetName) {
        this.codeSetName = codeSetName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeSetEntity that = (CodeSetEntity) o;
        return id == that.id && Objects.equals(codeSetName, that.codeSetName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codeSetName);
    }

    public static List<CodeSetEntity> getAllCodeSets() throws Exception {

        EntityManager entityManager = PersistenceManager.getEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CodeSetEntity> cq = cb.createQuery(CodeSetEntity.class);
        cq.from(CodeSetEntity.class);
        TypedQuery<CodeSetEntity> query = entityManager.createQuery(cq);
        List<CodeSetEntity> ret = query.getResultList();
        entityManager.close();

        return ret;
    }

    public static CodeSetEntity getCodeSet(int id) throws Exception {

        EntityManager entityManager = PersistenceManager.getEntityManager();

        CodeSetEntity ret = entityManager.find(CodeSetEntity.class, id);
        entityManager.close();

        return ret;
    }

    public static CodeSetEntity deleteCodeSet(int id) throws Exception {

        EntityManager entityManager = PersistenceManager.getEntityManager();

        entityManager.getTransaction().begin();
        CodeSetEntity entry = entityManager.find(CodeSetEntity.class, id);
        entry = entityManager.merge(entry);
        entityManager.remove(entry);
        entityManager.getTransaction().commit();
        entityManager.close();

        return entry;
    }

    public static CodeSetEntity createCodeSet(CodeSetEntity codeSet) throws Exception {

        EntityManager entityManager = PersistenceManager.getEntityManager();

        String sql = "select max(id) from subscriber_transform_pcr.code_set;";
        Query query = entityManager.createNativeQuery(sql);
        Integer result = (Integer) query.getSingleResult();
        codeSet.setId(result + 1);

        entityManager.getTransaction().begin();
        entityManager.persist(codeSet);
        entityManager.getTransaction().commit();
        entityManager.close();

        return codeSet;
    }

    public static CodeSetEntity updateCodeSet(CodeSetEntity codeSet) throws Exception {

        EntityManager entityManager = PersistenceManager.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(codeSet);
        entityManager.getTransaction().commit();
        entityManager.close();

        return codeSet;
    }
}