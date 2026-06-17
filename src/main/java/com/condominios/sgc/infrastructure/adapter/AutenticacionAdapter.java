package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.infrastructure.persistence.entity.TokenEntity;
import com.condominios.sgc.infrastructure.persistence.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.List;
import java.util.Optional;

@Component
public class AutenticacionAdapter implements AutenticacionPort {

    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final SecretKey secretKey;

    public AutenticacionAdapter(PasswordEncoder passwordEncoder,
                                TokenRepository tokenRepository,
                                SecretKey secretKey) {
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.secretKey = secretKey;
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
        return tokenRepository.findByToken(token)
                .filter(t -> !t.getUsado())
                .filter(t -> t.getExpiracion().isAfter(java.time.Instant.now()))
                .map(TokenEntity::getIdUsuario);
    }

    @Override
    public boolean esRecuerdame(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("recuerdame", Boolean.class) != null
                    && claims.get("recuerdame", Boolean.class);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void invalidarToken(String token) {
        tokenRepository.findByToken(token).ifPresent(t -> {
            t.setUsado(true);
            tokenRepository.save(t);
        });
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void invalidarTokensPorUsuario(Long idUsuario) {
        List<TokenEntity> tokens = tokenRepository.findByIdUsuario(idUsuario);
        tokens.forEach(t -> t.setUsado(true));
        tokenRepository.saveAll(tokens);
    }
}
