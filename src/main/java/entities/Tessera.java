package entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tessera {
   @Id

   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String numero;

    @Column(name = "data_rilascio")
    private LocalDate dataRilascio;

    @Column(name = "data_scadenza")
    private LocalDate dataScadenza;

@ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @OneToMany(mappedBy = "tessera", cascade = CascadeType.ALL)
    private List<Abbonamento> abbonamenti = new ArrayList<>();

    public Tessera(int id, String numero, LocalDate dataRilascio, LocalDate dataScadenza, Utente utente, List<Abbonamento> abbonamenti) {
        this.id = id;
        this.numero = numero;
        this.dataRilascio = dataRilascio;
        this.dataScadenza = dataScadenza;
        this.utente = utente;
        this.abbonamenti = abbonamenti;
    }

    public Tessera() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDataRilascio() {
        return dataRilascio;
    }

    public void setDataRilascio(LocalDate dataRilascio) {
        this.dataRilascio = dataRilascio;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public List<Abbonamento> getAbbonamenti() {
        return abbonamenti;
    }

    public void setAbbonamenti(List<Abbonamento> abbonamenti) {
        this.abbonamenti = abbonamenti;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", dataRilascio=" + dataRilascio +
                ", dataScadenza=" + dataScadenza +
                ", utente=" + utente +
                ", abbonamenti=" + abbonamenti +
                '}';
    }
}
