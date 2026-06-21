package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.EstacionamientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstacionamientoJpaRepository extends JpaRepository<EstacionamientoEntity, Long> {
}
