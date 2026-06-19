package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartamentoJpaRepository extends JpaRepository<ApartamentoEntity, Long> {
    List<ApartamentoEntity> findByIdPiso(Long idPiso);
}
