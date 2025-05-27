package entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")

public abstract class PuntoEmissione {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    @OneToMany(mappedBy = "puntoEmissione")
    private List<Biglietto> biglietti;

    public PuntoEmissione() {
    }

    public PuntoEmissione( String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Biglietto> getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(List<Biglietto> biglietti) {
        this.biglietti = biglietti;
    }

    @Override
    public String toString() {
        return "PuntoEmissione{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", biglietti=" + biglietti +
                '}';
    }
}
