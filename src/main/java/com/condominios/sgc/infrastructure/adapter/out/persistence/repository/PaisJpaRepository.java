package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.PaisEntity;

public interface PaisJpaRepository extends JpaRepository<PaisEntity, Long> {
}
