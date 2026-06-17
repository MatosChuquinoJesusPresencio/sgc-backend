package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.ConfiguracionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ConfiguracionRepository extends JpaRepository<ConfiguracionEntity, Long> {
    Optional<ConfiguracionEntity> findByIdCondominio(Long idCondominio);
}
