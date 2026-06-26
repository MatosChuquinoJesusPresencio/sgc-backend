package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.EstacionamientoEntity;

public interface EstacionamientoJpaRepository extends JpaRepository<EstacionamientoEntity, Long> {
    Page<EstacionamientoEntity> findByIdCondominio(Long idCondominio, Pageable pageable);
}
