package com.condominios.sgc.persistence.repository;

import com.condominios.sgc.persistence.entity.CondominioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CondominioRepository extends JpaRepository<CondominioEntity, Long>,
        JpaSpecificationExecutor<CondominioEntity> {

    Optional<CondominioEntity> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
