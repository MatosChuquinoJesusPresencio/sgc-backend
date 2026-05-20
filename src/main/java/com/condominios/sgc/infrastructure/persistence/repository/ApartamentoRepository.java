package com.condominios.sgc.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;

import java.util.List;
import java.util.Optional;

public interface ApartamentoRepository extends JpaRepository<ApartamentoEntity, Long>,
        JpaSpecificationExecutor<ApartamentoEntity> {

    List<ApartamentoEntity> findByPisoId(Long pisoId);

    Optional<ApartamentoEntity> findByPropietarioId(String usuarioId);

    boolean existsByPisoIdAndNumero(Long pisoId, Integer numero);
}
