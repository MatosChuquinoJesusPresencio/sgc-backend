package com.condominios.sgc.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;

import java.util.List;

@Repository
public interface TorreRepository extends JpaRepository<TorreEntity, Long>,
        JpaSpecificationExecutor<TorreEntity> {

    List<TorreEntity> findByCondominioId(Long condominioId);
    TorreEntity findByCondominioIdAndNombre(Long condominioId, String nombre);
    boolean existsByCondominioIdAndNombre(Long condominioId, String nombre);
    Page<TorreEntity> findByCondominioId(Long condominioId, Pageable pageable);
}
