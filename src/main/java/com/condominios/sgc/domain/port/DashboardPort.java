package com.condominios.sgc.domain.port;

import java.util.List;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.model.UsuarioModel;

public interface DashboardPort {
    int contarUsuariosPorRol(Rol rol);
    int contarTodosLosUsuario();
    int contarUsuariosPorActivo(Boolean activo);
    List<UsuarioModel> obtenerCincoUsuariosRecientes();
    List<CondominioModel> obtenerCincoCondominiosRecientes();
}
