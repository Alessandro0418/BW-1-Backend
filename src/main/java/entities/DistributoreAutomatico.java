package entities;

import jakarta.persistence.Entity;

@Entity
public class DistributoreAutomatico extends PuntoEmissione {

    private boolean servizio = true; // true = attivo, false = fuori servizio

    public DistributoreAutomatico() {
    }

    public DistributoreAutomatico(String nome, boolean servizio) {
        super(nome);
        this.servizio = servizio;
    }

    public boolean isServizio() {
        return servizio;
    }

    public void setServizio(boolean servizio) {
        this.servizio = servizio;
    }

    @Override
    public String toString() {
        return "DistributoreAutomatico{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", servizio=" + servizio +
                '}';
    }
}
