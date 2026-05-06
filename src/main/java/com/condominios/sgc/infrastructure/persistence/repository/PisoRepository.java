package com.condominios.sgc.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.condominios.sgc.infrastructure.persistence.entity.PisoEntity;

import java.util.List;
import java.util.Optional;

public interface PisoRepository extends JpaRepository<PisoEntity, Long>,
        JpaSpecificationExecutor<PisoEntity> {

    List<PisoEntity> findByTorreId(Long torreId);

    Optional<PisoEntity> findByTorreIdAndNumero(Long torreId, Integer numero);

    boolean existsByTorreIdAndNumero(Long torreId, Integer numero);
}
