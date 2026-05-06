package com.condominios.sgc.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.condominios.sgc.infrastructure.persistence.entity.ConfiguracionEntity;

public interface ConfiguracionRepository extends JpaRepository<ConfiguracionEntity, Long>,
        JpaSpecificationExecutor<ConfiguracionEntity> {
}
