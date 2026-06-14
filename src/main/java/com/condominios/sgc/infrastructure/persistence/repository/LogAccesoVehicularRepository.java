package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.LogAccesoVehicularEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LogAccesoVehicularRepository extends JpaRepository<LogAccesoVehicularEntity, Long>, JpaSpecificationExecutor<LogAccesoVehicularEntity> {
}
