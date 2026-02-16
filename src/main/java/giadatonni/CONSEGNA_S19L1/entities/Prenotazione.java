package giadatonni.CONSEGNA_S19L1.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "prenotazioni")
public class Prenotazione {

    @Id
    @GeneratedValue
    @Column(name = "prenotazione_id")
    private UUID prenotazioneId;

    @Column(name = "data_prenotazione", nullable = false)
    private LocalDate dataPrenotazione;

    @Column(name = "richieste_speciali", length = 500)
    private String richiesteSpeciali;

    @ManyToOne
    @JoinColumn(name = "viaggio_id", nullable = false)
    private Viaggio viaggio;

    @ManyToOne
    @JoinColumn(name = "dipendente_id", nullable = false)
    private Dipendente dipendente;

    public Prenotazione() {}

    public Prenotazione(LocalDate dataPrenotazione, String richiesteSpeciali, Viaggio viaggio, Dipendente dipendente) {
        this.dataPrenotazione = dataPrenotazione;
        this.richiesteSpeciali = richiesteSpeciali;
        this.viaggio = viaggio;
        this.dipendente = dipendente;
    }

    public UUID getPrenotazioneId() {
        return prenotazioneId;
    }

    public LocalDate getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(LocalDate dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public String getRichiesteSpeciali() {
        return richiesteSpeciali;
    }

    public void setRichiesteSpeciali(String richiesteSpeciali) {
        this.richiesteSpeciali = richiesteSpeciali;
    }

    public Viaggio getViaggio() {
        return viaggio;
    }

    public void setViaggio(Viaggio viaggio) {
        this.viaggio = viaggio;
    }

    public Dipendente getDipendente() {
        return dipendente;
    }

    public void setDipendente(Dipendente dipendente) {
        this.dipendente = dipendente;
    }

    @Override
    public String toString() {
        return "Prenotazione{" +
                "prenotazioneId=" + prenotazioneId +
                ", dataPrenotazione=" + dataPrenotazione +
                ", richiesteSpeciali='" + richiesteSpeciali + '\'' +
                ", viaggio=" + viaggio +
                ", dipendente=" + dipendente +
                '}';
    }
}
