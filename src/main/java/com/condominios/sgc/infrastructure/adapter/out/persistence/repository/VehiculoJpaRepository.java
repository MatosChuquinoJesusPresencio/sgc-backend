package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.VehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoJpaRepository extends JpaRepository<VehiculoEntity, Long> {
}
