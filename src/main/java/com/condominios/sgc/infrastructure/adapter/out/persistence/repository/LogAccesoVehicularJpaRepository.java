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

    @Query("""
        SELECT l FROM LogAccesoVehicularEntity l
        WHERE l.idCondominio = :cid
        ORDER BY l.fechaEntrada DESC
        """)
    List<LogAccesoVehicularEntity> findRecentByCondominio(@Param("cid") Long cid, Pageable pageable);


    @Query(value = """
        SELECT 
            id,
            'VEHICULO' AS tipo_log,
            placa,
            ocupante,
            datos_inquilino,
            metodo,
            fecha_entrada,
            fecha_salida,
            NULL AS solicitante,
            NULL AS nombre_solicitante,
            NULL AS dni_solicitante,
            NULL AS penalizacion,
            NULL AS fecha_prestamo,
            NULL AS fecha_devolucion,
            condominio_id,
            fecha_entrada AS fecha_orden
        FROM log_acceso_vehicular
        WHERE condominio_id = :condominioId
        
        UNION ALL
        
        SELECT 
            id,
            'CARRITO' AS tipo_log,
            NULL AS placa,
            NULL AS ocupante,
            NULL AS datos_inquilino,
            NULL AS metodo,
            NULL AS fecha_entrada,
            NULL AS fecha_salida,
            solicitante,
            nombre_solicitante,
            dni_solicitante,
            penalizacion,
            fecha_prestamo,
            fecha_devolucion,
            condominio_id,
            fecha_prestamo AS fecha_orden
        FROM log_prestamo_carrito
        WHERE condominio_id = :condominioId
        
        ORDER BY fecha_orden DESC
    """, countQuery = """
        SELECT sum(total) FROM (
            SELECT count(id) as total FROM log_acceso_vehicular WHERE condominio_id = :condominioId
            UNION ALL
            SELECT count(id) as total FROM log_prestamo_carrito WHERE condominio_id = :condominioId
        ) as count_table
    """, nativeQuery = true)
    Page<LogCombinadoProjection> buscarHistorialCombinadoPaginado(@Param("condominioId") Long condominioId, Pageable pageable);
}
