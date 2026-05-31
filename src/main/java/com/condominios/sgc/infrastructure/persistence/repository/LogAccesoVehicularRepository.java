package com.condominios.sgc.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.condominios.sgc.infrastructure.persistence.entity.LogAccesoVehicularEntity;

import java.util.List;

@Repository
public interface LogAccesoVehicularRepository extends JpaRepository<LogAccesoVehicularEntity, Long>,
        JpaSpecificationExecutor<LogAccesoVehicularEntity> {

    List<LogAccesoVehicularEntity> findByPlaca(String placa);
    List<LogAccesoVehicularEntity> findByVehiculoIdAndFechaSalidaIsNull(Long vehiculoId);
}
