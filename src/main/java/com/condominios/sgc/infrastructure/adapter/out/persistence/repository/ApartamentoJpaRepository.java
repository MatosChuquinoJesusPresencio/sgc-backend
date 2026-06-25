package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.ApartamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApartamentoJpaRepository extends JpaRepository<ApartamentoEntity, Long> {
    List<ApartamentoEntity> findByIdPropietario(Long idPropietario);

    @Query("""
        SELECT a FROM ApartamentoEntity a
        JOIN a.piso p
        JOIN p.torre t
        WHERE t.condominio.id = :condominioId AND a.numero = :numero
        """)
    Optional<ApartamentoEntity> findByNumeroAndCondominioId(
            @Param("numero") Integer numero,
            @Param("condominioId") Long condominioId);
}
