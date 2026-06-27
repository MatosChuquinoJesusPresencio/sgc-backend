package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.ConfiguracionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfiguracionJpaRepository extends JpaRepository<ConfiguracionEntity, Long> {
}
