package com.condominios.sgc.domain.port;

import java.util.Optional;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.UsuarioModel;

public interface UsuarioPort {
    Optional<UsuarioModel> findById(String id);
    Optional<UsuarioModel> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    UsuarioModel save(UsuarioModel model);
    void deleteById(String id);
    PaginacionResponse<UsuarioModel> findAll(PaginacionRequest request);
}
