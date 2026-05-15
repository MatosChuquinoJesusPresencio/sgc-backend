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
        this(nombres, apellidos, correo, telefono, rol, activo);
        this.id = id;
        this.condominioId = condominioId;
    }

    public UsuarioModel(
        String nombres, 
        String apellidos, 
        String correo, 
        String telefono, 
        Rol rol,
        Boolean activo
    ) {
        this.nombres = requerirNoVacio(nombres, UsuarioException::nombresObligatorios);
        this.apellidos = requerirNoVacio(apellidos, UsuarioException::apellidosObligatorios);
        this.correo = requerirNoVacio(correo, UsuarioException::correoObligatorio);
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

    public void asignarCondominio(Long condominioId) {
        this.condominioId = requerirNoNulo(condominioId, UsuarioException::condominioIdObligatorio);
    }

    public void desasignarCondominio() {
        this.condominioId = null;
    }

    public void actualizarDatos(
        String nombres,
        String apellidos,
        String correo,
        String telefono,
        Rol rol
    ) {
        this.nombres = requerirNoVacio(nombres, UsuarioException::nombresObligatorios);
        this.apellidos = requerirNoVacio(apellidos, UsuarioException::apellidosObligatorios);
        this.correo = requerirNoVacio(correo, UsuarioException::correoObligatorio);
        this.telefono = requerirNoVacio(telefono, UsuarioException::telefonoObligatorio);
        this.rol = requerirNoNulo(rol, UsuarioException::rolObligatorio);
    }

    public void actualizarEstado(Boolean activo) {
        this.activo = requerirNoNulo(activo, UsuarioException::activoObligatorio);
    }
}
