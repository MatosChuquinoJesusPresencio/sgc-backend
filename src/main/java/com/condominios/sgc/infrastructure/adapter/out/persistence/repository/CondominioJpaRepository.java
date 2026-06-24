package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.CondominioEntity;

public interface CondominioJpaRepository extends JpaRepository<CondominioEntity, Long> {

    Optional<CondominioEntity> findByNombre(String nombre);

    @Query("SELECT c FROM CondominioEntity c WHERE c.activo = true AND c.id NOT IN "
         + "(SELECT DISTINCT u.idCondominio FROM UsuarioEntity u WHERE u.idCondominio IS NOT NULL AND u.rol = 'ADMINISTRADOR_CONDOMINIO')")
    List<CondominioEntity> buscarActivosSinAdministrador();

    @Query("SELECT c.nombre FROM CondominioEntity c WHERE c.id = :id")
    Optional<String> findNombreById(@Param("id") Long id);

    @Query(value = "SELECT * FROM condominio c WHERE "
         + "(cast(:search as text) IS NULL OR unaccent(LOWER(c.nombre)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%')))) "
         + "AND (:activo IS NULL OR c.activo = :activo)",
         countQuery = "SELECT count(*) FROM condominio c WHERE "
         + "(cast(:search as text) IS NULL OR unaccent(LOWER(c.nombre)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%')))) "
         + "AND (:activo IS NULL OR c.activo = :activo)",
         nativeQuery = true)
    Page<CondominioEntity> buscarTodos(@Param("search") String search,
                                       @Param("activo") Boolean activo,
                                       Pageable pageable);

    @Query(value = "SELECT count(*) FROM condominio c WHERE "
         + "(cast(:search as text) IS NULL OR unaccent(LOWER(c.nombre)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%')))) "
         + "AND (:activo IS NULL OR c.activo = :activo)",
         nativeQuery = true)
    long contarTodos(@Param("search") String search,
                     @Param("activo") Boolean activo);

    @Query("SELECT c FROM CondominioEntity c ORDER BY c.fechaCreacion DESC")
    List<CondominioEntity> buscarRecientes(Pageable pageable);

    @Query("SELECT COUNT(c) FROM CondominioEntity c WHERE c.activo = true")
    long contarActivos();
}
