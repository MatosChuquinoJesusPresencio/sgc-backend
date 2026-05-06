package com.condominios.sgc.persistence.repository;

import com.condominios.sgc.persistence.entity.InquilinoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface InquilinoRepository extends JpaRepository<InquilinoEntity, Long>,
        JpaSpecificationExecutor<InquilinoEntity> {

    List<InquilinoEntity> findByApartamentoId(Long apartamentoId);

    InquilinoEntity findByDni(String dni);

    boolean existsByApartamentoIdAndDni(Long apartamentoId, String dni);
}
