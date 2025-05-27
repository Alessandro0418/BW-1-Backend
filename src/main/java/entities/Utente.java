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

    private String tipo;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Tessera> tessere = new ArrayList<>();

    public Utente(int id, String nome, String tipo, List<Tessera> tessere) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.tessere = tessere;
    }

    public Utente() {
    }

    public Utente(String nome) {
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
                ", tipo='" + tipo + '\'' +
                ", tessere=" + tessere +
                '}';
    }
}
