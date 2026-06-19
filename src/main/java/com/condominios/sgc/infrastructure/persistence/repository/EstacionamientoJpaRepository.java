package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.EstacionamientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstacionamientoJpaRepository extends JpaRepository<EstacionamientoEntity, Long> {
    List<EstacionamientoEntity> findByIdCondominio(Long idCondominio);
    List<EstacionamientoEntity> findByIdApartamento(Long idApartamento);
    long countByIdCondominio(Long idCondominio);
}
