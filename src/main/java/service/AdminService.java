package service;

import entities.*;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class AdminService {
    private EntityManager em;

    public AdminService(EntityManager em) {
        this.em = em;
    }

    public List<Biglietto> getBigliettiPerPuntoEmissione(PuntoEmissione punto) {
        return em.createQuery("SELECT b FROM Biglietto b WHERE b.puntoEmissione = :punto", Biglietto.class)
                .setParameter("punto", punto)
                .getResultList();
    }

    public List<Abbonamento> getAbbonamentiPerPuntoEmissione(PuntoEmissione punto) {
        return em.createQuery("SELECT a FROM Abbonamento a WHERE a.puntoEmissione = :punto", Abbonamento.class)
                .setParameter("punto", punto)
                .getResultList();
    }

    public List<Biglietto> getBigliettiInPeriodo(LocalDate inizio, LocalDate fine) {
        return em.createQuery("SELECT b FROM Biglietto b WHERE b.dataDiEmissione BETWEEN :inizio AND :fine", Biglietto.class)
                .setParameter("inizio", inizio)
                .setParameter("fine", fine)
                .getResultList();
    }

    public List<Abbonamento> getAbbonamentiInPeriodo(LocalDate inizio, LocalDate fine) {
        return em.createQuery("SELECT a FROM Abbonamento a WHERE a.dataInizio BETWEEN :inizio AND :fine", Abbonamento.class)
                .setParameter("inizio", inizio)
                .setParameter("fine", fine)
                .getResultList();
    }

    public List<Biglietto> getBigliettiVidimatiPerMezzo(Mezzo mezzo) {
        return em.createQuery("SELECT b FROM Biglietto b WHERE b.mezzoVidimazione = :mezzo", Biglietto.class)
                .setParameter("mezzo", mezzo)
                .getResultList();
    }

    public double calcolaTempoMedioTratta(Tratta tratta) {
        List<TrattaPercorsa> percorrenze = em.createQuery(
                        "SELECT tp FROM TrattaPercorsa tp WHERE tp.tratta = :tratta AND tp.tempoEffettivo IS NOT NULL", TrattaPercorsa.class)
                .setParameter("tratta", tratta)
                .getResultList();

        return percorrenze.stream()
                .mapToDouble(tp -> tp.getTempoEffettivo())
                .average()
                .orElse(0);
    }

    public List<Mezzo> getMezziInManutenzione() {
        return em.createQuery("SELECT m FROM Mezzo m WHERE m.statoAttuale = IN_MANUTENZIONE", Mezzo.class).getResultList();
    }

    public List<Mezzo> getMezziInServizio() {
        return em.createQuery("SELECT m FROM Mezzo m WHERE m.statoAttuale = IN_SERVIZIO", Mezzo.class).getResultList();
    }

    public List<Tratta> getTutteLeTratte() {
        return em.createQuery("SELECT t FROM Tratta t", Tratta.class).getResultList();
    }

    public List<TrattaPercorsa> getPercorrenzeTratta(Tratta tratta) {
        return em.createQuery("SELECT tp FROM TrattaPercorsa tp WHERE tp.tratta = :tratta", TrattaPercorsa.class)
                .setParameter("tratta", tratta)
                .getResultList();
    }
}