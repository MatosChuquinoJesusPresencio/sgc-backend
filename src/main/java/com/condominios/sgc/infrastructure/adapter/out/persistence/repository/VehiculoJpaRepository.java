package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.VehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VehiculoJpaRepository extends JpaRepository<VehiculoEntity, Long> {
    @Query("SELECT COUNT(v) FROM VehiculoEntity v WHERE v.idCondominio = :idCondominio")
    long countByIdCondominio(@Param("idCondominio") Long idCondominio);
}
