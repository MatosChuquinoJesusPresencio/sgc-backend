package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.CondominioEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CondominioJpaRepository extends JpaRepository<CondominioEntity, Long> {

    @EntityGraph(attributePaths = {
        "torres", "torres.pisos", "torres.pisos.apartamentos", "configuracion"
    })
    Optional<CondominioEntity> findWithTreeById(Long id);
}
