package com.condominios.sgc.persistence.repository;

import com.condominios.sgc.persistence.entity.ConfiguracionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConfiguracionRepository extends JpaRepository<ConfiguracionEntity, Long>,
        JpaSpecificationExecutor<ConfiguracionEntity> {
}
