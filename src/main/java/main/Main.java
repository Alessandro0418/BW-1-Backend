package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");

    EntityManager entityManager = emf.createEntityManager();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleziona un opzione:");
        System.out.println("1. Login");
        System.out.println("2. Registrati");


    }
}
