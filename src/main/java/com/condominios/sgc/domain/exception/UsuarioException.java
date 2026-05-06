package com.condominios.sgc.domain.exception;

public final class UsuarioException extends RuntimeException {
    private UsuarioException(String mensaje) {
        super(mensaje);
    }

    public static UsuarioException datosObligatorios() {
        return new UsuarioException("Nombres, correo y rol son obligatorios");
    }

    public static UsuarioException usuarioYaExistePorId(Long id) {
        return new UsuarioException("El usuario con id " + id + " ya existe");
    }

    public static UsuarioException usuarioYaExistePorCorreo(String correo) {
        return new UsuarioException("El usuario con correo " + correo + " ya existe");
    }

    public static UsuarioException correoInvalido() {
        return new UsuarioException("El formato del correo es invalido");
    }

    public static UsuarioException usuarioInactivo() {
        return new UsuarioException("El usuario se encuentra inactivo");
    }
}
