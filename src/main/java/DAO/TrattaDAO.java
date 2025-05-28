package dao;
import entities.Tratta;
import jakarta.persistence.EntityManager;

public class TrattaDAO {
    private EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }

    public void salva(Tratta tratta) {
        em.getTransaction().begin();
        em.persist(tratta);
        em.getTransaction().commit();
    }

    public Tratta trovaPerId(Long id) {
        return em.find(Tratta.class, id);
    }
}