package main;

import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Scanner;

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
                        mostraMenuPerRuolo(utenteLoggato);
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

    //implementare metodo login(),mostraMenuPerRuolo, Registrazione()
}
