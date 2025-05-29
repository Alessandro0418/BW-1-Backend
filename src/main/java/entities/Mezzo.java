package entities;
import Enumeration.StatoMezzo;
import Enumeration.TipoMezzo;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
public class Mezzo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int capienza;

    @Enumerated(EnumType.STRING)
    private TipoMezzo tipo;

    @Enumerated(EnumType.STRING)
    private StatoMezzo statoAttuale;

    //Periodi servizio e manutenzione
    @OneToMany(mappedBy = "mezzo", cascade = CascadeType.ALL)
    private List<PeriodoOperativo> periodiOperativi = new ArrayList<>();

    //Biglietti vidimati
    @OneToMany(mappedBy = "mezzo", cascade = CascadeType.ALL)
    private List<VidimazioneBiglietti> vidimazioni = new ArrayList<>();

    //Costruttori
    public Mezzo(){}

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

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public TipoMezzo getTipo() {
        return tipo;
    }

    public void setTipo(TipoMezzo tipo) {
        this.tipo = tipo;
    }

    public StatoMezzo getStatoAttuale() {
        return statoAttuale;
    }

    public void setStatoAttuale(StatoMezzo statoAttuale) {
        this.statoAttuale = statoAttuale;
    }

    public void aggiungiPeriodo(PeriodoOperativo p){
        periodiOperativi.add(p);
    }

    public List<VidimazioneBiglietti> getVidimazioni(){
        return vidimazioni;
    }

    public void aggiungiVidimazione(VidimazioneBiglietti v) {
        vidimazioni.add(v);
    }
}
