package dao;

import entities.Abbonamento;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AbbonamentoDao {
    private EntityManager em;

    public AbbonamentoDao(EntityManager em) {
        this.em = em;
    }

    public void save(Abbonamento abbonamento) {
        em.getTransaction().begin();
        em.persist(abbonamento);
        em.getTransaction().commit();
    }

    public Abbonamento findById(int id) {
        return em.find(Abbonamento.class, id);
    }

    public List<Abbonamento> findAll() {
        return em.createQuery("SELECT a FROM Abbonamento a", Abbonamento.class).getResultList();
    }

    public void delete(Abbonamento abbonamento) {
        em.getTransaction().begin();
        em.remove(abbonamento);
        em.getTransaction().commit();
    }

    public void update(Abbonamento abbonamento) {
        em.getTransaction().begin();
        em.merge(abbonamento);
        em.getTransaction().commit();
    }
}

