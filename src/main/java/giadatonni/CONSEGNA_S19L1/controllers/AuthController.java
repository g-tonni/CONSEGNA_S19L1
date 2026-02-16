package giadatonni.CONSEGNA_S19L1.controllers;

import giadatonni.CONSEGNA_S19L1.payload.AccessTokenDTO;
import giadatonni.CONSEGNA_S19L1.payload.LoginDTO;
import giadatonni.CONSEGNA_S19L1.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AccessTokenDTO getAccessToken(@RequestBody LoginDTO accessToken){
        return new AccessTokenDTO(this.authService.getAccessToken(accessToken));
    }
}
