package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.VerificacionTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificacionTokenRepository extends JpaRepository<VerificacionTokenEntity, String> {

    Optional<VerificacionTokenEntity> findByTokenAndUsadoFalse(String token);
}
