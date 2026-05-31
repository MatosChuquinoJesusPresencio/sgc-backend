package com.condominios.sgc.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.condominios.sgc.infrastructure.persistence.entity.PisoEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PisoRepository extends JpaRepository<PisoEntity, Long>,
        JpaSpecificationExecutor<PisoEntity> {

    List<PisoEntity> findByTorreId(Long torreId);
    Optional<PisoEntity> findByTorreIdAndNumero(Long torreId, Integer numero);
    boolean existsByTorreIdAndNumero(Long torreId, Integer numero);
    Page<PisoEntity> findByTorreId(Long torreId, Pageable pageable);
}
