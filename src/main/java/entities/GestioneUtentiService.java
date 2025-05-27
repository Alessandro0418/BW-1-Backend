package entities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class GestioneUtentiService {

    private EntityManager em;

    public GestioneUtentiService(EntityManager em) {
        this.em = em;
    }

    public Utente creaUtente(String nome) {
        Utente u = new Utente(nome);
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        return u;
    }

    public Tessera creaTessera(Utente utente) {
        Tessera tessera = new Tessera();
        tessera.setNumero(UUID.randomUUID().toString());
        tessera.setDataRilascio(LocalDate.now());
        tessera.setDataScadenza(LocalDate.now().plusYears(1));
        tessera.setUtente(utente);

        em.getTransaction().begin();
        em.persist(tessera);
        em.getTransaction().commit();

        return tessera;
    }

    public Abbonamento emettiAbbonamento(Tessera tessera, String tipo) {
        Abbonamento abbonamento = new Abbonamento();
        abbonamento.setTipo(tipo);
        abbonamento.setDataInizio(LocalDate.now());

        if (tipo.equalsIgnoreCase("settimanale")) {
            abbonamento.setDataFine(LocalDate.now().plusWeeks(1));
        } else if (tipo.equalsIgnoreCase("mensile")) {
            abbonamento.setDataFine(LocalDate.now().plusMonths(1));
        } else {
            throw new IllegalArgumentException("Tipo abbonamento non valido");
        }

        abbonamento.setTessera(tessera);

        em.getTransaction().begin();
        em.persist(abbonamento);
        em.getTransaction().commit();

        return abbonamento;
    }

    public boolean isAbbonamentoValido(String numeroTessera) {
        try {
            Tessera tessera = em.createQuery("SELECT t FROM Tessera t WHERE t.numero = :num", Tessera.class)
                    .setParameter("num", numeroTessera)
                    .getSingleResult();

            List<Abbonamento> abbonamenti = tessera.getAbbonamenti();
            LocalDate oggi = LocalDate.now();

            return abbonamenti.stream().anyMatch(a -> oggi.isAfter(a.getDataInizio()) && oggi.isBefore(a.getDataFine()));
        } catch (NoResultException e) {
            return false;
        }
    }
}
