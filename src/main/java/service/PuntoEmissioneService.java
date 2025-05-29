package service;

import Enumeration.TipoAbbonamento;
import dao.AbbonamentoDao;
import dao.BigliettoDao;
import dao.PuntoEmissioneDao;
import entities.*;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class PuntoEmissioneService {
    private  PuntoEmissioneDao dao;
    private EntityManager em;

    public PuntoEmissioneService(EntityManager em) {
        this.em = em;
        this.dao = new PuntoEmissioneDao(em);
    }

    public List<PuntoEmissione> findAll() {
        PuntoEmissioneDao puntoEmissioneDao = new PuntoEmissioneDao(em);
        return puntoEmissioneDao.findAll();
    }

    public Biglietto emettiBiglietto(PuntoEmissione puntoEmissione) {
        Biglietto biglietto = new Biglietto();
        biglietto.setDataDiEmissione(LocalDate.now());
        biglietto.setVidimato(false);
        biglietto.setPuntoEmissione(puntoEmissione);

        BigliettoDao bigliettoDao = new BigliettoDao(em);
        bigliettoDao.save(biglietto);

        return biglietto;
    }

    public Abbonamento emettiAbbonamento(PuntoEmissione puntoEmissione, Tessera tessera, TipoAbbonamento tipo) {
        LocalDate dataInizio = LocalDate.now();
        LocalDate dataFine;
        switch (tipo) {
            case SETTIMANALE -> dataFine = dataInizio.plusWeeks(1);
            case MENSILE -> dataFine = dataInizio.plusMonths(1);
            default -> throw new IllegalArgumentException("Tipo di abbonamento non valido");

        }
        Abbonamento abbonamento = new Abbonamento();
        abbonamento.setDataInizio(dataInizio);
        abbonamento.setDataFine(dataFine);
        abbonamento.setTipoAbbonamento(tipo);
        abbonamento.setTessera(tessera);

        new AbbonamentoDao(em).save(abbonamento);

        return abbonamento;
    }

    public void inizializzaPuntiEmissione() {
        if (findAll().isEmpty()) {


            DistributoreAutomatico d = new DistributoreAutomatico();
            d.setNome("Distributore Centrale");



            RivenditoreAutorizzato r = new RivenditoreAutorizzato();
            r.setNome("Edicola Stazione");


            dao.save(d);
            dao.save(r);



            System.out.println("Punti di emissione inizializzati.");
        }
    }
}
