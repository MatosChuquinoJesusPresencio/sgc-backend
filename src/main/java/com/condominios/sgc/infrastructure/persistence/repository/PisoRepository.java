package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.PisoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface PisoRepository extends JpaRepository<PisoEntity, Long>, JpaSpecificationExecutor<PisoEntity> {
    List<PisoEntity> findByIdTorre(Long idTorre);
}
