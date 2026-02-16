package giadatonni.CONSEGNA_S19L1.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTTools {

    @Value("jwt.secret")
    private String secret;

    public String get

}
