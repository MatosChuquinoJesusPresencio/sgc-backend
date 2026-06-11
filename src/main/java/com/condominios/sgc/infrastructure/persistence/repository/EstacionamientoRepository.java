package com.condominios.sgc.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.condominios.sgc.infrastructure.persistence.entity.EstacionamientoEntity;

import java.util.List;

public interface EstacionamientoRepository extends JpaRepository<EstacionamientoEntity, Long>,
        JpaSpecificationExecutor<EstacionamientoEntity> {

    List<EstacionamientoEntity> findByCondominioIdAndDisponibleTrue(Long condominioId);
    List<EstacionamientoEntity> findByApartamentoId(Long apartamentoId);
    boolean existsByCondominioIdAndNumero(Long condominioId, Integer numero);
}
