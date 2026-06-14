package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface TorreRepository extends JpaRepository<TorreEntity, Long>, JpaSpecificationExecutor<TorreEntity> {
    List<TorreEntity> findByIdCondominio(Long idCondominio);
}
