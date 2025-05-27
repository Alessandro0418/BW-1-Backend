package entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class VidimazioneBiglietti {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private long id;

    //Vidimato
    @ManyToOne
    @JoinColumn(name = "biglietto_id", nullable = false)
    private Biglietto biglietto;

    //Mezzo
    @ManyToOne
    @JoinColumn(name = "mezzo_id", nullable = false)
    private Mezzo mezzo;

    private LocalDateTime dataOraVidimazione;

    //Costruttori
    public VidimazioneBiglietti(){}

    public VidimazioneBiglietti(Biglietto biglietto, Mezzo mezzo, LocalDateTime dataOraVidimazione) {
        this.biglietto = biglietto;
        this.mezzo = mezzo;
        this.dataOraVidimazione = dataOraVidimazione;
    }

    //Get e set

    public long getId() {
        return id;
    }

    public Biglietto getBiglietto() {
        return biglietto;
    }

    public void setBiglietto(Biglietto biglietto) {
        this.biglietto = biglietto;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public LocalDateTime getDataOraVidimazione() {
        return dataOraVidimazione;
    }

    public void setDataOraVidimazione(LocalDateTime dataOraVidimazione) {
        this.dataOraVidimazione = dataOraVidimazione;
    }
}
