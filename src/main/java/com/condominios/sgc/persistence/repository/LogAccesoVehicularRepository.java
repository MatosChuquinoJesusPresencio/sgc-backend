package com.condominios.sgc.persistence.repository;

import com.condominios.sgc.persistence.entity.LogAccesoVehicularEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LogAccesoVehicularRepository extends JpaRepository<LogAccesoVehicularEntity, Long>,
        JpaSpecificationExecutor<LogAccesoVehicularEntity> {

    List<LogAccesoVehicularEntity> findByPlaca(String placa);

    List<LogAccesoVehicularEntity> findByVehiculoIdAndFechaSalidaIsNull(Long vehiculoId);
}
