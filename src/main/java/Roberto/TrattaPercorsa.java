package Roberto;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tratte_percorse")
public class TrattaPercorsa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Mezzo mezzo;

    @ManyToOne
    private Tratta tratta;

    private LocalDate data;
    private int tempoEffettivo; // in minuti

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getTempoEffettivo() {
        return tempoEffettivo;
    }

    public void setTempoEffettivo(int tempoEffettivo) {
        this.tempoEffettivo = tempoEffettivo;
    }

    public TrattaPercorsa(Long id, Mezzo mezzo, Tratta tratta, LocalDate data, int tempoEffettivo) {
        this.id = id;
        this.mezzo = mezzo;
        this.tratta = tratta;
        this.data = data;
        this.tempoEffettivo = tempoEffettivo;
    }
}
