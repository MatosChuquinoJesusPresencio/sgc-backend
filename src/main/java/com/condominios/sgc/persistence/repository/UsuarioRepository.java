package com.condominios.sgc.persistence.repository;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, String>,
        JpaSpecificationExecutor<UsuarioEntity> {

    Optional<UsuarioEntity> findByCorreo(String correo);

    boolean existsByCorreo(String correo);

    List<UsuarioEntity> findByCondominioIdAndRol(Long condominioId, Rol rol);

    List<UsuarioEntity> findByApartamentoId(Long apartamentoId);
}
