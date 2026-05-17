package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.RestablecimientoTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestablecimientoTokenRepository extends JpaRepository<RestablecimientoTokenEntity, String> {

    Optional<RestablecimientoTokenEntity> findByTokenAndUsadoFalse(String token);
}
