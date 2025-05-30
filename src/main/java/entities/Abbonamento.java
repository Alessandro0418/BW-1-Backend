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

    @ManyToOne
    @JoinColumn(name = "punto_emissione_id")
    private PuntoEmissione puntoEmissione;

    public Abbonamento() {}

    public Abbonamento(int id, LocalDate dataInizio, LocalDate dataFine, TipoAbbonamento tipoAbbonamento, Tessera tessera, PuntoEmissione puntoEmissione) {
        this.id = id;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.tipoAbbonamento = tipoAbbonamento;
        this.tessera = tessera;
        this.puntoEmissione = puntoEmissione;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDataInizio() { return dataInizio; }
    public void setDataInizio(LocalDate dataInizio) { this.dataInizio = dataInizio; }

    public LocalDate getDataFine() { return dataFine; }
    public void setDataFine(LocalDate dataFine) { this.dataFine = dataFine; }

    public TipoAbbonamento getTipoAbbonamento() { return tipoAbbonamento; }
    public void setTipoAbbonamento(TipoAbbonamento tipoAbbonamento) { this.tipoAbbonamento = tipoAbbonamento; }

    public Tessera getTessera() { return tessera; }
    public void setTessera(Tessera tessera) { this.tessera = tessera; }

    public PuntoEmissione getPuntoEmissione() { return puntoEmissione; }
    public void setPuntoEmissione(PuntoEmissione puntoEmissione) { this.puntoEmissione = puntoEmissione; }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "id=" + id +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", tipoAbbonamento=" + tipoAbbonamento +
                ", tessera=" + tessera +
                ", puntoEmissione=" + (puntoEmissione != null ? puntoEmissione.getNome() : "null") +
                '}';
    }
}