package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.infrastructure.persistence.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Optional;

@Component
public class AutenticacionAdapter implements AutenticacionPort {

    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final SecretKey secretKey;

    public AutenticacionAdapter(PasswordEncoder passwordEncoder,
                                TokenRepository tokenRepository,
                                SecretKey jwtSecretKey) {
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.secretKey = jwtSecretKey;
    }

    @Override
    public String hashContrasena(String contrasena) {
        return passwordEncoder.encode(contrasena);
    }

    @Override
    public boolean verificarContrasena(String contrasena, String hash) {
        return passwordEncoder.matches(contrasena, hash);
    }

    @Override
    public Optional<Long> validarToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            if (claims.getExpiration().before(new Date()))
                return Optional.empty();

            Long idUsuario = Long.parseLong(claims.getSubject());

            var tokenEntity = tokenRepository.findByToken(token);
            if (tokenEntity.isEmpty() || tokenEntity.get().getUsado())
                return Optional.empty();

            return Optional.of(idUsuario);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void invalidarToken(String token) {
        tokenRepository.findByToken(token).ifPresent(t -> {
            t.setUsado(true);
            tokenRepository.save(t);
        });
    }
}
