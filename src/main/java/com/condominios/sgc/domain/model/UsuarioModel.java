package com.condominios.sgc.domain.model;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.exception.UsuarioException;

public class UsuarioModel {
    private String id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private Rol rol;
    private Boolean activo;
    private Long condominioId;
    private String correoPendiente;
    private Boolean correoVerificado = false;

    public UsuarioModel(
        String id, 
        String nombres, 
        String apellidos, 
        String correo, 
        String telefono, 
        Rol rol,
        Boolean activo,
        Long condominioId
    ) {
        this(id, nombres, apellidos, correo, telefono, rol, activo);
        this.condominioId = condominioId;
    }

    public UsuarioModel(
        String id,
        String nombres, 
        String apellidos, 
        String correo, 
        String telefono, 
        Rol rol,
        Boolean activo
    ) {
        this.id = requerirNoVacio(id, UsuarioException::idObligatorio);
        validarYAsignarDatos(nombres, apellidos, telefono, rol, activo);
        this.correo = requerirEmailValido(correo, UsuarioException::correoInvalido);
        this.correoVerificado = false;
    }

    private void validarYAsignarDatos(String nombres, String apellidos, String telefono, Rol rol, Boolean activo) {
        this.nombres = requerirNoVacio(nombres, UsuarioException::nombresObligatorios);
        this.apellidos = requerirNoVacio(apellidos, UsuarioException::apellidosObligatorios);
        this.telefono = requerirNoVacio(telefono, UsuarioException::telefonoObligatorio);
        this.rol = requerirNoNulo(rol, UsuarioException::rolObligatorio);
        this.activo = requerirNoNulo(activo, UsuarioException::activoObligatorio);
    }

    public String getId() { return id; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public Rol getRol() { return rol; }
    public Boolean isActivo() { return activo; }
    public Long getCondominioId() { return condominioId; }
    public String getCorreoPendiente() { return correoPendiente; }
    public Boolean isCorreoVerificado() { return correoVerificado; }

    public void asignarCondominio(Long condominioId) {
        this.condominioId = requerirNoNulo(condominioId, UsuarioException::condominioIdObligatorio);
    }

    public void desasignarCondominio() {
        this.condominioId = null;
    }

    public void actualizarCorreo(String correo) {
        this.correo = requerirEmailValido(correo, UsuarioException::correoInvalido);
    }

    public void pendienteVerificarCorreo(String nuevoCorreo) {
        this.correoPendiente = requerirEmailValido(nuevoCorreo, UsuarioException::correoInvalido);
        this.correoVerificado = false;
    }

    public void confirmarCorreo() {
        this.correo = this.correoPendiente;
        this.correoPendiente = null;
        this.correoVerificado = true;
    }

    public void asignarCorreoVerificado(Boolean verificado) {
        this.correoVerificado = requerirNoNulo(verificado, UsuarioException::activoObligatorio);
    }

    public void asignarCorreoPendiente(String correoPendiente) {
        this.correoPendiente = correoPendiente;
    }

    public void actualizarDatos(
        String nombres,
        String apellidos,
        String telefono,
        Rol rol
    ) {
        validarYAsignarDatos(nombres, apellidos, telefono, rol, this.activo);
    }

    public void actualizarEstado(Boolean activo) {
        this.activo = requerirNoNulo(activo, UsuarioException::activoObligatorio);
    }
}
