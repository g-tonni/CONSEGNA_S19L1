package giadatonni.CONSEGNA_S19L1.exceptions;

import java.time.LocalDateTime;

public record ErrorDTO (
        String errore,
        LocalDateTime timestamp
){
    public ErrorDTO (String errore) {
        this(errore, LocalDateTime.now());
    }
}
