package entities;

import Enumeration.TipoAbbonamento;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Abbonamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    @Column(name = "data_inizio")
    private LocalDate dataInizio;
    @Column(name = "data_fine")
    private LocalDate dataFine;
    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipoAbbonamento;

    @ManyToOne
    @JoinColumn(name = "tessera_id")
    private Tessera tessera;

    public Abbonamento(int id, LocalDate dataInizio, LocalDate dataFine, TipoAbbonamento tipoAbbonamento, Tessera tessera) {
        this.id = id;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.tipoAbbonamento = tipoAbbonamento;
        this.tessera = tessera;
    }

    public Abbonamento(int id) {
        this.id = id;
    }

    public Abbonamento() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }

    public void setTipoAbbonamento(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    @Override
    public String toString() {
        return "Abbonamneto{" +
                "id=" + id +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", tipo='" + tipoAbbonamento + '\'' +
                ", tessera=" + tessera +
                '}';
    }
}
