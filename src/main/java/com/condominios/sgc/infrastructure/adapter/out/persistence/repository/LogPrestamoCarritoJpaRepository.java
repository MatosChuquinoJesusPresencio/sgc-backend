package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.LogPrestamoCarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogPrestamoCarritoJpaRepository extends JpaRepository<LogPrestamoCarritoEntity, Long> {
}
