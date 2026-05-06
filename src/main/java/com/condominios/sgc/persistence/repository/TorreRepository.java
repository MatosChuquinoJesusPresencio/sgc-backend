package com.condominios.sgc.persistence.repository;

import com.condominios.sgc.persistence.entity.TorreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TorreRepository extends JpaRepository<TorreEntity, Long>,
        JpaSpecificationExecutor<TorreEntity> {

    List<TorreEntity> findByCondominioId(Long condominioId);

    TorreEntity findByCondominioIdAndNombre(Long condominioId, String nombre);

    boolean existsByCondominioIdAndNombre(Long condominioId, String nombre);
}
