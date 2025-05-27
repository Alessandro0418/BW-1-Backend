package dao;

import jakarta.persistence.EntityManager;
import entities.Mezzo;
import java.util.List;

public class MezzoDao {

    private EntityManager em;

    public MezzoDao(EntityManager em){
        this.em = em;
    }

    public void salva(Mezzo mezzo){
        em.getTransaction().begin();
        em.persist(mezzo);
        em.getTransaction().commit();
    }

    public Mezzo trovaPerId(Long id){
        return em.find(Mezzo.class, id);
    }

    public void rimuovi(Mezzo mezzo){
        em.getTransaction().begin();
        em.remove(mezzo);
        em.getTransaction().commit();
    }

    public List<Mezzo> trovaTutti(){
        return em.createQuery("Select m From Mezzo m", Mezzo.class).getResultList();
    }
}
