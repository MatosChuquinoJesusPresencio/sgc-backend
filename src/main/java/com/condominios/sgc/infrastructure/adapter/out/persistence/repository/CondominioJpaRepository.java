package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.CondominioEntity;

public interface CondominioJpaRepository extends JpaRepository<CondominioEntity, Long> {

    @EntityGraph(attributePaths = {
        "torres", "torres.pisos", "torres.pisos.apartamentos", "configuracion"
    })
    Optional<CondominioEntity> findWithTreeById(Long id);

    @Query("SELECT c FROM CondominioEntity c WHERE c.activo = true AND c.id NOT IN "
         + "(SELECT DISTINCT u.idCondominio FROM UsuarioEntity u WHERE u.idCondominio IS NOT NULL)")
    List<CondominioEntity> buscarActivosSinAdministrador();

    @Query("SELECT c.nombre FROM CondominioEntity c WHERE c.id = :id")
    Optional<String> findNombreById(@Param("id") Long id);

    @Query("SELECT c FROM CondominioEntity c WHERE "
         + "(:search IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :search, '%'))) "
         + "AND (:activo IS NULL OR c.activo = :activo)")
    Page<CondominioEntity> buscarTodos(@Param("search") String search,
                                       @Param("activo") Boolean activo,
                                       Pageable pageable);

    @Query("SELECT COUNT(c) FROM CondominioEntity c WHERE "
         + "(:search IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :search, '%'))) "
         + "AND (:activo IS NULL OR c.activo = :activo)")
    long contarTodos(@Param("search") String search,
                     @Param("activo") Boolean activo);

    @Query("SELECT c FROM CondominioEntity c ORDER BY c.fechaCreacion DESC")
    List<CondominioEntity> buscarRecientes(Pageable pageable);

    @Query("SELECT COUNT(c) FROM CondominioEntity c WHERE c.activo = true")
    long contarActivos();
}
