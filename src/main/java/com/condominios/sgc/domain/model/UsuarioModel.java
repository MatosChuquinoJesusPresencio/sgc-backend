package com.condominios.sgc.domain.model;

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
    private Long apartamentoId;
    private Long condominioId;

    public UsuarioModel(Long id, String nombres, String apellidos, String correo, String telefono, Rol rol,
            Long condominioId) {
        validarDatos(nombres, apellidos, correo, rol);
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
        this.condominioId = condominioId;
        this.activo = true;
    }

    public Long getId() { return id; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public Rol getRol() { return rol; }
    public Boolean isActivo() { return activo; }
    public Long getApartamentoId() { return apartamentoId; }
    public Long getCondominioId() { return condominioId; }

    private void validarDatos(String nombres, String apellidos, String correo, Rol rol) {
        if (nombres == null || nombres.trim().isEmpty()) {
            throw UsuarioException.datosObligatorios();
        }
        if (correo == null || correo.trim().isEmpty() || !correo.contains("@")) {
            throw UsuarioException.correoInvalido();
        }
        if (rol == null) {
            throw UsuarioException.datosObligatorios();
        }
    }

    public void desactivar() {
        this.activo = false;
    }

    public void activar() {
        this.activo = true;
    }

    public void asignarApartamento(Long apartamentoId) {
        if (!this.activo) {
            throw UsuarioException.usuarioInactivo();
        }
        if (apartamentoId == null) {
            throw UsuarioException.datosObligatorios();
        }
        this.apartamentoId = apartamentoId;
    }

    public void desactivarApartamento() {
        this.apartamentoId = null;
    }

    public void asignarCondominio(Long condominioId) {
        if (!this.activo) {
            throw UsuarioException.usuarioInactivo();
        }
        if (condominioId == null) {
            throw UsuarioException.datosObligatorios();
        }
        this.condominioId = condominioId;
    }

    public void desactivarCondominio() {
        this.condominioId = null;
    }

    public void actualizarDatos(String nombres, String apellidos, String correo, String telefono, Rol rol) {
        validarDatos(nombres, apellidos, correo, rol);
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
    }
}
