package com.condominios.sgc.infrastructure.adapter.out.persistence.repository.catalog;

import org.springframework.data.jpa.repository.JpaRepository;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.catalog.PaisEntity;

public interface PaisJpaRepository extends JpaRepository<PaisEntity, Long> {
}
