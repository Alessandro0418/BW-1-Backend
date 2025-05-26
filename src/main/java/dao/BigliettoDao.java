package dao;

import entities.Biglietto;
import entities.PuntoEmissione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class BigliettoDao {

    private EntityManager em;

    public BigliettoDao(EntityManager em) {
        this.em = em;
    }

    public void save(Biglietto b) {
        em.getTransaction().begin();
        em.persist(b);
        em.getTransaction().commit();
    }

    public Biglietto getById(Long id) {
        return em.find(Biglietto.class, id);
    }

    public List<Biglietto> getByPeriodo(LocalDate inizio, LocalDate fine) {
        TypedQuery<Biglietto> query = em.createQuery(
                "SELECT b FROM Biglietto b WHERE b.dataDiEmissione BETWEEN :inizio AND :fine",
                Biglietto.class
        );
        return query.setParameter("inizio", inizio)
                .setParameter("fine", fine)
                .getResultList();
    }

    public List<Biglietto> findByPuntoEmissione(PuntoEmissione pe) {
        return em.createQuery(
                        "SELECT b FROM Biglietto b WHERE b.puntoEmissione = :pe",
                        Biglietto.class
                )
                .setParameter("pe", pe)
                .getResultList();
    }

    public List<Biglietto> findByPeriodoAndPuntoEmissione(LocalDate startDate, LocalDate endDate, PuntoEmissione pe) {
        return em.createQuery(
                        "SELECT b FROM Biglietto b WHERE b.dataDiEmissione BETWEEN :startDate AND :endDate AND b.puntoEmissione = :pe",
                        Biglietto.class
                )
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("pe", pe)
                .getResultList();
    }

    public void emettiBiglietto(PuntoEmissione pe){
        Biglietto b = new Biglietto();
        b.setPuntoEmissione(pe);
        b.setDataDiEmissione(LocalDate.now());
        b.setVidimato(false);
        save(b);

    }


}