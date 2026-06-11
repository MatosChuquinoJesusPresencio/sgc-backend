package com.condominios.sgc.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.condominios.sgc.infrastructure.persistence.entity.ConfiguracionEntity;

import java.util.Optional;

public interface ConfiguracionRepository extends JpaRepository<ConfiguracionEntity, Long>,
        JpaSpecificationExecutor<ConfiguracionEntity> {

    Optional<ConfiguracionEntity> findByCondominioId(Long condominioId);

    @Query("SELECT c FROM ConfiguracionEntity c " +
           "WHERE c.condominio.id = " +
           "(SELECT a.piso.torre.condominio.id FROM ApartamentoEntity a WHERE a.id = :apartamentoId)")
    Optional<ConfiguracionEntity> findByApartamentoId(@Param("apartamentoId") Long apartamentoId);
}
