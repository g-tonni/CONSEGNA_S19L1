package giadatonni.CONSEGNA_S19L1.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DipendenteDTO (
     @NotBlank(message = "Lo username deve essere inserito")
     @Size(min = 3, max = 30, message = "Lo username deve essere compreso tra 3 e 30 caratteri")
     String username,
     @NotBlank(message = "Il nome deve essere inserito")
     @Size(min = 2, max = 30, message = "Il nome deve essere compreso tra 2 e 30 caratteri")
     String nome,
     @NotBlank(message = "Il cognome deve essere inserito")
     @Size(min = 2, max = 30, message = "Il cognome deve essere compreso tra 2 e 30 caratteri")
     String cognome,
     @NotBlank(message = "L'email deve essere inserita")
     @Email(message = "Indirizzo email non valido")
     String email){
}
