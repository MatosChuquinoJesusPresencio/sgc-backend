package com.condominios.sgc.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.condominios.sgc.infrastructure.persistence.entity.LogAccesoVehicularEntity;

import java.util.List;
import org.springframework.data.repository.query.Param;

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
    Page<LogAccesoVehicularEntity> findByCondominioId(@Param("condominioId") Long condominioId, Pageable pageable);

    @Query("SELECT lav FROM LogAccesoVehicularEntity lav " +
           "JOIN lav.vehiculo v " +
           "JOIN v.inquilino i " +
           "JOIN i.apartamento a " +
           "WHERE a.id = :apartamentoId")
    Page<LogAccesoVehicularEntity> findByApartamentoId(@Param("apartamentoId") Long apartamentoId, Pageable pageable);
}
