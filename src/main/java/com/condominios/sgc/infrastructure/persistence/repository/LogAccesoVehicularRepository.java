package com.condominios.sgc.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.condominios.sgc.infrastructure.persistence.entity.LogAccesoVehicularEntity;

import java.util.List;

@Repository
public interface LogAccesoVehicularRepository extends JpaRepository<LogAccesoVehicularEntity, Long>,
        JpaSpecificationExecutor<LogAccesoVehicularEntity> {

    List<LogAccesoVehicularEntity> findByPlaca(String placa);

    List<LogAccesoVehicularEntity> findByVehiculoIdAndFechaSalidaIsNull(Long vehiculoId);

    @Query("SELECT lav FROM LogAccesoVehicularEntity lav " +
           "JOIN lav.vehiculo v " +
           "JOIN v.propietario u " +
           "JOIN u.condominio c " +
           "WHERE c.id = :condominioId")
    Page<LogAccesoVehicularEntity> findByCondominioId(Long condominioId, Pageable pageable);

    @Query("SELECT lav FROM LogAccesoVehicularEntity lav " +
           "JOIN lav.vehiculo v " +
           "JOIN v.inquilino i " +
           "JOIN i.apartamento a " +
           "WHERE a.id = :apartamentoId")
    Page<LogAccesoVehicularEntity> findByApartamentoId(Long apartamentoId, Pageable pageable);
}
