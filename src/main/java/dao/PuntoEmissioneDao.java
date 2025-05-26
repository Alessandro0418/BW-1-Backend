package dao;

import jakarta.persistence.EntityManager;

public class PuntoEmissioneDao {

    public EntityManager em;

    public PuntoEmissioneDao(EntityManager em) {
        this.em = em;
    }
}
