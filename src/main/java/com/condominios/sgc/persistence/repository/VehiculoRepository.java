package com.condominios.sgc.persistence.repository;

import com.condominios.sgc.persistence.entity.VehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Long>,
        JpaSpecificationExecutor<VehiculoEntity> {

    Optional<VehiculoEntity> findByPlaca(String placa);

    boolean existsByPlaca(String placa);

    List<VehiculoEntity> findByPropietarioUsuarioId(String usuarioId);

    List<VehiculoEntity> findByPropietarioInquilinoId(Long inquilinoId);
}
