package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;
import com.condominios.sgc.domain.auxiliar.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>, JpaSpecificationExecutor<UsuarioEntity> {
    Optional<UsuarioEntity> findByCorreo(String correo);
    List<UsuarioEntity> findByRol(Rol rol);
    List<UsuarioEntity> findByIdCondominio(Long idCondominio);
    List<UsuarioEntity> findByFechaCreacionAfter(Instant desde);
}
