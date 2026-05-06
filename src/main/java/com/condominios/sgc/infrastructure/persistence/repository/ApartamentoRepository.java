package com.condominios.sgc.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;

import java.util.List;

public interface ApartamentoRepository extends JpaRepository<ApartamentoEntity, Long>,
        JpaSpecificationExecutor<ApartamentoEntity> {

    List<ApartamentoEntity> findByPisoId(Long pisoId);

    List<ApartamentoEntity> findByPropietarioId(String usuarioId);

    boolean existsByPisoIdAndNumero(Long pisoId, Integer numero);
}
