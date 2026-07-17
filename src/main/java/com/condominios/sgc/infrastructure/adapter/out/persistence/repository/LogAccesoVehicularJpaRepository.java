package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.LogAccesoVehicularEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LogAccesoVehicularJpaRepository extends JpaRepository<LogAccesoVehicularEntity, Long> {

    @Query(value = """
        SELECT l FROM LogAccesoVehicularEntity l
        WHERE l.idCondominio = :cid
        AND (:userId IS NULL OR l.idVehiculo IN
            (SELECT v.id FROM VehiculoEntity v WHERE v.idPropietario = :userId OR v.idInquilino = :userId))
        AND (l.fechaEntrada >= COALESCE(:fechaInicio, l.fechaEntrada))
        AND (l.fechaEntrada <= COALESCE(:fechaFin, l.fechaEntrada))
        ORDER BY l.fechaEntrada DESC
        """,
        countQuery = """
        SELECT COUNT(l) FROM LogAccesoVehicularEntity l
        WHERE l.idCondominio = :cid
        """)
    Page<LogAccesoVehicularEntity> findByFilters(
            @Param("cid") Long cid,
            @Param("userId") Long userId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            Pageable pageable);

    @Query("""
        SELECT l FROM LogAccesoVehicularEntity l
        WHERE l.idCondominio = :cid
        ORDER BY l.fechaEntrada DESC
        """)
    List<LogAccesoVehicularEntity> findRecentByCondominio(@Param("cid") Long cid, Pageable pageable);

    @Query("""
        SELECT DISTINCT l.idVehiculo FROM LogAccesoVehicularEntity l
        WHERE l.idEstacionamiento = :slotId
        AND l.fechaSalida IS NULL
        """)
    List<Long> findActiveVehicleIdsByEstacionamiento(@Param("slotId") Long slotId);
}
