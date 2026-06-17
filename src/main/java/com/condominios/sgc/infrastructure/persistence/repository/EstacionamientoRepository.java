package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.EstacionamientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface EstacionamientoRepository extends JpaRepository<EstacionamientoEntity, Long>, JpaSpecificationExecutor<EstacionamientoEntity> {
    List<EstacionamientoEntity> findByIdApartamento(Long idApartamento);
}
