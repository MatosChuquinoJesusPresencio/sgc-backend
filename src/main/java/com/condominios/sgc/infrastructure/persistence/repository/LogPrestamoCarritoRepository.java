package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.LogPrestamoCarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface LogPrestamoCarritoRepository extends JpaRepository<LogPrestamoCarritoEntity, Long>, JpaSpecificationExecutor<LogPrestamoCarritoEntity> {
    List<LogPrestamoCarritoEntity> findByFechaDevolucionIsNull();
    long countByFechaDevolucionIsNullAndIdApartamento(Long idApartamento);
}
