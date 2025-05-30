package dao;

import entities.Tessera;
import entities.Utente;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;

public class TesseraDao {
    private EntityManager em;
    public TesseraDao (EntityManager em){this.em = em;}

    public void save(Tessera tessera){
        em.getTransaction().begin();
        em.persist(tessera);
        em.getTransaction().commit();
    }
    public Tessera findById(int id) {
        return em.createQuery("SELECT t FROM Tessera t WHERE t.id = :id", Tessera.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Tessera findById(Long id) {
        return em.find(Tessera.class, id);
    }

    public void delete(Tessera tessera) {
        em.getTransaction().begin();
        em.remove(em.contains(tessera) ? tessera : em.merge(tessera));
        em.getTransaction().commit();
    }

    public Tessera getTesseraByUtente(Utente utente) {
        return em.createQuery("SELECT t FROM Tessera t WHERE t.utente = :utente", Tessera.class)
                .setParameter("utente", utente)
                .getSingleResult();
    }

    public void rinnovaTessera(Tessera tessera) {
        LocalDate oggi = LocalDate.now();
        LocalDate scadenzaAttuale = tessera.getDataScadenza();

        // Se scaduta, rinnova da oggi. Altrimenti prolunga da data esistente.
        LocalDate nuovaScadenza = scadenzaAttuale.isBefore(oggi)
                ? oggi.plusYears(1)
                : scadenzaAttuale.plusYears(1);

        tessera.setDataScadenza(nuovaScadenza);
        em.getTransaction().begin();
        em.merge(tessera);
        em.getTransaction().commit();
    }
}
