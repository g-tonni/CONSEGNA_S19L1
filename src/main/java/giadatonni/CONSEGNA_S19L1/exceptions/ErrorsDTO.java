package giadatonni.CONSEGNA_S19L1.exceptions;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsDTO (String messaggio, List<String> listaErrori, LocalDateTime timestamp) {

    public ErrorsDTO(String messaggio, List<String> listaErrori){
        this(messaggio, listaErrori, LocalDateTime.now());
    }

}
