package dao;

import jakarta.persistence.EntityManager;
import entities.PeriodoOperativo;
import entities.Mezzo;
import java.time.LocalDate;
import java.util.List;

public class PeriodoOperativoDao {

    private EntityManager em;

    public PeriodoOperativoDao(EntityManager em){
        this.em = em;
    }

    public void salva(PeriodoOperativo periodo){
        em.getTransaction().begin();
        em.persist(periodo);
        em.getTransaction().commit();
    }

    public PeriodoOperativo trovaPerId(Long id){
        return em.find(PeriodoOperativo.class, id);
    }

    public List<PeriodoOperativo> trovaPerMezzo(Mezzo mezzo) {
        return em.createQuery(
                        "SELECT p FROM PeriodoOperativo p WHERE p.mezzo = :mezzo", PeriodoOperativo.class)
                .setParameter("mezzo", mezzo)
                .getResultList();
    }

    public List<PeriodoOperativo> trovaTraDate(LocalDate inizio, LocalDate fine) {
        return em.createQuery(
                        "SELECT p FROM PeriodoOperativo p WHERE p.dataInizio >= :inizio AND (p.dataFine IS NULL OR p.dataFine <= :fine)",
                        PeriodoOperativo.class)
                .setParameter("inizio", inizio)
                .setParameter("fine", fine)
                .getResultList();
    }
}
