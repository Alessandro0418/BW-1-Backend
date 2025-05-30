package main;

import Enumeration.TipoAbbonamento;
import Enumeration.TipoMezzo;
import dao.BigliettoDao;
import dao.MezzoDao;
import dao.TesseraDao;
import entities.*;
import service.*;
import service.PuntoEmissioneService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import service.GestioneUtentiService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
    private static final EntityManager em = emf.createEntityManager();
    private static final Scanner scanner = new Scanner(System.in);
    private static final GestioneUtentiService servizio = new GestioneUtentiService(em);
    private static TesseraDao tesseraDao;
    private static BigliettoDao bigliettoDao;
    private static MezzoDao mezzoDao;




    public static void main(String[] args) {
        PuntoEmissioneService pes = new PuntoEmissioneService(em);
        pes.inizializzaPuntiEmissione();
        tesseraDao = new TesseraDao(em);
        bigliettoDao = new BigliettoDao(em);
        mezzoDao = new MezzoDao(em);

        MezzoDao mezzoDao = new MezzoDao(em);

        Mezzo tram = new Mezzo();
        tram.setTipo(TipoMezzo.TRAM);
        mezzoDao.salva(tram);

        Mezzo autobus = new Mezzo();
        autobus.setTipo(TipoMezzo.AUTOBUS);
        mezzoDao.salva(autobus);

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
        System.out.print("Scegliere punto emissione attivi/in servizio : ");
        for (PuntoEmissione puntoEmissione : puntiEmissione) {
            System.out.print(i + ": " + puntoEmissione.getNome());
            i++;
        }

        String puntoEmissione = scanner.nextLine();

        PuntoEmissione puntoEmissioneScelto = puntiEmissione.get(Integer.parseInt(puntoEmissione));


        if (utente.getTipoUtente().name().equals("ADMIN")) {
            System.out.println("MENU ADMIN");

            if (utente.getTipoUtente().name().equals("ADMIN")) {
                System.out.println("MENU ADMIN");

                while (true) {
                    System.out.println("\n--- MENU ADMIN ---");
                    System.out.println("1. Visualizza biglietti/abbonamenti emessi per punto di emissione");
                    System.out.println("2. Visualizza biglietti/abbonamenti emessi in un periodo");
                    System.out.println("3. Visualizza biglietti vidimati su un mezzo o in un periodo");
                    System.out.println("4. Calcola tempo medio di percorrenza di una tratta da parte di un mezzo");
                    System.out.println("5. Visualizza mezzi in manutenzione o in servizio");
                    System.out.println("6. Visualizza tratte e percorrenze");
                    System.out.println("0. Esci");
                    System.out.print("Scelta: ");
                    String sceltaAdmin = scanner.nextLine();

                    AdminService adminService = new AdminService(em);

                    switch (sceltaAdmin) {
                        case "1":
                            List<Biglietto> biglietti = adminService.getBigliettiPerPuntoEmissione(puntoEmissioneScelto);
                            List<Abbonamento> abbonamenti = adminService.getAbbonamentiPerPuntoEmissione(puntoEmissioneScelto);
                            System.out.println("Biglietti emessi:");
                            biglietti.forEach(System.out::println);
                            System.out.println("Abbonamenti emessi:");
                            abbonamenti.forEach(System.out::println);
                            break;

                        case "2":
                            System.out.print("\nRicerca: \nInserisci data inizio (yyyy-mm-dd): ");
                            LocalDate inizio = LocalDate.parse(scanner.nextLine());
                            System.out.print("Inserisci data fine (yyyy-mm-dd): ");
                            LocalDate fine = LocalDate.parse(scanner.nextLine());
                            List<Biglietto> bigliettiPeriodo = adminService.getBigliettiInPeriodo(inizio, fine);
                            List<Abbonamento> abbonamentiPeriodo = adminService.getAbbonamentiInPeriodo(inizio, fine);
                            System.out.println("Biglietti emessi nel periodo:");
                            bigliettiPeriodo.forEach(System.out::println);
                            System.out.println("Abbonamenti emessi nel periodo:");
                            abbonamentiPeriodo.forEach(System.out::println);
                            break;

                        case "3":
                            System.out.print("Inserisci ID del mezzo: ");
                            Long mezzoId = Long.parseLong(scanner.nextLine());
                            Mezzo mezzo = em.find(Mezzo.class, mezzoId);
                            if (mezzo != null) {
                                List<Biglietto> bigliettiVidimati = adminService.getBigliettiVidimatiPerMezzo(mezzo);
                                bigliettiVidimati.forEach(System.out::println);
                            } else {
                                System.out.println("Mezzo non trovato.");
                            }
                            break;

                        case "4":
                            System.out.print("Inserisci ID tratta: ");
                            Long trattaId = Long.parseLong(scanner.nextLine());
                            Tratta tratta = em.find(Tratta.class, trattaId);
                            if (tratta != null) {
                                double tempoMedio = adminService.calcolaTempoMedioTratta(tratta);
                                System.out.println("Tempo medio di percorrenza: " + tempoMedio + " minuti");
                            } else {
                                System.out.println("Tratta non trovata.");
                            }
                            break;

                        case "5":
                            List<Mezzo> inManutenzione = adminService.getMezziInManutenzione();
                            List<Mezzo> inServizio = adminService.getMezziInServizio();
                            System.out.println("Mezzi in manutenzione:");
                            inManutenzione.forEach(System.out::println);
                            System.out.println("Mezzi in servizio:");
                            inServizio.forEach(System.out::println);
                            break;

                        case "6":
                            List<Tratta> tratte = adminService.getTutteLeTratte();
                            for (Tratta t : tratte) {
                                System.out.println("Tratta: " + t);
                                List<TrattaPercorsa> percorrenze = adminService.getPercorrenzeTratta(t);
                                percorrenze.forEach(System.out::println);
                            }
                            break;
                    }
                }
            }


        } else {
            System.out.println("MENU UTENTE");

            while (true) {
                System.out.println("--- MENU UTENTE ---");
                System.out.println("1. Acquista biglietto");
                System.out.println("2. Acquista abbonamento");
                System.out.println("3. Verifica validità abbonamento");
                System.out.println("4. Richiedi o rinnova tessera");
                System.out.println("5. Vidima Biglietto");
                System.out.println("0. Esci");
                System.out.print("Scelta: ");
                String scelta = scanner.nextLine();



                switch (scelta) {
                    case "1":
                        PuntoEmissioneService puntoEmissioneService1 = new PuntoEmissioneService(em);
                        Biglietto biglietto = puntoEmissioneService1.emettiBiglietto(puntoEmissioneScelto);
                        System.out.println("Biglietto emesso con successo!");
                        System.out.println("ID: " + biglietto.getId() + ", Data: " + biglietto.getDataDiEmissione());
                        break;

                    case "2":
                        System.out.println("1. Crea una tessera");
                        System.out.println("2. Crea un abbonamento");
                        String sceltaUtente = scanner.nextLine();

                        switch (sceltaUtente) {
                            case "1":
                                servizio.creaTessera(utente);
                                System.out.println("Tessera creata con successo.");
                                break;

                            case "2":

                                Tessera tesseraScelta = utente.getTessere().get(0);


                                System.out.println("Scegli tipo abbonamento: 1. Settimanale  2. Mensile");
                                String tipoInput = scanner.nextLine();
                                TipoAbbonamento tipoScelto;

                                if (tipoInput.equals("1")) {
                                    tipoScelto = TipoAbbonamento.SETTIMANALE;
                                } else if (tipoInput.equals("2")) {
                                    tipoScelto = TipoAbbonamento.MENSILE;
                                } else {
                                    System.out.println("Tipo abbonamento non valido.");
                                    break;
                                }


                                puntoEmissioneService = new PuntoEmissioneService(em);
                                Abbonamento abbonamento = puntoEmissioneService.emettiAbbonamento(puntoEmissioneScelto, tesseraScelta, tipoScelto);
                                System.out.println("Abbonamento emesso con successo! ID: " + abbonamento.getId());
                                break;

                            default:
                                System.out.println("Scelta non valida.");
                        }
                        break;

                    case "3":
                        List<Abbonamento> abbonamenti = utente.getTessere().stream()
                                .flatMap(t -> t.getAbbonamenti().stream())
                                .toList();

                        if (abbonamenti.isEmpty()) {
                            System.out.println("Nessun abbonamento trovato.");
                            break;
                        }

                        for (Abbonamento ab : abbonamenti) {
                            boolean valido = ab.isValido();
                            System.out.println("Abbonamento ID: " + ab.getId() + " - Tipo: " + ab.getTipoAbbonamento()
                                    + " - Emesso il: " + ab.getDataInizio() + " - Valido: " + (valido ? "SÌ" : "NO"));
                        }
                        break;

                    case "4":
                        System.out.println("Inserisci il numero della tessera da rinnovare:");
                        String numero = scanner.nextLine();
                        Tessera tessera = tesseraDao.findByNumero(numero);
                        if (tessera != null) {
                            tesseraDao.rinnovaTessera(tessera);
                            System.out.println("Tessera rinnovata con successo!");
                        } else if (tessera == null){
                            System.out.println("Tessera non trovata.");
                        } else{
                            System.out.println("Tessera ancora valida");
                        }
                        break;

                    case "5":
                        System.out.print("Inserisci ID biglietto da vidimare: ");
                        String idInput = scanner.nextLine();

                        try {
                            long idBiglietto = Long.parseLong(idInput);

                            // Mostro i mezzi disponibili con ID e tipo
                            List<Mezzo> mezzi = mezzoDao.trovaTutti();
                            System.out.println("Mezzi disponibili:");
                            for (Mezzo m : mezzi) {
                                System.out.println("ID: " + m.getId() + " - Tipo: " + m.getTipo().name());
                            }

                            System.out.print("Inserisci ID del mezzo per la vidimazione: ");
                            long idMezzo = Long.parseLong(scanner.nextLine());
                            Mezzo mezzo = mezzoDao.trovaPerId(idMezzo);

                            if (mezzo == null) {
                                System.out.println("Mezzo non trovato.");
                            } else {
                                String risultato = bigliettoDao.vidimaBiglietto(idBiglietto, mezzo);
                                System.out.println(risultato);
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Input non valido. Inserisci un numero valido.");
                        }
                        break;

                    case "0":
                        System.out.println("Uscita dal menu utente.");
                        return; // esci dal menu utente (puoi anche fare break se vuoi far tornare al menu principale)

                    default:
                        System.out.println("Scelta non valida.");
                }

            }

        }
    }
}