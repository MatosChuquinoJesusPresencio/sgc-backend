package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TorreJpaRepository extends JpaRepository<TorreEntity, Long> {
    List<TorreEntity> findByIdCondominio(Long idCondominio);
}
