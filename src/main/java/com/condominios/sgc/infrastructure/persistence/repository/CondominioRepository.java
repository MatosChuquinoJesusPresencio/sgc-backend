package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CondominioRepository extends JpaRepository<CondominioEntity, Long>, JpaSpecificationExecutor<CondominioEntity> {
}
