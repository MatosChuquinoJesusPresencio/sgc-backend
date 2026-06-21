package com.condominios.sgc.infrastructure.adapter.out.persistence.repository.catalog;

import org.springframework.data.jpa.repository.JpaRepository;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.catalog.MonedaEntity;

public interface MonedaJpaRepository extends JpaRepository<MonedaEntity, Long> {
}
