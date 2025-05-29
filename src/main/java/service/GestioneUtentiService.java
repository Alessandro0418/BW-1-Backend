package service;

import Enumeration.TipoUtente;
import dao.UtenteDAO;
import entities.Abbonamento;
import entities.Biglietto;
import entities.Tessera;
import entities.Utente;
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

    public Utente creaUtente(int id, String nome, String nomeUtente, String password, TipoUtente tipoUtente, List <Tessera> tessere) {
        Utente u = new Utente(id,nome,nomeUtente,password,tipoUtente, tessere);
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        return u;
    }

    public void salvaUtente(Utente utente) {
        UtenteDAO utenteDAO = new UtenteDAO(em);
        utenteDAO.salva(utente);
    }


    public Utente getUtenteByNomeUtente(String nomeUtente){
        UtenteDAO dao = new UtenteDAO(em);
        return dao.getUtenteByNomeUtente(nomeUtente);
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

    public Utente login(String nomeUtente, String password) {
        Utente utente = getUtenteByNomeUtente(nomeUtente);

        if (utente == null || !utente.getPassword().equals(password)) {
            return null; // login fallito
        }

        return utente; // login riuscito
    }

    public Utente registraNuovoUtente(String nome, String nomeUtente, String password) {
        Utente esistente = getUtenteByNomeUtente(nomeUtente);

        if (esistente != null) {
            throw new RuntimeException("Nome utente già in uso.");
        }

        Utente nuovo = new Utente(nome, nomeUtente, password, TipoUtente.USER, null);
        salvaUtente(nuovo);
        return nuovo;
    }


}
