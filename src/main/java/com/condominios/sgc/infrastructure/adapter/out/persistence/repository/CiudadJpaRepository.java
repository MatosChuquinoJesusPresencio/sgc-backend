package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.CiudadEntity;

public interface CiudadJpaRepository extends JpaRepository<CiudadEntity, Long> {
    List<CiudadEntity> findByIdPais(Long paisId);
}
