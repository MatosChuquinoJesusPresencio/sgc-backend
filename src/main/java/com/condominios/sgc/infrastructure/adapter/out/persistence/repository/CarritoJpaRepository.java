package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.CarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarritoJpaRepository extends JpaRepository<CarritoEntity, Long> {
    @Query("SELECT COUNT(c) FROM CarritoEntity c WHERE c.idCondominio = :idCondominio")
    long countByIdCondominio(@Param("idCondominio") Long idCondominio);

    List<CarritoEntity> findByIdCondominioOrderByIdAsc(Long idCondominio);

    Optional<CarritoEntity> findByCodigo(String codigo);
}
