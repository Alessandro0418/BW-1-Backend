package entities;

import Enumeration.TipoUtente;
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

    @Enumerated(EnumType.STRING)
    private TipoUtente tipoUtente;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Tessera> tessere = new ArrayList<>();


    public Utente(int id, String nome, String nomeUtente, String password, TipoUtente tipoUtente, List<Tessera> tessere) {
        this.nome = nome;
        this.nomeUtente = nomeUtente;
        this.password = password;
        this.tipoUtente = tipoUtente;
        this.tessere = tessere;
    }

    public Utente(String nome, String nomeUtente, String password, TipoUtente tipoUtente, List<Tessera> tessere) {
        this.nome = nome;
        this.nomeUtente = nomeUtente;
        this.password = password;
        this.tipoUtente = tipoUtente;
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

    public TipoUtente getTipoUtente() {
        return tipoUtente;
    }

    public void setTipoUtente(TipoUtente tipoUtente) {
        this.tipoUtente = tipoUtente;
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
                ", tipoUtente='" + tipoUtente + '\'' +
                ", tessere=" + tessere +
                '}';
    }
}
