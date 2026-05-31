package com.condominios.sgc.application.service;

import org.springframework.stereotype.Service;

import com.condominios.sgc.application.dto.ActualizarUsuarioRequest;
import com.condominios.sgc.application.dto.CrearUsuarioRequest;
import com.condominios.sgc.application.usecase.ActualizarCorreoUseCase;
import com.condominios.sgc.application.usecase.ActualizarEstadoUsuarioUseCase;
import com.condominios.sgc.application.usecase.ActualizarUsuarioUseCase;
import com.condominios.sgc.application.usecase.AsignarCondominioUseCase;
import com.condominios.sgc.application.usecase.CrearUsuarioUseCase;
import com.condominios.sgc.application.usecase.EliminarUsuarioUseCase;
import com.condominios.sgc.application.usecase.EnviarRecuperacionContrasenaUseCase;
import com.condominios.sgc.application.usecase.ListarUsuariosUseCase;
import com.condominios.sgc.application.usecase.ObtenerUsuarioUseCase;
import com.condominios.sgc.application.usecase.RestablecerContrasenaUseCase;
import com.condominios.sgc.application.usecase.VerificarCorreoUseCase;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;

import com.condominios.sgc.domain.model.UsuarioModel;

import java.util.Optional;

@Service
public class UsuarioService {

    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final ActualizarUsuarioUseCase actualizarUsuarioUseCase;
    private final ObtenerUsuarioUseCase obtenerUsuarioUseCase;
    private final EliminarUsuarioUseCase eliminarUsuarioUseCase;
    private final ListarUsuariosUseCase listarUsuariosUseCase;
    private final ActualizarEstadoUsuarioUseCase actualizarEstadoUsuarioUseCase;
    private final AsignarCondominioUseCase asignarCondominioUseCase;
    private final ActualizarCorreoUseCase actualizarCorreoUseCase;
    private final VerificarCorreoUseCase verificarCorreoUseCase;
    private final EnviarRecuperacionContrasenaUseCase enviarRecuperacionContrasenaUseCase;
    private final RestablecerContrasenaUseCase restablecerContrasenaUseCase;

    public UsuarioService(
            CrearUsuarioUseCase crearUsuarioUseCase,
            ActualizarUsuarioUseCase actualizarUsuarioUseCase,
            ObtenerUsuarioUseCase obtenerUsuarioUseCase,
            EliminarUsuarioUseCase eliminarUsuarioUseCase,
            ListarUsuariosUseCase listarUsuariosUseCase,
            ActualizarEstadoUsuarioUseCase actualizarEstadoUsuarioUseCase,
            AsignarCondominioUseCase asignarCondominioUseCase,
            ActualizarCorreoUseCase actualizarCorreoUseCase,
            VerificarCorreoUseCase verificarCorreoUseCase,
            EnviarRecuperacionContrasenaUseCase enviarRecuperacionContrasenaUseCase,
            RestablecerContrasenaUseCase restablecerContrasenaUseCase) {
        this.crearUsuarioUseCase = crearUsuarioUseCase;
        this.actualizarUsuarioUseCase = actualizarUsuarioUseCase;
        this.obtenerUsuarioUseCase = obtenerUsuarioUseCase;
        this.eliminarUsuarioUseCase = eliminarUsuarioUseCase;
        this.listarUsuariosUseCase = listarUsuariosUseCase;
        this.actualizarEstadoUsuarioUseCase = actualizarEstadoUsuarioUseCase;
        this.asignarCondominioUseCase = asignarCondominioUseCase;
        this.actualizarCorreoUseCase = actualizarCorreoUseCase;
        this.verificarCorreoUseCase = verificarCorreoUseCase;
        this.enviarRecuperacionContrasenaUseCase = enviarRecuperacionContrasenaUseCase;
        this.restablecerContrasenaUseCase = restablecerContrasenaUseCase;
    }

    public UsuarioModel crear(CrearUsuarioRequest request, Rol rolAutenticado) {
        return crearUsuarioUseCase.ejecutar(request, rolAutenticado);
    }

    public UsuarioModel actualizar(Long id, ActualizarUsuarioRequest request, Rol rolAutenticado) {
        return actualizarUsuarioUseCase.ejecutar(id, request, rolAutenticado);
    }

    public UsuarioModel obtener(Long id) {
        return obtenerUsuarioUseCase.ejecutar(id);
    }

    public void eliminar(Long id) {
        eliminarUsuarioUseCase.ejecutar(id);
    }

    public PaginacionResponse<UsuarioModel> listar(PaginacionRequest request) {
        return listarUsuariosUseCase.ejecutar(request);
    }

    public UsuarioModel actualizarEstado(Long id, Boolean activo, Rol rolAutenticado) {
        return actualizarEstadoUsuarioUseCase.ejecutar(id, activo, rolAutenticado);
    }

    public UsuarioModel asignarCondominio(Long usuarioId, Long condominioId) {
        return asignarCondominioUseCase.ejecutar(usuarioId, condominioId);
    }

    public UsuarioModel actualizarCorreo(Long id, String nuevoCorreo, String token) {
        return actualizarCorreoUseCase.ejecutar(id, nuevoCorreo, token);
    }

    public UsuarioModel verificarCorreo(String token) {
        return verificarCorreoUseCase.ejecutar(token);
    }

    public Optional<String> enviarRecuperacionContrasena(String email, String token) {
        return enviarRecuperacionContrasenaUseCase.ejecutar(email, token);
    }

    public void restablecerContrasena(String token, String nuevaContrasena) {
        restablecerContrasenaUseCase.ejecutar(token, nuevaContrasena);
    }
}
