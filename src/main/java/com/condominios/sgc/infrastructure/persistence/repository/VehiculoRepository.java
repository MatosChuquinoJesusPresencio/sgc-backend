package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.VehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Long>, JpaSpecificationExecutor<VehiculoEntity> {
    Optional<VehiculoEntity> findByPlaca(String placa);
    List<VehiculoEntity> findByIdCondominio(Long idCondominio);
    List<VehiculoEntity> findByIdPropietario(Long idPropietario);
    List<VehiculoEntity> findByIdInquilino(Long idInquilino);
}
