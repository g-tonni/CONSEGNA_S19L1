package giadatonni.CONSEGNA_S19L1.security;

import giadatonni.CONSEGNA_S19L1.entities.Dipendente;
import giadatonni.CONSEGNA_S19L1.exceptions.UnauthorizedException;
import giadatonni.CONSEGNA_S19L1.services.DipendentiService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTTCheckFilter extends OncePerRequestFilter {

    private final JWTTools jwtTools;
    private final DipendentiService dipendentiService;

    public JWTTCheckFilter(JWTTools jwtTools, DipendentiService dipendentiService) {
        this.jwtTools = jwtTools;
        this.dipendentiService = dipendentiService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserire il token nell'header nel formato corretto");

        String accessToken = authHeader.replace("Bearer ", "");

        this.jwtTools.verifyToken(accessToken);

        UUID userId = this.jwtTools.getId(accessToken);

        Dipendente dipendente = this.dipendentiService.findById(userId);

        Authentication authentication = new UsernamePasswordAuthenticationToken(dipendente, null, dipendente.getAuthorities());
        System.out.println("User: " + authentication.getName());
        System.out.println("Authorities: " + authentication.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
