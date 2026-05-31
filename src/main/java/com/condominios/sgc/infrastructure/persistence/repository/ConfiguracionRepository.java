package com.condominios.sgc.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.condominios.sgc.infrastructure.persistence.entity.ConfiguracionEntity;

import java.util.Optional;

@Repository
public interface ConfiguracionRepository extends JpaRepository<ConfiguracionEntity, Long>,
        JpaSpecificationExecutor<ConfiguracionEntity> {

    Optional<ConfiguracionEntity> findByCondominioId(Long condominioId);
}
