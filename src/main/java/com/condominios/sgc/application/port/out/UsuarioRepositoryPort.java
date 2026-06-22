package com.condominios.sgc.application.port.out;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.domain.model.UsuarioModel;

public interface UsuarioRepositoryPort {
    Optional<UsuarioModel> buscarPorId(Long id);
    Optional<UsuarioModel> buscarPorCorreo(String correo);
    UsuarioModel guardar(UsuarioModel modelo);
    void eliminarPorId(Long id);

    List<UsuarioModel> buscarAdministradores(String search, Boolean activo, int pagina, int tamano);
    long contarAdministradores(String search, Boolean activo);
    boolean existePorCorreo(String correo);
    List<UsuarioModel> buscarAdministradoresSinCondominio();
    Optional<UsuarioModel> buscarPorCondominioId(Long idCondominio);
    long contarPorRol(String rol);
    List<UsuarioModel> buscarRecientesPorRol(String rol, int limite);
    List<UsuarioModel> buscarTodos(String search, String rol, Boolean activo, int pagina, int tamano);
    long contarTodos(String search, String rol, Boolean activo);
}

