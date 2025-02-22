package eap.uniapp.db;

import eap.uniapp.db.exceptions.NonexistentEntityException;
import eap.uniapp.db.exceptions.PreexistingEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

/**
 *
 * @author Ersi
 */
public class UniversityJpaController implements Serializable {
    
    //constructor
    public UniversityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
        System.out.println("Entity Manager Factory created.");
    }
    
    private EntityManagerFactory emf = null;
    
    //getter of Entity Manager
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    /*
    //μέθοδος κλεισίματος του EntityManagerFactory
    public void close(){
        if(emf != null && emf.isOpen()){
            emf.close();
            System.out.println("Entity Manager Factory closed.");
        }
    }
    */
    //
    public void create(University university) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(university);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUniversity(university.getName()) != null) {
                throw new PreexistingEntityException("University " + university + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    //
    public void edit(University university) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            university = em.merge(university);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = university.getName();
                if (findUniversity(id) == null) {
                    throw new NonexistentEntityException("The university with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    //
    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            University university;
            try {
                university = em.getReference(University.class, id);
                university.getName();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The university with id " + id + " no longer exists.", enfe);
            }
            em.remove(university);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    //
    public List<University> findUniversityEntities() {
        return findUniversityEntities(true, -1, -1);
    }
    
    //
    public List<University> findUniversityEntities(int maxResults, int firstResult) {
        return findUniversityEntities(false, maxResults, firstResult);
    }
    
    //
    private List<University> findUniversityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(University.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    //
    public University findUniversity(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(University.class, id);
        } finally {
            em.close();
        }
    }
    
    //
    public int getUniversityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<University> rt = cq.from(University.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
