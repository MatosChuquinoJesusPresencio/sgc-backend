package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.PisoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PisoJpaRepository extends JpaRepository<PisoEntity, Long> {
}
