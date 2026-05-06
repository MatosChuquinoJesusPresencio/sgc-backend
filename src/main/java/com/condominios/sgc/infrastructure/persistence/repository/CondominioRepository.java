package com.condominios.sgc.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;

import java.util.Optional;

public interface CondominioRepository extends JpaRepository<CondominioEntity, Long>,
        JpaSpecificationExecutor<CondominioEntity> {

    Optional<CondominioEntity> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
