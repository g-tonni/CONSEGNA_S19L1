package giadatonni.CONSEGNA_S19L1.controllers;

import giadatonni.CONSEGNA_S19L1.entities.Dipendente;
import giadatonni.CONSEGNA_S19L1.exceptions.ValidationException;
import giadatonni.CONSEGNA_S19L1.payload.DipendenteDTO;
import giadatonni.CONSEGNA_S19L1.services.DipendentiService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dipendenti")
public class DipendentiController {

    private final DipendentiService dipendentiService;

    public DipendentiController(DipendentiService dipendentiService) {
        this.dipendentiService = dipendentiService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN')")
    public Page<Dipendente> getDipendenti(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "cognome") String orderBy){
        return this.dipendentiService.getDipendenti(page, size, orderBy);
    }

    @GetMapping("/{dipendenteId}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN')")
    public Dipendente getDipendenteById(@PathVariable UUID dipendenteId){
        return this.dipendentiService.findById(dipendenteId);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN')")
    public Dipendente getMe(@AuthenticationPrincipal Dipendente dipendenteAutenticato){
        return this.dipendentiService.findById(dipendenteAutenticato.getDipendenteId());
    }

    @PutMapping("/me")
    public Dipendente putDipendente(@AuthenticationPrincipal Dipendente dipendenteAutenticato, @RequestBody @Validated DipendenteDTO body, BindingResult validationResults){
        if (validationResults.hasErrors()){
            List<String> errors = validationResults.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        } else {
            return this.dipendentiService.putDipendente(dipendenteAutenticato.getDipendenteId(), body);
        }
    }

    @PatchMapping("/me/fotoProfilo")
    public Dipendente uploadImage(@AuthenticationPrincipal Dipendente dipendenteAutenticato, @RequestParam("foto_profilo") MultipartFile file, @PathVariable UUID dipendenteId) {
        // System.out.println(file.getOriginalFilename());
        return this.dipendentiService.uploadFotoProfilo(dipendenteAutenticato.getDipendenteId(), file);
    }

    /*@DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Dipendente deleteDipendente(@PathVariable UUID dipendenteId){}*/


}
