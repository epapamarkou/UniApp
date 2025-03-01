package eap.uniapp.db;

import eap.uniapp.db.exceptions.NonexistentEntityException;
import eap.uniapp.db.exceptions.PreexistingEntityException;

import java.util.List;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * <p>
 * Η κλάση {@code UniversityJpaController} παρέχει λειτουργικότητα για τη διαχείριση
 * των οντοτήτων {@link University} στη βάση δεδομένων μέσω JPA. Χειρίζεται
 * δημιουργία, επεξεργασία, διαγραφή και αναζητήσεις εγγραφών του πίνακα University.
 * </p>
 * <p>
 * Περιλαμβάνει διάφορες μεθόδους που επιτρέπουν την εύκολη αλληλεπίδραση με την
 * βάση δεδομένων, όπως {@code create}, {@code edit}, {@code destroy}, καθώς και
 * μεθόδους εύρεσης (find) και καταμέτρησης (count).
 * </p>
 * 
 */
public class UniversityJpaController implements Serializable {
    
    //constructor

    /**
     * <p>
     * Δημιουργεί ένα νέο αντικείμενο {@code UniversityJpaController} με το
     * παρεχόμενο {@link EntityManagerFactory}. Μέσω αυτού θα δημιουργούνται
     * {@link EntityManager} για τις διάφορες πράξεις στη βάση δεδομένων.
     * </p>
     * 
     * @param emf Το {@link EntityManagerFactory} που θα χρησιμοποιηθεί
     */
    public UniversityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
        System.out.println("Entity Manager Factory created on UniversityJpaController.");
    }
    
    /**
     * To {@link EntityManagerFactory} που χρησιμοποιείται για τη δημιουργία
     * {@link EntityManager} αντικειμένων.
     */
    private EntityManagerFactory emf = null;
    
    //getter of Entity Manager

    /**
     * <p>
     * Επιστρέφει ένα νέο {@link EntityManager} από το {@code emf}.
     * </p>
     * 
     * @return Ένα {@link EntityManager} για διαχείριση οντοτήτων στη βάση
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    /**
     * <p>
     * Προσθέτει ένα νέο {@link University} στη βάση δεδομένων. Εάν υπάρχει ήδη
     * εγγραφή με ίδιο όνομα (primary key), πετάει {@link PreexistingEntityException}.
     * </p>
     * 
     * @param university Η οντότητα {@link University} που θα αποθηκευτεί
     * @throws PreexistingEntityException Εάν υπάρχει ήδη καταχώριση με το ίδιο κλειδί
     * @throws Exception Γενική εξαίρεση για σφάλματα που μπορεί να προκύψουν
     */
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
    
    /**
     * <p>
     * Ενημερώνει μια υπάρχουσα εγγραφή {@link University} στη βάση δεδομένων
     * κάνοντας merge της μεταβιβασθείσας οντότητας. Εάν το Πανεπιστήμιο με το
     * συγκεκριμένο όνομα δεν υπάρχει, πετάει {@link NonexistentEntityException}.
     * </p>
     * 
     * @param university Η οντότητα {@link University} που θα ενημερωθεί
     * @throws NonexistentEntityException Αν δεν βρεθεί το Πανεπιστήμιο με το δεδομένο id
     * @throws Exception Γενική εξαίρεση για σφάλματα που μπορεί να προκύψουν
     */
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
    
    /**
     * <p>
     * Διαγράφει μία εγγραφή {@link University} από τη βάση δεδομένων. Εάν η
     * οντότητα με το συγκεκριμένο id δεν εντοπιστεί, πετάει
     * {@link NonexistentEntityException}.
     * </p>
     * 
     * @param id Το όνομα (primary key) του Πανεπιστημίου που θα διαγραφεί
     * @throws NonexistentEntityException Εάν δεν βρεθεί η συγκεκριμένη εγγραφή
     */
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
    
    /**
     * <p>
     * Επιστρέφει μια λίστα με όλα τα {@link University} αντικείμενα από τη βάση δεδομένων.
     * </p>
     * 
     * @return Λίστα από όλα τα {@link University} αντικείμενα
     */
    public List<University> findUniversityEntities() {
        return findUniversityEntities(true, -1, -1);
    }
    
    /**
     * <p>
     * Επιστρέφει μια λίστα με {@link University} αντικείμενα, με δυνατότητα
     * ορισμού ορίων για τα αποτελέσματα (π.χ. για σελιδοποίηση).
     * </p>
     * 
     * @param maxResults Μέγιστος αριθμός επιστρεφόμενων εγγραφών
     * @param firstResult Το πρώτο αποτέλεσμα (offset) από όπου θα ξεκινήσει η αναζήτηση
     * @return Μια λίστα με {@link University} αντικείμενα
     */
    public List<University> findUniversityEntities(int maxResults, int firstResult) {
        return findUniversityEntities(false, maxResults, firstResult);
    }
    
    /**
     * <p>
     * Υλοποιεί την λογική για την εύρεση οντοτήτων {@link University} είτε όλων
     * είτε ορισμένου εύρους, αναλόγως των παραμέτρων.
     * </p>
     * 
     * @param all Εάν είναι {@code true}, επιστρέφονται όλα τα αποτελέσματα
     * @param maxResults Μέγιστος αριθμός επιστρεφόμενων εγγραφών (χρησιμοποιείται μόνο αν {@code all} είναι {@code false})
     * @param firstResult Το πρώτο αποτέλεσμα (offset) από όπου θα ξεκινήσει η αναζήτηση (χρησιμοποιείται μόνο αν {@code all} είναι {@code false})
     * @return Μια λίστα με {@link University} αντικείμενα
     */
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
    
    /**
     * <p>
     * Επιστρέφει ένα αντικείμενο {@link University} με βάση το παρεχόμενο id. Εάν
     * δεν βρεθεί, επιστρέφει {@code null} ή πετάει εξαίρεση ανάλογα με την υλοποίηση.
     * </p>
     * 
     * @param id Το όνομα (primary key) του Πανεπιστημίου
     * @return Το αντικείμενο {@link University} ή {@code null} εάν δεν βρεθεί
     * @throws NonexistentEntityException Εάν η εγγραφή δεν υπάρχει
     * @throws Exception Γενική εξαίρεση για σφάλματα που μπορεί να προκύψουν
     */
    public University findUniversity(String id) throws NonexistentEntityException, Exception {
        EntityManager em = getEntityManager();
        try {
            return em.find(University.class, id);
        } finally {
            em.close();
        }
    }
    
    /**
     * <p>
     * Επιστρέφει το συνολικό αριθμό εγγραφών {@link University} που υπάρχουν στη
     * βάση δεδομένων.
     * </p>
     * 
     * @return Ο αριθμός των εγγραφών (ως ακέραιος)
     */
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
