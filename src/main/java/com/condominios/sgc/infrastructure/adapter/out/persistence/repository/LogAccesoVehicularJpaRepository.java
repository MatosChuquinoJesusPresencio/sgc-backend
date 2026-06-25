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
            'VEHICULO' AS tipoLog,
            placa,
            ocupante,
            datos_inquilino AS datosInquilino,
            metodo,
            fecha_entrada AS fechaEntrada,
            fecha_salida AS fechaSalida,
            NULL AS solicitante,
            NULL AS nombreSolicitante,
            NULL AS dniSolicitante,
            NULL AS penalizacion,
            NULL AS fechaPrestamo,
            NULL AS fechaDevolucion,
            condominio_id AS condominioId,
            fecha_entrada AS fechaOrden
        FROM log_acceso_vehicular
        WHERE condominio_id = :condominioId
        
        UNION ALL
        
        SELECT 
            id,
            'CARRITO' AS tipoLog,
            NULL AS placa,
            NULL AS ocupante,
            NULL AS datosInquilino,
            NULL AS metodo,
            NULL AS fechaEntrada,
            NULL AS fechaSalida,
            solicitante,
            nombre_solicitante AS nombreSolicitante,
            dni_solicitante AS dniSolicitante,
            penalizacion,
            fecha_prestamo AS fechaPrestamo,
            fecha_devolucion AS fechaDevolucion,
            condominio_id AS condominioId,
            fecha_prestamo AS fechaOrden
        FROM log_prestamo_carrito
        WHERE condominio_id = :condominioId
        
        ORDER BY fechaOrden DESC
    """, countQuery = """
        SELECT sum(total) FROM (
            SELECT count(id) as total FROM log_acceso_vehicular WHERE condominio_id = :condominioId
            UNION ALL
            SELECT count(id) as total FROM log_prestamo_carrito WHERE condominio_id = :condominioId
        ) as count_table
    """, nativeQuery = true)
    Page<LogCombinadoProjection> buscarHistorialCombinadoPaginado(@Param("condominioId") Long condominioId, Pageable pageable);
}
