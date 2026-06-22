package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import java.time.LocalDateTime;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.LogAccesoVehicularEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LogAccesoVehicularJpaRepository extends JpaRepository<LogAccesoVehicularEntity, Long> {

    @Query("""
        SELECT l FROM LogAccesoVehicularEntity l
        WHERE l.idCondominio = :cid
        AND (:userId IS NULL OR l.idVehiculo IN
            (SELECT v.id FROM VehiculoEntity v WHERE v.idPropietario = :userId OR v.idInquilino = :userId))
        AND (:fechaInicio IS NULL OR l.fechaEntrada >= :fechaInicio)
        AND (:fechaFin IS NULL OR l.fechaEntrada <= :fechaFin)
        ORDER BY l.fechaEntrada DESC
        """)
    Page<LogAccesoVehicularEntity> findByFilters(
            @Param("cid") Long cid,
            @Param("userId") Long userId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            Pageable pageable);
}
