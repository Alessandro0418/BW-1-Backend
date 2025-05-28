package main;

import entities.PuntoEmissione;
import service.PuntoEmissioneService;
import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import service.GestioneUtentiService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
    private static final EntityManager em = emf.createEntityManager();
    private static final Scanner scanner = new Scanner(System.in);
    private static final GestioneUtentiService servizio = new GestioneUtentiService(em);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Benvenuto!");
            System.out.println("1. Login");
            System.out.println("2. Registrazione");
            System.out.print("Scelta: ");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    gestisciLogin();
                    break;
                case "2":
                    gestisciRegistrazione();
                    break;
                default:
                    System.out.println("Scelta non valida. Premi 1 per il login o 2 per la registrazione.");
            }
        }
    }

    private static void gestisciLogin() {
        System.out.print("Nome utente: ");
        String username = scanner.nextLine();

        Utente utente = servizio.getUtenteByNomeUtente(username);

        if (utente == null) {
            System.out.println("Utente non registrato. Vuoi registrarti ora? (s/n)");
            String scelta = scanner.nextLine();
            if (scelta.equalsIgnoreCase("s")) {
                gestisciRegistrazione();
            } else {
                System.out.println("Torno al menu principale.");
            }
        } else {
            // L'utente esiste, chiedi la password finché non è corretta o l'utente vuole uscire
            boolean loginRiuscito = false;

            while (!loginRiuscito) {
                System.out.print("Password: ");
                String password = scanner.nextLine();

                if (utente.getPassword().equals(password)) {
                    System.out.println("Accesso effettuato come " + utente.getTipoUtente());
                    mostraMenuPerTipo(utente);
                    loginRiuscito = true;
                } else {
                    System.out.println("Password errata. Vuoi riprovare? (s/n)");
                    String risposta = scanner.nextLine();
                    if (!risposta.equalsIgnoreCase("s")) {
                        System.out.println("Login annullato.");
                        break;
                    }
                }
            }
        }
    }

    private static void gestisciRegistrazione() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Nome utente: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            // Imposta automaticamente il tipo utente come USER
            Utente nuovo = servizio.registraNuovoUtente(nome, username, password);
            System.out.println("Registrazione completata! Ora puoi fare il login.");
        } catch (RuntimeException e) {
            System.out.println("Errore: " + e.getMessage());
            if (e.getMessage().contains("già in uso")) {
                System.out.println("Reindirizzamento al login...");
                gestisciLogin(); // vai direttamente al login se già registrato
            }
        }
    }

    private static void mostraMenuPerTipo(Utente utente) {

        PuntoEmissioneService puntoEmissioneService = new PuntoEmissioneService(em);
        List<PuntoEmissione> puntiEmissione = puntoEmissioneService.findAll();

        int i = 0;
        System.out.print("Scegliere distribnutire:");
        for (PuntoEmissione puntoEmissione : puntiEmissione) {
            System.out.print(i + ": "+puntoEmissione.getNome());
        }

        String puntoEmissione = scanner.nextLine();

        PuntoEmissione puntoEmissioneScelto = puntiEmissione.get(Integer.parseInt(puntoEmissione));


        if (utente.getTipoUtente().name().equals("ADMIN")) {
            System.out.println("MENU ADMIN");
            // inserisci opzioni admin qui
        } else {
            System.out.println("MENU UTENTE");
            // inserisci opzioni utente semplice qui

            PuntoEmissioneService puntoEmissioneService1 = new PuntoEmissioneService(em);
            puntoEmissioneService1.emettiBiglietto(puntoEmissioneScelto);
        }
    }
}
