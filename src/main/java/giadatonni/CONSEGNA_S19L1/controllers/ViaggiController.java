package giadatonni.CONSEGNA_S19L1.controllers;

import giadatonni.CONSEGNA_S19L1.entities.Viaggio;
import giadatonni.CONSEGNA_S19L1.exceptions.ValidationException;
import giadatonni.CONSEGNA_S19L1.payload.ViaggioDTO;
import giadatonni.CONSEGNA_S19L1.services.ViaggiService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/viaggi")
public class ViaggiController {

    private final ViaggiService viaggiService;

    public ViaggiController(ViaggiService viaggiService) {
        this.viaggiService = viaggiService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN')")
    public Page<Viaggio> getViaggi(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "dataPartenza") String orderBy){
        return this.viaggiService.getViaggi(page, size, orderBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN')")
    public Viaggio postViaggio(@RequestBody @Validated ViaggioDTO body, BindingResult validationResults){
        if (validationResults.hasErrors()){
            List<String> errors = validationResults.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        } else {
            return this.viaggiService.postViaggio(body);
        }
    }

    @GetMapping("/{viaggioId}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN')")
    public Viaggio getViaggioById(@PathVariable UUID viaggioId){
        return this.viaggiService.findById(viaggioId);
    }

    @PutMapping("/{viaggioId}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN')")
    public Viaggio putViaggio(@PathVariable UUID viaggioId, @RequestBody @Validated ViaggioDTO body, BindingResult validationResults){
        if (validationResults.hasErrors()){
            List<String> errors = validationResults.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        } else {
            return this.viaggiService.putViaggio(viaggioId, body);
        }
    }

    @PatchMapping("/{viaggioId}/stato")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN')")
    public Viaggio cambiaStato(@PathVariable UUID viaggioId, @RequestBody String stato){
        return this.viaggiService.patchStatoViaggio(viaggioId, stato);
    }

    /* @DeleteMapping("/{viaggioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Viaggio deleteViaggio(@PathVariable UUID viaggioId){}*/
}
