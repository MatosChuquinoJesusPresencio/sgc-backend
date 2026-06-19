package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.PisoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PisoJpaRepository extends JpaRepository<PisoEntity, Long> {
    List<PisoEntity> findByIdTorre(Long idTorre);
}
