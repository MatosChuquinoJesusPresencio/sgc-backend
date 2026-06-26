package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.ApartamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApartamentoJpaRepository extends JpaRepository<ApartamentoEntity, Long> {
    Optional<ApartamentoEntity> findByIdPropietario(Long idPropietario);

    @Query("""
        SELECT a FROM ApartamentoEntity a
        JOIN a.piso p
        JOIN p.torre t
        WHERE t.condominio.id = :condominioId AND a.numero = :numero
        """)
    Optional<ApartamentoEntity> findByNumeroAndCondominioId(
            @Param("numero") Integer numero,
            @Param("condominioId") Long condominioId);

    @Query(nativeQuery = true, value = """
        SELECT a.id, a.numero, a.metraje, a.derecho_estacionamiento,
               a.propietario_id, p.numero AS piso_numero, t.nombre AS torre_nombre,
               u.nombres, u.apellidos
        FROM apartamento a
        JOIN piso p ON p.id = a.piso_id
        JOIN torre t ON t.id = p.torre_id
        LEFT JOIN usuario u ON u.id = a.propietario_id
        WHERE t.condominio_id = :cid
        ORDER BY t.nombre, p.numero, a.numero
        LIMIT :limit OFFSET :offset
        """)
    List<Object[]> findApartamentosPage(
            @Param("cid") Long condominioId,
            @Param("offset") int offset,
            @Param("limit") int limit);

    @Query(nativeQuery = true, value = """
        SELECT COUNT(*)
        FROM apartamento a
        JOIN piso p ON p.id = a.piso_id
        JOIN torre t ON t.id = p.torre_id
        WHERE t.condominio_id = :cid
        """)
    long countApartamentosByCondominio(@Param("cid") Long condominioId);
}
