package dao;

import entities.Biglietto;
import entities.DistributoreAutomatico;
import entities.PuntoEmissione;
import entities.RivenditoreAutorizzato;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PuntoEmissioneDao {

    public EntityManager em;

    public PuntoEmissioneDao(EntityManager em) {
        this.em = em;
    }


    public void save(PuntoEmissione pe) {
        em.persist(pe);

    }

    public PuntoEmissione getById(Long id) {
        return em.find(PuntoEmissione.class, id);
    }

    public void update(PuntoEmissione pe) {
        em.getTransaction().begin();
        em.merge(pe);
        em.getTransaction().commit();
    }

    public void delete(PuntoEmissione pe) {
        em.getTransaction().begin();
        em.remove(em.contains(pe) ? pe : em.merge(pe));
        em.getTransaction().commit();
    }


    // trova distributori attivi
    public List<DistributoreAutomatico> findDistributore(DistributoreAutomatico da) {
        return em.createQuery(
                        "SELECT da FROM DistributoreAutomatico da WHERE da.servizio = true = :da",
                        DistributoreAutomatico.class
                )
                .setParameter("da", da)
                .getResultList();
    }

    //verificare se uno specifico distributore è attivo

    public boolean isDistributoreAttivo(DistributoreAutomatico da){
        DistributoreAutomatico trovato= em.find(DistributoreAutomatico.class, da.getId());
                return trovato != null && trovato.isServizio();
    }

    //restituise tutti i punti di emissione
    public List<PuntoEmissione> findAll() {
        return em.createQuery("SELECT pe FROM PuntoEmissione pe", PuntoEmissione.class)
                .getResultList();
    }

    //restituise tutti i rivenditori autorizzati
    public List<RivenditoreAutorizzato> findAllRivendtori() {
        return em.createQuery("SELECT ra FROM RivenditoreAutorizzato ra", RivenditoreAutorizzato.class)
                .getResultList();
    }
}
