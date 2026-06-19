package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.LogAccesoVehicularEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogAccesoVehicularJpaRepository extends JpaRepository<LogAccesoVehicularEntity, Long> {
    List<LogAccesoVehicularEntity> findByPlaca(String placa);
    List<LogAccesoVehicularEntity> findByIdVehiculo(Long idVehiculo);
    List<LogAccesoVehicularEntity> findByIdEstacionamiento(Long idEstacionamiento);
}
