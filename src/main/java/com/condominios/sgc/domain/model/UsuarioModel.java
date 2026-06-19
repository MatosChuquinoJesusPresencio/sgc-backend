package com.condominios.sgc.domain.model;

import com.condominios.sgc.domain.type.Rol;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import com.condominios.sgc.domain.shared.value_objects.Correo;
import com.condominios.sgc.domain.shared.value_objects.NombreCompleto;
import com.condominios.sgc.domain.shared.value_objects.Telefono;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

import java.time.Instant;

public class UsuarioModel {
    private Long id;
    private NombreCompleto nombreCompleto;
    private Correo correo;
    private Telefono telefono;
    private Rol rol;
    private Boolean activo;
    private String contrasena;
    private Correo correoPendiente;
    private Boolean correoVerificado;
    private Long idCondominio;
    private Instant fechaCreacion;

    public UsuarioModel(Long id, NombreCompleto nombreCompleto, Correo correo,
            Telefono telefono, Rol rol, Boolean activo, String contrasena,
            Correo correoPendiente, Boolean correoVerificado, Long idCondominio,
            Instant fechaCreacion) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
        this.activo = activo;
        this.contrasena = contrasena;
        this.correoPendiente = correoPendiente;
        this.correoVerificado = correoVerificado;
        this.idCondominio = idCondominio;
        this.fechaCreacion = fechaCreacion;
    }

    public UsuarioModel(String nombres, String apellidos, String correo,
            String telefono, Rol rol, String contrasena) {
        this.id = null;
        this.nombreCompleto = new NombreCompleto(nombres, apellidos);
        this.correo = new Correo(correo);
        this.telefono = new Telefono(telefono);
        this.rol = noNulo(rol, UsuarioException::rolRequerido);
        this.activo = true;
        this.contrasena = requerido(contrasena, UsuarioException::contrasenaRequerida);
        this.correoPendiente = null;
        this.correoVerificado = true;
        this.idCondominio = null;
        this.fechaCreacion = Instant.now();
    }

    public Long getId() { return id; }
    public NombreCompleto getNombreCompleto() { return nombreCompleto; }
    public String getNombres() { return nombreCompleto.nombres(); }
    public String getApellidos() { return nombreCompleto.apellidos(); }
    public Correo getCorreo() { return correo; }
    public Telefono getTelefono() { return telefono; }
    public Rol getRol() { return rol; }
    public Boolean getActivo() { return activo; }
    public String getContrasena() { return contrasena; }
    public Correo getCorreoPendiente() { return correoPendiente; }
    public Boolean getCorreoVerificado() { return correoVerificado; }
    public Long getIdCondominio() { return idCondominio; }
    public Instant getFechaCreacion() { return fechaCreacion; }

    public void cambiarCorreo(String nuevoCorreo) {
        var nuevo = new Correo(nuevoCorreo);
        distinto(this.correo, nuevo, UsuarioException::correoIgualAlActual);
        this.correoPendiente = nuevo;
        this.correoVerificado = false;
    }

    public void confirmarCorreo() {
        noNulo(this.correoPendiente, UsuarioException::sinCorreoPendiente);
        this.correo = this.correoPendiente;
        this.correoPendiente = null;
        this.correoVerificado = true;
    }

    public void actualizar(String nombres, String apellidos,
            String telefono) {
        this.nombreCompleto = new NombreCompleto(nombres, apellidos);
        this.telefono = new Telefono(telefono);
    }

    public void cambiarRol(Rol rol) {
        this.rol = noNulo(rol, UsuarioException::rolRequerido);
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
