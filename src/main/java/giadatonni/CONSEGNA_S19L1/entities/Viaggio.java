package giadatonni.CONSEGNA_S19L1.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "viaggi")
public class Viaggio {

    @Id
    @GeneratedValue
    @Column(name = "viaggio_id")
    private UUID viaggioId;

    @Column(nullable = false)
    private String destinazione;

    @Column(name = "data_partenza", nullable = false)
    private LocalDate dataPartenza;

    @Column(name = "data_ritorno", nullable = false)
    private LocalDate dataRitorno;

    @Column(nullable = false)
    private String stato;

    public Viaggio() {}

    public Viaggio(String destinazione, LocalDate dataPartenza, LocalDate dataRitorno, String stato) {
        this.destinazione = destinazione;
        this.dataPartenza = dataPartenza;
        this.dataRitorno = dataRitorno;
        this.stato = stato;
    }

    public UUID getViaggioId() {
        return viaggioId;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public LocalDate getDataPartenza() {
        return dataPartenza;
    }

    public void setDataPartenza(LocalDate dataPartenza) {
        this.dataPartenza = dataPartenza;
    }

    public LocalDate getDataRitorno() {
        return dataRitorno;
    }

    public void setDataRitorno(LocalDate dataRitorno) {
        this.dataRitorno = dataRitorno;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        return "Viaggio{" +
                "viaggioId=" + viaggioId +
                ", destinazione='" + destinazione + '\'' +
                ", dataPartenza=" + dataPartenza +
                ", dataRitorno=" + dataRitorno +
                ", stato='" + stato + '\'' +
                '}';
    }
}
