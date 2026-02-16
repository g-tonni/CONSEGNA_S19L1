package giadatonni.CONSEGNA_S19L1.payload;

import jakarta.validation.constraints.Size;

public record PutPrenotazioneDTO (
        @Size(max = 30, message = "Le richieste speciali non possono superare i 500 caratteri")
        String richiesteSpeciali) {
}
