package entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Utente {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (length = 20)
    private String nome;
    private String nomeUtente;
    private String password;
    private String ruolo;

    private String tipo;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Tessera> tessere = new ArrayList<>();

    public Utente(int id, String nome, String nomeUtente, String password, String tipo, String ruolo, List<Tessera> tessere) {
        this.id = id;
        this.nome = nome;
        this.nomeUtente = nomeUtente;
        this.password = password;
        this.tipo = tipo;
        this.ruolo = ruolo;
        this.tessere = tessere;
    }

    public Utente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Tessera> getTessere() {
        return tessere;
    }

    public void setTessere(List<Tessera> tessere) {
        this.tessere = tessere;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", nomeUtente='" + nomeUtente + '\'' +
                ", password='" + password + '\'' +
                ", ruolo='" + ruolo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", tessere=" + tessere +
                '}';
    }
}
