package giadatonni.CONSEGNA_S19L1.services;

import giadatonni.CONSEGNA_S19L1.entities.Dipendente;
import giadatonni.CONSEGNA_S19L1.exceptions.UnauthorizedException;
import giadatonni.CONSEGNA_S19L1.payload.AccessTokenDTO;
import giadatonni.CONSEGNA_S19L1.payload.LoginDTO;
import giadatonni.CONSEGNA_S19L1.security.JWTTools;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final DipendentiService dipendentiService;
    private final JWTTools jwtTools;

    public AuthService(JWTTools jwtTools, DipendentiService dipendentiService) {
        this.jwtTools = jwtTools;
        this.dipendentiService = dipendentiService;
    }

    public String getAccessToken(LoginDTO body){
        Dipendente found = this.dipendentiService.findByEmail(body.email());

        if(found.getPassword().equals(body.password())){
            String accessToken = this.jwtTools.getAccessToken(found);

            return accessToken;
        } else {
            throw new UnauthorizedException("Password non valida");
        }
    }
}
