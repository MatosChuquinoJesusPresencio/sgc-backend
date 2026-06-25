package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import java.util.List;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.EstacionamientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstacionamientoJpaRepository extends JpaRepository<EstacionamientoEntity, Long> {
    List<EstacionamientoEntity> findByIdCondominioOrderByNumeroAsc(Long idCondominio);

    @Query("SELECT COUNT(e) FROM EstacionamientoEntity e WHERE e.idCondominio = :idCondominio")
    long countByIdCondominio(@Param("idCondominio") Long idCondominio);
}
