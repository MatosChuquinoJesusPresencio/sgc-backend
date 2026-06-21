package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.TorreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TorreJpaRepository extends JpaRepository<TorreEntity, Long> {
}
