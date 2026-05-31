package com.condominios.sgc.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.condominios.sgc.infrastructure.persistence.entity.InquilinoEntity;

import java.util.List;

@Repository
public interface InquilinoRepository extends JpaRepository<InquilinoEntity, Long>,
        JpaSpecificationExecutor<InquilinoEntity> {

    List<InquilinoEntity> findByApartamentoId(Long apartamentoId);
    InquilinoEntity findByDni(String dni);
    boolean existsByApartamentoIdAndDni(Long apartamentoId, String dni);
    Integer countByApartamentoId(Long apartamentoId);
}
