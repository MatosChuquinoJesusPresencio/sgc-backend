package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.InquilinoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquilinoJpaRepository extends JpaRepository<InquilinoEntity, Long> {
}
