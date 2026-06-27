package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import java.util.List;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.InquilinoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InquilinoJpaRepository extends JpaRepository<InquilinoEntity, Long> {
    List<InquilinoEntity> findByIdApartamento(Long idApartamento);
    void deleteByIdApartamento(Long idApartamento);

    @Query("SELECT i FROM InquilinoEntity i WHERE i.idApartamento IN :ids")
    List<InquilinoEntity> findByIdApartamentoIn(@Param("ids") List<Long> ids);
}
