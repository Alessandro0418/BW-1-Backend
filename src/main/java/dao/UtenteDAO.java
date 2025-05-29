package dao;
import entities.Biglietto;
import entities.Tratta;
import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class UtenteDAO {
    private EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    public void salva(Utente utente) {
        em.getTransaction().begin();
        em.persist(utente);
        em.getTransaction().commit();


    }

    public Utente getById(Long id) {
        return em.find(Utente.class, id);
    }

    public void update(Utente u) {
        em.getTransaction().begin();
        em.merge(u);
        em.getTransaction().commit();
    }

    public void delete(Utente u) {
        em.getTransaction().begin();
        em.remove(em.contains(u) ? u : em.merge(u));
        em.getTransaction().commit();
    }

    public Utente getUtenteByNomeUtente(String nomeUtente) {
        try {
            return em.createQuery("SELECT u FROM Utente u WHERE u.nomeUtente = :nomeUtente", Utente.class)
                    .setParameter("nomeUtente", nomeUtente)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<Utente> getAll() {
        return em.createQuery("SELECT u FROM Utente u", Utente.class).getResultList();
    }
}
