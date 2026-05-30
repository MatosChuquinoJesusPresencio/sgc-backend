package com.condominios.sgc.domain.exception;

public final class UsuarioException extends DominioException {
    private UsuarioException(String mensaje, TipoError tipo) {
        super(mensaje, tipo);
    }

    public static UsuarioException nombresObligatorios() {
        return new UsuarioException("Los nombres son obligatorios", TipoError.BAD_REQUEST);
    }

    public static UsuarioException apellidosObligatorios() {
        return new UsuarioException("Los apellidos son obligatorios", TipoError.BAD_REQUEST);
    }

    public static UsuarioException correoObligatorio() {
        return new UsuarioException("El correo es obligatorio", TipoError.BAD_REQUEST);
    }

    public static UsuarioException correoInvalido() {
        return new UsuarioException("El correo no es válido", TipoError.BAD_REQUEST);
    }

    public static UsuarioException telefonoObligatorio() {
        return new UsuarioException("El teléfono es obligatorio", TipoError.BAD_REQUEST);
    }

    public static UsuarioException rolObligatorio() {
        return new UsuarioException("El rol es obligatorio", TipoError.BAD_REQUEST);
    }

    public static UsuarioException activoObligatorio() {
        return new UsuarioException("El estado activo es obligatorio", TipoError.BAD_REQUEST);
    }

    public static UsuarioException condominioIdObligatorio() {
        return new UsuarioException("El id del condominio es obligatorio", TipoError.BAD_REQUEST);
    }
    
    public static UsuarioException contrasenaObligatoria() {
        return new UsuarioException("La contraseña es obligatoria", TipoError.BAD_REQUEST);
    }

    public static UsuarioException noEncontrado() {
        return new UsuarioException("El usuario solicitado no existe", TipoError.NOT_FOUND);
    }

    public static UsuarioException correoYaEnUso() {
        return new UsuarioException("El correo ya está en uso", TipoError.BAD_REQUEST);
    }

    public static UsuarioException idObligatorio() {
        return new UsuarioException("El id del usuario es obligatorio", TipoError.BAD_REQUEST);
    }

    public static UsuarioException correoPendienteNoEncontrado() {
        return new UsuarioException("No hay un cambio de correo pendiente de verificación", TipoError.BAD_REQUEST);
    }

    public static UsuarioException correoNoVerificado() {
        return new UsuarioException("El correo aún no ha sido confirmado", TipoError.BAD_REQUEST);
    }

    public static UsuarioException noPermisoParaAsignarRol() {
        return new UsuarioException("No tienes permiso para asignar ese rol", TipoError.FORBIDDEN);
    }

}
