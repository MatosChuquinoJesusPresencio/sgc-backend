package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import java.util.Optional;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.ConfiguracionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConfiguracionJpaRepository extends JpaRepository<ConfiguracionEntity, Long> {
    @Query("SELECT c FROM ConfiguracionEntity c WHERE c.condominio.id = :condominioId")
    Optional<ConfiguracionEntity> findByCondominioId(@Param("condominioId") Long condominioId);
}
