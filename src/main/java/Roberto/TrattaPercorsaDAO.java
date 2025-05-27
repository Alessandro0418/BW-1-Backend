package Roberto;
import jakarta.persistence.EntityManager;
import java.util.List;

public class TrattaPercorsaDAO {
    private EntityManager em;

    public TrattaPercorsaDAO(EntityManager em) {
        this.em = em;
    }

    public void registraPercorrenza(TrattaPercorsa t) {
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
    }

    public double calcolaMediaTempoPerTratta(Tratta tratta) {
        String jpql = "SELECT AVG(tp.tempoEffettivo) FROM TrattaPercorsa tp WHERE tp.tratta = :tratta";
        Double media = em.createQuery(jpql, Double.class)
                .setParameter("tratta", tratta)
                .getSingleResult();
        return media != null ? media : 0;
    }

    public List<TrattaPercorsa> trovaPercorsiPerTratta(Tratta tratta) {
        String jpql = "SELECT tp FROM TrattaPercorsa tp WHERE tp.tratta = :tratta";
        return em.createQuery(jpql, TrattaPercorsa.class)
                .setParameter("tratta", tratta)
                .getResultList();
    }
}
