package entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="biglietti")

public class Biglietto {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate dataDiEmissione;
    @ManyToOne
    private Mezzo mezzoVidimazione;
    private boolean vidimato=false;
    @ManyToOne
    private PuntoEmissione puntoEmissione;

    public Biglietto() {
    }

    public Biglietto(Long id, LocalDate dataDiEmissione, Mezzo mezzoVidimazione, boolean vidimato, PuntoEmissione puntoEmissione) {
        this.id = id;
        this.dataDiEmissione = dataDiEmissione;
        this.mezzoVidimazione = mezzoVidimazione;
        this.vidimato = vidimato;
        this.puntoEmissione = puntoEmissione;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataDiEmissione() {
        return dataDiEmissione;
    }

    public void setDataDiEmissione(LocalDate dataDiEmissione) {
        this.dataDiEmissione = dataDiEmissione;
    }

    public Mezzo getMezzoVidimazione() {
        return mezzoVidimazione;
    }

    public void setMezzoVidimazione(Mezzo mezzoVidimazione) {
        this.mezzoVidimazione = mezzoVidimazione;
    }

    public boolean isVidimato() {
        return vidimato;
    }

    public void setVidimato(boolean vidimato) {
        this.vidimato = vidimato;
    }

    public PuntoEmissione getPuntoEmissione() {
        return puntoEmissione;
    }

    public void setPuntoEmissione(PuntoEmissione puntoEmissione) {
        this.puntoEmissione = puntoEmissione;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "id=" + id +
                ", dataDiEmissione=" + dataDiEmissione +
                ", mezzoVidimazione=" + mezzoVidimazione +
                ", vidimato=" + vidimato +
                ", puntoEmissione=" + puntoEmissione +
                '}';
    }
}
