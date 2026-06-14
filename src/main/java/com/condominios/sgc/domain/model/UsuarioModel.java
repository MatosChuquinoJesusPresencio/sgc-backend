package com.condominios.sgc.domain.model;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.exception.UsuarioException;
import static com.condominios.sgc.domain.util.ValidacionUtil.*;

public class UsuarioModel {
    private Long id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private Rol rol;
    private Boolean activo;
    private String contrasena;
    private String correoPendiente;
    private Boolean correoVerificado;
    private Long idCondominio;

    public UsuarioModel(Long id, String nombres, String apellidos, String correo, 
            String telefono, Rol rol, Boolean activo, String contrasena,
            String correoPendiente, Boolean correoVerificado, Long idCondominio) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
        this.activo = activo;
        this.contrasena = contrasena;
        this.correoPendiente = correoPendiente;
        this.correoVerificado = correoVerificado;
        this.idCondominio = idCondominio;
    }

    public UsuarioModel(String nombres, String apellidos, String correo, 
            String telefono, Rol rol, String contrasena, Long idCondominio) {
        this.id = null;
        this.nombres = requerido(nombres, UsuarioException::nombreRequerido);
        this.apellidos = requerido(apellidos, UsuarioException::apellidoRequerido);
        this.correo = correoValido(correo, UsuarioException::correoInvalido);
        this.telefono = telefono;
        this.rol = noNulo(rol, UsuarioException::rolRequerido);
        this.activo = true;
        this.contrasena = requerido(contrasena, UsuarioException::contrasenaRequerida);
        this.correoPendiente = null;
        this.correoVerificado = true;
        this.idCondominio = noNulo(idCondominio, UsuarioException::condominioRequerido);
    }

    public Long getId() { return id; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public Rol getRol() { return rol; }
    public Boolean getActivo() { return activo; }
    public String getContrasena() { return contrasena; }
    public String getCorreoPendiente() { return correoPendiente; }
    public Boolean getCorreoVerificado() { return correoVerificado; }
    public Long getIdCondominio() { return idCondominio; }

    public void cambiarCorreo(String nuevoCorreo) {
        distinto(
            this.correo,
            correoValido(nuevoCorreo, UsuarioException::nuevoCorreoRequerido),
            UsuarioException::correoIgualAlActual
        );
        this.correoPendiente = nuevoCorreo;
        this.correoVerificado = false;
    }

    public void confirmarCorreo() {
        noNulo(this.correoPendiente, UsuarioException::sinCorreoPendiente);
        this.correo = this.correoPendiente;
        this.correoPendiente = null;
        this.correoVerificado = true;
    }

    public void actualizar(String nombres, String apellidos, 
            String telefono, Rol rol, Long idCondominio) {
        this.nombres = requerido(nombres, UsuarioException::nombreRequerido);
        this.apellidos = requerido(apellidos, UsuarioException::apellidoRequerido);
        this.telefono = telefono;
        this.rol = noNulo(rol, UsuarioException::rolRequerido);
        this.idCondominio = idCondominio;
    }

    public void desactivar() {
        this.activo = false;
    }

    public void activar() {
        this.activo = true;
    }

    public void asignarCondominio(Long idCondominio) {
        this.idCondominio = noNulo(idCondominio, UsuarioException::condominioRequerido);
    }

    public void desasignarCondominio() {
        this.idCondominio = null;
    }

    public void cambiarContrasena(String contrasena) {
        this.contrasena = requerido(contrasena, UsuarioException::contrasenaRequerida);
    }
}
