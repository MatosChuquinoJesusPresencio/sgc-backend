package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.ApartamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartamentoJpaRepository extends JpaRepository<ApartamentoEntity, Long> {
}
