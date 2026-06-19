package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.MonedaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonedaJpaRepository extends JpaRepository<MonedaEntity, Long> {
}
