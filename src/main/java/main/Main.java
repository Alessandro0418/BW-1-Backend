package main;

import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");

    EntityManager entityManager = emf.createEntityManager();
        EntityManager em = emf.createEntityManager();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleziona un opzione:");
        System.out.println("1. Login");
        System.out.println("2. Registrati");
        String scelta = scanner.nextLine();


        switch (scelta) {
            case "1" :
                System.out.println("Nome Utente ");
                String nomeUtente = scanner.nextLine();
                System.out.println("password");
                String password = scanner.nextLine();
                public String datiUtente(nomeUtente, password){
                    em.createQuery("SELECT COUNT(u) FROM Utente u WHERE u.nome = :nome", Utente.class)
                            .setParameter("nomeUtente", nomeUtente)
                            .setParameter("password", password)
                            .getSingleResult();
            }

                if (query.getSingleResult() > 0) {
                    System.out.println("Nome utente già in uso.");
                    return null;
                }
        }


    }
}
