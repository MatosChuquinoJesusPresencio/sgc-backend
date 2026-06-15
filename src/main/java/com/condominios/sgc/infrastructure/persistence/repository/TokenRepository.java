package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.domain.auxiliar.TipoToken;
import com.condominios.sgc.infrastructure.persistence.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByToken(String token);
    Optional<TokenEntity> findByIdUsuarioAndTipo(Long idUsuario, TipoToken tipo);
    List<TokenEntity> findByIdUsuario(Long idUsuario);
    List<TokenEntity> findByUsadoFalse();
}
