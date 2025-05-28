package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tratte")
public class Tratta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zonaPartenza;
    private String capolinea;
    private int tempoStimato; // in minuti

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZonaPartenza() {
        return zonaPartenza;
    }

    public void setZonaPartenza(String zonaPartenza) {
        this.zonaPartenza = zonaPartenza;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

    public int getTempoStimato() {
        return tempoStimato;
    }

    public void setTempoStimato(int tempoStimato) {
        this.tempoStimato = tempoStimato;
    }

    public Tratta(Long id, String zonaPartenza, String capolinea, int tempoStimato) {
        this.id = id;
        this.zonaPartenza = zonaPartenza;
        this.capolinea = capolinea;
        this.tempoStimato = tempoStimato;
    }
}