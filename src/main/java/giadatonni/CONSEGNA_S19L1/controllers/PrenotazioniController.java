package giadatonni.CONSEGNA_S19L1.controllers;


import giadatonni.CONSEGNA_S19L1.entities.Dipendente;
import giadatonni.CONSEGNA_S19L1.entities.Prenotazione;
import giadatonni.CONSEGNA_S19L1.exceptions.ValidationException;
import giadatonni.CONSEGNA_S19L1.payload.PrenotazioneDTO;
import giadatonni.CONSEGNA_S19L1.payload.PutPrenotazioneDTO;
import giadatonni.CONSEGNA_S19L1.services.PrenotazioniService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {

    private final PrenotazioniService prenotazioniService;

    public PrenotazioniController(PrenotazioniService prenotazioniService) {
        this.prenotazioniService = prenotazioniService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN')")
    public Page<Prenotazione> getPrenotazioni(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "dataPrenotazione") String orderBy){
        return this.prenotazioniService.getPrenotazioni(page, size, orderBy);
    }

    @GetMapping("/{prenotazioneId}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN')")
    public Prenotazione getPrenotazioneById(@PathVariable UUID prenotazioneId){
        return this.prenotazioniService.findById(prenotazioneId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione postPrenotazione(@RequestBody @Validated PrenotazioneDTO body, BindingResult validationResults){
        if (validationResults.hasErrors()){
            List<String> errors = validationResults.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        } else {
            return this.prenotazioniService.postPrenotazione(body);
        }
    }

    @PutMapping("/{prenotazioneId}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN')")
    public Prenotazione putPrenotazione(@PathVariable UUID prenotazioneId, @RequestBody @Validated PutPrenotazioneDTO body, BindingResult validationResults){
        if (validationResults.hasErrors()){
            List<String> errors = validationResults.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        } else {
            return this.prenotazioniService.putPrenotazione(prenotazioneId, body);
        }
    }

    @GetMapping("/{dipendenteId}/trovaPrenotazioni")
    public List<Prenotazione> findPrenotazioniByDipendente(@AuthenticationPrincipal Dipendente dipendenteAutenticato){
        return this.prenotazioniService.trovaByUtente(dipendenteAutenticato.getDipendenteId());
    }

    /*@DeleteMapping("/{PrenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Prenotazione deletePrenotazione(@PathVariable UUID PrenotazioneId){}*/
}
