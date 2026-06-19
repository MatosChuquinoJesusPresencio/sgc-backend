package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.CarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarritoJpaRepository extends JpaRepository<CarritoEntity, Long> {
    Optional<CarritoEntity> findByCodigo(String codigo);
    List<CarritoEntity> findByIdCondominio(Long idCondominio);
    long countByIdCondominio(Long idCondominio);
}
