package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.VehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehiculoJpaRepository extends JpaRepository<VehiculoEntity, Long> {
    Optional<VehiculoEntity> findByPlaca(String placa);
    List<VehiculoEntity> findByIdCondominio(Long idCondominio);
    List<VehiculoEntity> findByIdPropietario(Long idPropietario);
    List<VehiculoEntity> findByIdInquilino(Long idInquilino);
    long countByIdCondominio(Long idCondominio);
    long countByIdPropietario(Long idPropietario);
}
