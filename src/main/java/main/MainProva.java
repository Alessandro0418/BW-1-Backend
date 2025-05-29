package main;

import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import Enumeration.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
    private static final EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {

        while (true) {
            System.out.println("Seleziona un'opzione:");
            System.out.println("1. Login");
            System.out.println("2. Registrati");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    Utente utenteLoggato = login();
                    if (utenteLoggato != null) {
                        mostraMenuPerTipo(utenteLoggato);
                    }
                    break;
                case "2":
                    registrazione();
                    break;
                default:
                    System.out.println("Scelta non valida");
            }
        }
    }

    public static Utente login(){
        System.out.println("Nome Utente: ");
        String userName = scanner.nextLine();
        System.out.println("password: ");
        String pass = scanner.nextLine();

        TypedQuery<Utente> query = em.createQuery("SELECT u FROM Utente u WHERE u.nomeUtente = :userName AND u.password = :pass", Utente.class)
                .setParameter("userName", userName)
                .setParameter("pass", pass);
        List<Utente> risultati = query.getResultList();

        if (risultati.isEmpty()) {
            System.out.println("Utente non trovato o credenziali errate.");
            return null;
        } else {
            return risultati.get(0);
        }
    }

    //implementare metodo login(),mostraMenuPerTipo, Registrazione()

    public static void mostraMenuPerTipo(Utente utente){
        if (utente.isAdmin()) {
            mostraMenuAdmin();
        } else {
            mostraMenuUtente();
        }
    }

    //Menu Admin
    public static void mostraMenuAdmin(){
        while (true){
            System.out.println("1. Inserisci un nuovo mezzo");
            System.out.println("2. Cambia stato di un mezzo");
            System.out.println("3. Visualizza mezzo in servizio");
            System.out.println("4. Torna indietro");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    inserisciMezzo();
                    break;
                case "2":
                    cambiaStatoMezzo();
                    break;
                case "3":
                    visualizzaMezziInServizio();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }

    //Menu Utente
    public static void mostraMenuUtente() {
        while (true) {
            System.out.println("1. Vidimare un biglietto");
            System.out.println("2. Esci");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    vidimareBiglietto();
                    break;
                case "2":
                    return;
                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }

    //Vidimazione
    public static void vidimareBiglietto(){
        System.out.println("Inserisci ID biglietto: ");
        Long idBiglietto = Long.parseLong(scanner.nextLine());

        System.out.println("Inserisci ID mezzo:");
        Long idMezzo = Long.parseLong(scanner.nextLine());

        em.getTransaction().begin();
        try{
            Biglietto biglietto = em.find(Biglietto.class, idBiglietto);
            Mezzo mezzo = em.find(Mezzo.class, idMezzo);

            if (biglietto == null || mezzo == null){
                System.out.println("Biglietto o mezzo non trovati.");
                em.getTransaction().rollback();
                return;
            }

            VidimazioneBiglietti vid = new VidimazioneBiglietti();
            vid.setBiglietto(biglietto);
            vid.setMezzo(mezzo);
            vid.setDataOraVidimazione(LocalDateTime.now());

            em.persist(vid);
            em.getTransaction().commit();
            System.out.println("Biglietto vidimato con successo.");
        } catch (Exception e){
            em.getTransaction().rollback();
            System.out.println("Errore nella vidimazione: " + e.getMessage());
        }
    }

    //Inserisci mezzo
    public static void inserisciMezzo(){
        System.out.println("Tipo mezzo: ");
        String tipo = scanner.nextLine().toLowerCase();

        System.out.println("Capienza: ");
        int capienza = Integer.parseInt(scanner.nextLine());

        em.getTransaction().begin();
        try {
            Mezzo mezzo = new Mezzo();
            mezzo.setTipo(TipoMezzo.valueOf(tipo));
            mezzo.setCapienza(capienza);
            mezzo.setStatoAttuale(StatoMezzo.IN_SERVIZIO);
            em.persist(mezzo);
            em.getTransaction().commit();
            System.out.println("Mezzo inserito.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Errore: " + e.getMessage());
        }
    };

    //Cambia stato mezzo
    public static void cambiaStatoMezzo() {
        System.out.print("ID del mezzo: ");
        Long id = Long.parseLong(scanner.nextLine());

        System.out.print("Nuovo stato (IN_SERVIZIO / IN_MANUTENZIONE): ");
        String statoInput = scanner.nextLine().toUpperCase();

        em.getTransaction().begin();
        try {
            Mezzo mezzo = em.find(Mezzo.class, id);
            if (mezzo == null) {
                System.out.println("Mezzo non trovato.");
                em.getTransaction().rollback();
                return;
            }

            StatoMezzo nuovoStato = StatoMezzo.valueOf(statoInput);

            //Chiudere periodo operativo
            TypedQuery<PeriodoOperativo> query = em.createQuery(
                    "SELECT p FROM PeriodoOperativo p WHERE p.mezzo = :mezzo AND p.dataFine IS NULL",
                    PeriodoOperativo.class
            );
            query.setParameter("mezzo", mezzo);
            List<PeriodoOperativo> periodiAperti = query.getResultList();

            if (!periodiAperti.isEmpty()) {
                PeriodoOperativo corrente = periodiAperti.get(0);
                corrente.setDataFine(LocalDate.now());
            }

            //Crea nuovo periodo
            PeriodoOperativo nuovoPeriodo = new PeriodoOperativo();
            nuovoPeriodo.setMezzo(mezzo);
            nuovoPeriodo.setStato(nuovoStato);
            nuovoPeriodo.setDataInizio(LocalDate.now());

            em.persist(nuovoPeriodo);

            //Aggiorna stato
            mezzo.setStatoAttuale(nuovoStato);

            em.getTransaction().commit();
            System.out.println("Stato del mezzo aggiornato e periodo registrato.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Errore durante il cambio di stato: " + e.getMessage());
        }
    }



    //Visualizza mezzi in servizio
    public static void visualizzaMezziInServizio(){};

}
