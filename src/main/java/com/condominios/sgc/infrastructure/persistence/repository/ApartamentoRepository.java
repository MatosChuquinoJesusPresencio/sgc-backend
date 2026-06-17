package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface ApartamentoRepository extends JpaRepository<ApartamentoEntity, Long>, JpaSpecificationExecutor<ApartamentoEntity> {
    List<ApartamentoEntity> findByIdPiso(Long idPiso);
}
