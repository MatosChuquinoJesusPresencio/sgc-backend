package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.LogAccesoVehicularEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.time.Instant;
import java.util.List;

public interface LogAccesoVehicularRepository extends JpaRepository<LogAccesoVehicularEntity, Long>, JpaSpecificationExecutor<LogAccesoVehicularEntity> {
    List<LogAccesoVehicularEntity> findByIdVehiculoInOrderByFechaEntradaDesc(List<Long> idsVehiculo, org.springframework.data.domain.Pageable pageable);
    long countByIdVehiculoInAndFechaEntradaAfter(List<Long> idsVehiculo, Instant desde);
    long countByIdVehiculoInAndFechaSalidaIsNull(List<Long> idsVehiculo);
}
