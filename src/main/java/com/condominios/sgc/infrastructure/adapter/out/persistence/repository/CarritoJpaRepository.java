package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.CarritoEntity;

public interface CarritoJpaRepository extends JpaRepository<CarritoEntity, Long> {
    @Query("SELECT COUNT(c) FROM CarritoEntity c WHERE c.idCondominio = :idCondominio")
    long countByIdCondominio(@Param("idCondominio") Long idCondominio);

    Page<CarritoEntity> findByIdCondominio(Long idCondominio, Pageable pageable);

    Optional<CarritoEntity> findByCodigo(String codigo);
}
