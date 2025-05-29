package dao;

import entities.Biglietto;
import entities.Mezzo;
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

    public void update(Biglietto b) {
        em.getTransaction().begin();
        em.merge(b);
        em.getTransaction().commit();
    }

    public void delete(Biglietto b) {
        em.getTransaction().begin();
        em.remove(em.contains(b) ? b : em.merge(b));
        em.getTransaction().commit();
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
        //Trova tutti i biglietti emessi da un determinato punto di emissione (PuntoEmissione)
    public List<Biglietto> findByPuntoEmissione(PuntoEmissione pe) {
        return em.createQuery(
                        "SELECT b FROM Biglietto b WHERE b.puntoEmissione = :pe",
                        Biglietto.class
                )
                .setParameter("pe", pe)
                .getResultList();
    }

    // Trova tutti i biglietti emessi in un dato intervallo di tempo da un determinato punto di emissione
    public List<Biglietto> findByPeriodoAndPuntoEmissione(LocalDate inizio, LocalDate fine, PuntoEmissione pe) {
        return em.createQuery(
                        "SELECT b FROM Biglietto b WHERE b.dataDiEmissione BETWEEN :inizio AND :fine AND b.puntoEmissione = :pe",
                        Biglietto.class
                )
                .setParameter("inizio", inizio)
                .setParameter("fine", fine)
                .setParameter("pe", pe)
                .getResultList();
    }


        //emissione biglietto
    public void salvaBiglietto(PuntoEmissione pe){
        Biglietto b = new Biglietto();
        b.setPuntoEmissione(pe);
        b.setDataDiEmissione(LocalDate.now());
        b.setVidimato(false);
        save(b);

    }


        //conta biglietti convalidati in un determinato periodo
    public long countBigliettiVidimatiNelPeriodo(LocalDate inizio, LocalDate fine){
        return em.createQuery("SELECT COUNT (b) FROM Biglietto b WHERE b.vidimato = true BETWEEN :inizio AND :fine", Long.class)
                .setParameter("inizio", inizio)
                .setParameter("fine", fine)
                .getSingleResult();

    }
        //conta biglietti convalidati per Mezzo per un determinato periodo
    public long countBigliettiVidimatiPerMezzoNelPeriodo(LocalDate inizio, LocalDate fine){
        return em.createQuery("SELECT COUNT (B) FROM Biglietto b WHERE b.vidimato = true AND b.mezzoVidimazione BETWEEN :inizio AND :fine" , Long.class)
                .setParameter("inizio", inizio)
                .setParameter("fine", fine)
                .getSingleResult();
    }



        public String vidimaBiglietto(long id, Mezzo mezzo) {
            Biglietto b = getById(id);
            if (b == null) return "Biglietto non trovato.";
            if (b.isVidimato()) return "Biglietto già vidimato.";

            em.getTransaction().begin();
            b.setVidimato(true);
            b.setMezzoVidimazione(mezzo);
            em.merge(b);
            em.getTransaction().commit();

            return "Biglietto vidimato con successo.";
        }

    }


