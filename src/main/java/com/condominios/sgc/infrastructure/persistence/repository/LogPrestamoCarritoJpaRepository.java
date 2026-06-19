package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.LogPrestamoCarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogPrestamoCarritoJpaRepository extends JpaRepository<LogPrestamoCarritoEntity, Long> {
    List<LogPrestamoCarritoEntity> findByIdApartamento(Long idApartamento);
    List<LogPrestamoCarritoEntity> findByIdCarrito(Long idCarrito);
}
