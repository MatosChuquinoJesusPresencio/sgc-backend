package com.condominios.sgc.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.condominios.sgc.infrastructure.persistence.entity.LogPrestamoCarritoEntity;

import java.util.List;

public interface LogPrestamoCarritoRepository extends JpaRepository<LogPrestamoCarritoEntity, Long>,
        JpaSpecificationExecutor<LogPrestamoCarritoEntity> {

    List<LogPrestamoCarritoEntity> findByCarritoIdAndFechaDevolucionIsNull(Long carritoId);

    List<LogPrestamoCarritoEntity> findByApartamentoId(Long apartamentoId);
}
