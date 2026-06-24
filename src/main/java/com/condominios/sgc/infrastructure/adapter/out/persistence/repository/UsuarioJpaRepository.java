package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.UsuarioEntity;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByCorreo(String correo);

    boolean existsByCorreoIgnoreCase(String correo);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.rol = 'ADMINISTRADOR_CONDOMINIO' "
         + "AND (cast(:search as String) IS NULL OR LOWER(u.nombres) LIKE LOWER(CONCAT('%', cast(:search as String), '%')) "
         + "OR LOWER(u.apellidos) LIKE LOWER(CONCAT('%', cast(:search as String), '%'))) "
         + "AND (:activo IS NULL OR u.activo = :activo)")
    Page<UsuarioEntity> buscarAdministradores(@Param("search") String search,
                                               @Param("activo") Boolean activo,
                                               Pageable pageable);

    @Query("SELECT COUNT(u) FROM UsuarioEntity u WHERE u.rol = 'ADMINISTRADOR_CONDOMINIO' "
         + "AND (cast(:search as String) IS NULL OR LOWER(u.nombres) LIKE LOWER(CONCAT('%', cast(:search as String), '%')) "
         + "OR LOWER(u.apellidos) LIKE LOWER(CONCAT('%', cast(:search as String), '%'))) "
         + "AND (:activo IS NULL OR u.activo = :activo)")
    long contarAdministradores(@Param("search") String search,
                               @Param("activo") Boolean activo);

    List<UsuarioEntity> findByRolAndIdCondominioIsNull(String rol);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.idCondominio = :idCondominio AND u.rol = 'ADMINISTRADOR_CONDOMINIO'")
    Optional<UsuarioEntity> findByIdCondominio(@Param("idCondominio") Long idCondominio);

    long countByRol(String rol);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.rol = :rol ORDER BY u.fechaCreacion DESC")
    List<UsuarioEntity> buscarRecientesPorRol(@Param("rol") String rol, Pageable pageable);

    @Query("SELECT u FROM UsuarioEntity u WHERE "
         + "(cast(:search as String) IS NULL OR LOWER(u.nombres) LIKE LOWER(CONCAT('%', cast(:search as String), '%')) "
         + "OR LOWER(u.apellidos) LIKE LOWER(CONCAT('%', cast(:search as String), '%')) "
         + "OR LOWER(u.correo) LIKE LOWER(CONCAT('%', cast(:search as String), '%'))) "
         + "AND (:rol IS NULL OR u.rol = :rol) "
         + "AND (:activo IS NULL OR u.activo = :activo) "
         + "ORDER BY u.fechaCreacion DESC")
    List<UsuarioEntity> buscarTodos(@Param("search") String search,
                                    @Param("rol") String rol,
                                    @Param("activo") Boolean activo,
                                    Pageable pageable);

    @Query("SELECT COUNT(u) FROM UsuarioEntity u WHERE "
         + "(cast(:search as String) IS NULL OR LOWER(u.nombres) LIKE LOWER(CONCAT('%', cast(:search as String), '%')) "
         + "OR LOWER(u.apellidos) LIKE LOWER(CONCAT('%', cast(:search as String), '%')) "
         + "OR LOWER(u.correo) LIKE LOWER(CONCAT('%', cast(:search as String), '%'))) "
         + "AND (:rol IS NULL OR u.rol = :rol) "
         + "AND (:activo IS NULL OR u.activo = :activo)")
    long contarTodos(@Param("search") String search,
                     @Param("rol") String rol,
                     @Param("activo") Boolean activo);

    @Query("SELECT COUNT(u) FROM UsuarioEntity u WHERE u.idCondominio = :idCondominio AND u.rol = :rol")
    long countByIdCondominioAndRol(@Param("idCondominio") Long idCondominio, @Param("rol") String rol);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.idCondominio = :idCondominio "
         + "AND (cast(:search as String) IS NULL OR LOWER(u.nombres) LIKE LOWER(CONCAT('%', cast(:search as String), '%')) "
         + "OR LOWER(u.apellidos) LIKE LOWER(CONCAT('%', cast(:search as String), '%')) "
         + "OR LOWER(u.correo) LIKE LOWER(CONCAT('%', cast(:search as String), '%'))) "
         + "AND (:rol IS NULL OR u.rol = :rol) "
         + "AND (:activo IS NULL OR u.activo = :activo) "
         + "ORDER BY u.fechaCreacion DESC")
    List<UsuarioEntity> buscarPorCondominio(@Param("idCondominio") Long idCondominio,
                                            @Param("search") String search,
                                            @Param("rol") String rol,
                                            @Param("activo") Boolean activo,
                                            Pageable pageable);

    @Query("SELECT COUNT(u) FROM UsuarioEntity u WHERE u.idCondominio = :idCondominio "
         + "AND (cast(:search as String) IS NULL OR LOWER(u.nombres) LIKE LOWER(CONCAT('%', cast(:search as String), '%')) "
         + "OR LOWER(u.apellidos) LIKE LOWER(CONCAT('%', cast(:search as String), '%')) "
         + "OR LOWER(u.correo) LIKE LOWER(CONCAT('%', cast(:search as String), '%'))) "
         + "AND (:rol IS NULL OR u.rol = :rol) "
         + "AND (:activo IS NULL OR u.activo = :activo)")
    long contarPorCondominio(@Param("idCondominio") Long idCondominio,
                             @Param("search") String search,
                             @Param("rol") String rol,
                             @Param("activo") Boolean activo);
}
