package com.condominios.sgc.domain.model;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.exception.UsuarioException;

public class UsuarioModel {
    private Long id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private Rol rol;
    private Boolean activo;
    private Long condominioId;
    private String correoPendiente;
    private Boolean correoVerificado;
    private String contrasena;

    public UsuarioModel(
        Long id,
        String nombres,
        String apellidos,
        String correo,
        String telefono,
        Rol rol,
        Boolean activo,
        Long condominioId,
        String correoPendiente,
        Boolean correoVerificado, 
        String contrasena
    ) {
        this.id = id;
        asignarDatos(nombres, apellidos, telefono, rol, condominioId);
        this.correo = requerirCorreoElectronicoValido(correo, UsuarioException::correoInvalido);
        this.activo = requerirNoNulo(activo, UsuarioException::activoObligatorio);
        this.correoPendiente = correoPendiente;
        this.correoVerificado = correoVerificado;
        this.contrasena = requerirNoVacio(contrasena, UsuarioException::contrasenaObligatoria);
    }

    public UsuarioModel(
        String nombres,
        String apellidos,
        String correo,
        String telefono,
        Rol rol,
        Long condominioId,
        String contrasena
    ) {
        this(null, 
            nombres,
            apellidos,
            correo, telefono,
            rol, true, 
            condominioId,
            null,
            true,
            contrasena
        );
    }

    public Long getId() { return id; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public Rol getRol() { return rol; }
    public Boolean isActivo() { return activo; }
    public Long getCondominioId() { return condominioId; }
    public String getCorreoPendiente() { return correoPendiente; }
    public Boolean isCorreoVerificado() { return correoVerificado; }
    public String getContrasena() { return contrasena; }

    public void cambiarCorreo(String nuevoCorreo) {
        this.correoPendiente = nuevoCorreo;
        this.correoVerificado = false;
    }

    public void confirmarCambioCorreo() {
        this.correo = this.correoPendiente;
        this.correoPendiente = null;
        this.correoVerificado = true;
    }

    public void actualizarContrasena(String nuevaContrasena) {
        this.contrasena = nuevaContrasena;
    }

    public void actualizar(
        String nuevoNombres,
        String nuevoApellidos,
        String nuevoTelefono,
        Rol nuevoRol,
        Long condominioId
    ) {
        asignarDatos(nuevoNombres, nuevoApellidos, nuevoTelefono, nuevoRol, condominioId);
    }

    private void asignarDatos(String nombres, String apellidos, String telefono, Rol rol, Long condominioId) {
        this.nombres = requerirNoVacio(nombres, UsuarioException::nombresObligatorios);
        this.apellidos = requerirNoVacio(apellidos, UsuarioException::apellidosObligatorios);
        this.telefono = requerirNoVacio(telefono, UsuarioException::telefonoObligatorio);
        this.rol = requerirNoNulo(rol, UsuarioException::rolObligatorio);
        this.condominioId = requerirNoNulo(condominioId, UsuarioException::condominioIdObligatorio);
    }

    public void cambiarActivo(boolean nuevoActivo) {
        this.activo = nuevoActivo;
    }

    public void asignarCondominio(Long condominioId) {
        this.condominioId = requerirNoNulo(condominioId, UsuarioException::condominioIdObligatorio);
    }

    public void desasignarCondominio() {
        this.condominioId = null;
    }
}
