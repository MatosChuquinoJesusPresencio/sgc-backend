package com.condominios.sgc.persistence.repository;

import com.condominios.sgc.persistence.entity.EstacionamientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EstacionamientoRepository extends JpaRepository<EstacionamientoEntity, Long>,
        JpaSpecificationExecutor<EstacionamientoEntity> {

    List<EstacionamientoEntity> findByCondominioIdAndDisponibleTrue(Long condominioId);

    List<EstacionamientoEntity> findByApartamentoId(Long apartamentoId);

    boolean existsByCondominioIdAndNumero(Long condominioId, Integer numero);
}
