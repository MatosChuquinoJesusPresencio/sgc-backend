package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import java.time.LocalDateTime;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.LogPrestamoCarritoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LogPrestamoCarritoJpaRepository extends JpaRepository<LogPrestamoCarritoEntity, Long> {

    @Query("""
        SELECT l FROM LogPrestamoCarritoEntity l
        WHERE l.idCondominio = :cid
        AND (:userId IS NULL OR l.idPropietario = :userId OR l.idInquilino = :userId)
        AND (:fechaInicio IS NULL OR l.fechaPrestamo >= :fechaInicio)
        AND (:fechaFin IS NULL OR l.fechaPrestamo <= :fechaFin)
        ORDER BY l.fechaPrestamo DESC
        """)
    Page<LogPrestamoCarritoEntity> findByFilters(
            @Param("cid") Long cid,
            @Param("userId") Long userId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            Pageable pageable);
}
