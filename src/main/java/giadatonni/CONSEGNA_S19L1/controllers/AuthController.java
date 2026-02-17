package giadatonni.CONSEGNA_S19L1.controllers;

import giadatonni.CONSEGNA_S19L1.entities.Dipendente;
import giadatonni.CONSEGNA_S19L1.exceptions.ValidationException;
import giadatonni.CONSEGNA_S19L1.payload.AccessTokenDTO;
import giadatonni.CONSEGNA_S19L1.payload.DipendenteDTO;
import giadatonni.CONSEGNA_S19L1.payload.LoginDTO;
import giadatonni.CONSEGNA_S19L1.services.AuthService;
import giadatonni.CONSEGNA_S19L1.services.DipendentiService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final DipendentiService dipendentiService;

    public AuthController(AuthService authService, DipendentiService dipendentiService) {
        this.authService = authService;
        this.dipendentiService = dipendentiService;
    }

    @PostMapping("/login")
    public AccessTokenDTO getAccessToken(@RequestBody LoginDTO accessToken){
        return new AccessTokenDTO(this.authService.getAccessToken(accessToken));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente postDipendente(@RequestBody @Validated DipendenteDTO body, BindingResult validationResults){
        if (validationResults.hasErrors()){
            List<String> errors = validationResults.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        } else {
            return this.dipendentiService.postDipendente(body);
        }
    }
}
