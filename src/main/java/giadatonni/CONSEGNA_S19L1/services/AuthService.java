package giadatonni.CONSEGNA_S19L1.services;

import giadatonni.CONSEGNA_S19L1.entities.Dipendente;
import giadatonni.CONSEGNA_S19L1.exceptions.UnauthorizedException;
import giadatonni.CONSEGNA_S19L1.payload.AccessTokenDTO;
import giadatonni.CONSEGNA_S19L1.payload.LoginDTO;
import giadatonni.CONSEGNA_S19L1.security.JWTTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final DipendentiService dipendentiService;
    private final JWTTools jwtTools;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JWTTools jwtTools, DipendentiService dipendentiService, PasswordEncoder passwordEncoder) {
        this.jwtTools = jwtTools;
        this.dipendentiService = dipendentiService;
        this.passwordEncoder = passwordEncoder;
    }

    public String getAccessToken(LoginDTO body){
        Dipendente found = this.dipendentiService.findByEmail(body.email());

        if(passwordEncoder.matches(body.password(), found.getPassword())){
            String accessToken = this.jwtTools.getAccessToken(found);
            return accessToken;
        } else {
            throw new UnauthorizedException("Password non valida");
        }
    }
}
