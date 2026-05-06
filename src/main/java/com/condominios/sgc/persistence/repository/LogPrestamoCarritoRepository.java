package com.condominios.sgc.persistence.repository;

import com.condominios.sgc.persistence.entity.LogPrestamoCarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LogPrestamoCarritoRepository extends JpaRepository<LogPrestamoCarritoEntity, Long>,
        JpaSpecificationExecutor<LogPrestamoCarritoEntity> {

    List<LogPrestamoCarritoEntity> findByCarritoIdAndFechaDevolucionIsNull(Long carritoId);

    List<LogPrestamoCarritoEntity> findByApartamentoId(Long apartamentoId);
}
