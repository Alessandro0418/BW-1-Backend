package service;

import dao.PuntoEmissioneDao;
import entities.Biglietto;
import entities.PuntoEmissione;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class PuntoEmissioneService {

    private EntityManager em;

    public PuntoEmissioneService(EntityManager em) {
        this.em = em;
    }

    public List<PuntoEmissione> findAll(){
        PuntoEmissioneDao puntoEmissioneDao= new PuntoEmissioneDao(em);
        return puntoEmissioneDao.findAll();

    }

    public Biglietto emettiBiglietto(PuntoEmissione puntoEmissione){
        return new Biglietto(LocalDate.now(), null,false, puntoEmissione);
    }
}
