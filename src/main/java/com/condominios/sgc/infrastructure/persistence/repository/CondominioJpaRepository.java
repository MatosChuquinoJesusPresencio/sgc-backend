package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CondominioJpaRepository extends JpaRepository<CondominioEntity, Long> {
}
