package DAO;

import entities.Tessera;
import jakarta.persistence.EntityManager;

public class TesseraDao {
    private EntityManager em;
    public TesseraDao (EntityManager em){this.em = em;}

    public void save(Tessera tessera){
        em.getTransaction().begin();
        em.persist(tessera);
        em.getTransaction().commit();
    }
    public Tessera findByNumero(String numero) {
        return em.createQuery("SELECT t FROM Tessera t WHERE t.numero = :num", Tessera.class)
                .setParameter("num", numero)
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
}
