package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.InquilinoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquilinoJpaRepository extends JpaRepository<InquilinoEntity, Long> {
    List<InquilinoEntity> findByIdApartamento(Long idApartamento);
    long countByIdApartamento(Long idApartamento);
}
