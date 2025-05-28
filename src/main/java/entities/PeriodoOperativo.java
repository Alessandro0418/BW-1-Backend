package entities;

import java.time.LocalDate;

import Enumeration.StatoMezzo;
import jakarta.persistence.*;

@Entity
public class PeriodoOperativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzo mezzo;


    private LocalDate dataInizio;
    private LocalDate dataFine;

    @Enumerated(EnumType.STRING)
    private StatoMezzo stato;

    //Costruttore JPA
    public PeriodoOperativo(){
    }

    //Costruttori
    public PeriodoOperativo(Mezzo mezzo, LocalDate dataInizio, LocalDate dataFine, StatoMezzo stato){
        this.mezzo = mezzo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.stato = stato;
    }

    //Getter e setter
    public Long getId() {
        return id;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
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

    public StatoMezzo getStato() {
        return stato;
    }

    public void setStato(StatoMezzo stato) {
        this.stato = stato;
    }
}
