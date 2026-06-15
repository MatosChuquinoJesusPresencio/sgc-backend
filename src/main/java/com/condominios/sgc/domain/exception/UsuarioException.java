package com.condominios.sgc.domain.exception;

public class UsuarioException extends DominioException {
    private UsuarioException(String mensaje) {
        super(mensaje);
    }

    private UsuarioException(String mensaje, int httpStatus) {
        super(mensaje, httpStatus);
    }

    public static UsuarioException nombreRequerido() {
        return new UsuarioException("nombres no puede estar vacío");
    }

    public static UsuarioException apellidoRequerido() {
        return new UsuarioException("apellidos no puede estar vacío");
    }

    public static UsuarioException correoInvalido() {
        return new UsuarioException("correo no tiene un formato válido");
    }

    public static UsuarioException contrasenaRequerida() {
        return new UsuarioException("contrasena no puede estar vacía");
    }

    public static UsuarioException rolRequerido() {
        return new UsuarioException("rol no puede ser nulo");
    }

    public static UsuarioException condominioRequerido() {
        return new UsuarioException("idCondominio no puede ser nulo");
    }

    public static UsuarioException nuevoCorreoRequerido() {
        return new UsuarioException("nuevo correo no puede estar vacío");
    }

    public static UsuarioException correoIgualAlActual() {
        return new UsuarioException("el nuevo correo debe ser diferente al actual");
    }

    public static UsuarioException sinCorreoPendiente() {
        return new UsuarioException("no hay correo pendiente por confirmar");
    }

    public static UsuarioException noEncontrado() {
        return new UsuarioException("usuario no encontrado", 404);
    }

    public static UsuarioException credencialesInvalidas() {
        return new UsuarioException("correo o contraseña incorrectos", 401);
    }

    public static UsuarioException noPermisoAsignarRol() {
        return new UsuarioException("no tienes permiso para asignar este rol");
    }

    public static UsuarioException correoYaRegistrado() {
        return new UsuarioException("el correo ya está registrado");
    }
}
