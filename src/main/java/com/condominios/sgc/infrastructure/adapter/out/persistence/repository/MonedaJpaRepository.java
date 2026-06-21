package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.MonedaEntity;

public interface MonedaJpaRepository extends JpaRepository<MonedaEntity, Long> {
}
