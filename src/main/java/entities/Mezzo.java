package entities;
import java.util.*;

import java.util.ArrayList;
import java.util.List;

public class Mezzo {
    private long id;
    private int capienza;
    private TipoMezzo tipo;
    private StatoMezzo statoAttuale;

    //Periodi servizio e manutenzione
    private List<PeriodoOperativo> periodiOperativi = new ArrayList<>();

    //Biglietti vidimati
    private List<VidimazioneBiglietti> vidimazioni = new ArrayList<>();

    //Costruttori
    public Mezzo(){

    }

    public Mezzo(int capienza, TipoMezzo tipo) {
        this.capienza = capienza;
        this.tipo = tipo;
        this.statoAttuale = StatoMezzo.IN_SERVIZIO;
    }

    //Getter e setter

    public long getId() {
        return id;
    }

    public int getCapienza() {
        return capienza;
    }

    public StatoMezzo getStatoAttuale() {
        return statoAttuale;
    }

    public List<PeriodoOperativo> getPeriodiOperativi() {
        return periodiOperativi;
    }

    public List<VidimazioneBiglietti> getVidimazioni() {
        return vidimazioni;
    }

    public TipoMezzo getTipo() {
        return tipo;
    }
}
