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

    @Query(value = "SELECT * FROM usuario u WHERE u.rol = 'ADMINISTRADOR_CONDOMINIO' "
         + "AND (cast(:search as text) IS NULL OR unaccent(LOWER(u.nombres)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%'))) "
         + "OR unaccent(LOWER(u.apellidos)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%')))) "
         + "AND (:activo IS NULL OR u.activo = :activo)",
         countQuery = "SELECT count(*) FROM usuario u WHERE u.rol = 'ADMINISTRADOR_CONDOMINIO' "
         + "AND (cast(:search as text) IS NULL OR unaccent(LOWER(u.nombres)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%'))) "
         + "OR unaccent(LOWER(u.apellidos)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%')))) "
         + "AND (:activo IS NULL OR u.activo = :activo)",
         nativeQuery = true)
    Page<UsuarioEntity> buscarAdministradores(@Param("search") String search,
                                               @Param("activo") Boolean activo,
                                               Pageable pageable);

    @Query(value = "SELECT count(*) FROM usuario u WHERE u.rol = 'ADMINISTRADOR_CONDOMINIO' "
         + "AND (cast(:search as text) IS NULL OR unaccent(LOWER(u.nombres)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%'))) "
         + "OR unaccent(LOWER(u.apellidos)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%')))) "
         + "AND (:activo IS NULL OR u.activo = :activo)",
         nativeQuery = true)
    long contarAdministradores(@Param("search") String search,
                                @Param("activo") Boolean activo);

    List<UsuarioEntity> findByRolAndIdCondominioIsNull(String rol);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.idCondominio = :idCondominio AND u.rol = 'ADMINISTRADOR_CONDOMINIO'")
    Optional<UsuarioEntity> findByIdCondominio(@Param("idCondominio") Long idCondominio);

    long countByRol(String rol);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.rol = :rol ORDER BY u.fechaCreacion DESC")
    List<UsuarioEntity> buscarRecientesPorRol(@Param("rol") String rol, Pageable pageable);

    @Query(value = "SELECT * FROM usuario u WHERE "
         + "(cast(:search as text) IS NULL OR unaccent(LOWER(u.nombres)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%'))) "
         + "OR unaccent(LOWER(u.apellidos)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%'))) "
         + "OR unaccent(LOWER(u.correo)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%')))) "
         + "AND (:rol IS NULL OR u.rol = :rol) "
         + "AND (:activo IS NULL OR u.activo = :activo) "
         + "ORDER BY u.fechaCreacion DESC",
         nativeQuery = true)
    List<UsuarioEntity> buscarTodos(@Param("search") String search,
                                    @Param("rol") String rol,
                                    @Param("activo") Boolean activo,
                                    Pageable pageable);

    @Query(value = "SELECT count(*) FROM usuario u WHERE "
         + "(cast(:search as text) IS NULL OR unaccent(LOWER(u.nombres)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%'))) "
         + "OR unaccent(LOWER(u.apellidos)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%'))) "
         + "OR unaccent(LOWER(u.correo)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%')))) "
         + "AND (:rol IS NULL OR u.rol = :rol) "
         + "AND (:activo IS NULL OR u.activo = :activo)",
         nativeQuery = true)
    long contarTodos(@Param("search") String search,
                     @Param("rol") String rol,
                     @Param("activo") Boolean activo);

    @Query("SELECT COUNT(u) FROM UsuarioEntity u WHERE u.idCondominio = :idCondominio AND u.rol = :rol")
    long countByIdCondominioAndRol(@Param("idCondominio") Long idCondominio, @Param("rol") String rol);

    @Query(value = "SELECT * FROM usuario u WHERE u.idCondominio = :idCondominio "
         + "AND (cast(:search as text) IS NULL OR unaccent(LOWER(u.nombres)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%'))) "
         + "OR unaccent(LOWER(u.apellidos)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%'))) "
         + "OR unaccent(LOWER(u.correo)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%')))) "
         + "AND (:rol IS NULL OR u.rol = :rol) "
         + "AND (:activo IS NULL OR u.activo = :activo) "
         + "ORDER BY u.fechaCreacion DESC",
         nativeQuery = true)
    List<UsuarioEntity> buscarPorCondominio(@Param("idCondominio") Long idCondominio,
                                            @Param("search") String search,
                                            @Param("rol") String rol,
                                            @Param("activo") Boolean activo,
                                            Pageable pageable);

    @Query(value = "SELECT count(*) FROM usuario u WHERE u.idCondominio = :idCondominio "
         + "AND (cast(:search as text) IS NULL OR unaccent(LOWER(u.nombres)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%'))) "
         + "OR unaccent(LOWER(u.apellidos)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%'))) "
         + "OR unaccent(LOWER(u.correo)) LIKE unaccent(LOWER(CONCAT('%', cast(:search as text), '%')))) "
         + "AND (:rol IS NULL OR u.rol = :rol) "
         + "AND (:activo IS NULL OR u.activo = :activo)",
         nativeQuery = true)
    long contarPorCondominio(@Param("idCondominio") Long idCondominio,
                             @Param("search") String search,
                             @Param("rol") String rol,
                             @Param("activo") Boolean activo);
}
