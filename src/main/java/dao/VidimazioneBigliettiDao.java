package dao;

import jakarta.persistence.EntityManager;
import entities.VidimazioneBiglietti;
import entities.Mezzo;
import java.time.LocalDateTime;
import java.util.List;

public class VidimazioneBigliettiDao {

    private EntityManager em;

    public VidimazioneBigliettiDao(EntityManager em){
        this.em = em;
    }

    public void salva(VidimazioneBiglietti v){
        em.getTransaction().begin();
        em.persist(v);
        em.getTransaction().commit();
    }

    public VidimazioneBiglietti trovaPerId(Long id){
        return em.find(VidimazioneBiglietti.class, id);
    }

    public List<VidimazioneBiglietti> trovaPerMezzo(Mezzo mezzo){
        return em.createQuery(
                        "SELECT v FROM VidimazioneBiglietto v WHERE v.mezzo = :mezzo", VidimazioneBiglietti.class)
                .setParameter("mezzo", mezzo)
                .getResultList();
    }

    public long contaVidimazioniInPeriodo(LocalDateTime inizio, LocalDateTime fine) {
        return em.createQuery(
                        "SELECT COUNT(v) FROM VidimazioneBiglietto v WHERE v.dataOraVidimazione BETWEEN :inizio AND :fine", Long.class)
                .setParameter("inizio", inizio)
                .setParameter("fine", fine)
                .getSingleResult();
    }
}
