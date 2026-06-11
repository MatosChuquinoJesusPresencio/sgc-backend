package com.condominios.sgc.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.condominios.sgc.infrastructure.persistence.entity.VehiculoEntity;

import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Long>,
        JpaSpecificationExecutor<VehiculoEntity> {

    Optional<VehiculoEntity> findByPlaca(String placa);
    boolean existsByPlaca(String placa);
    List<VehiculoEntity> findByPropietarioId(Long usuarioId);
    List<VehiculoEntity> findByInquilinoId(Long inquilinoId);
}
