package com.condominios.sgc.domain.exception;

import com.condominios.sgc.domain.auxiliar.DominioException;

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
        return new UsuarioException("El correo no es valido", TipoError.BAD_REQUEST);
    }

    public static UsuarioException telefonoObligatorio() {
        return new UsuarioException("El telefono es obligatorio", TipoError.BAD_REQUEST);
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
        return new UsuarioException("El correo electrónico ya está en uso", TipoError.BAD_REQUEST);
    }

    public static UsuarioException noPermisoParaAsignarRol() {
        return new UsuarioException("No tienes permiso para asignar este rol", TipoError.BAD_REQUEST);
    }

    public static UsuarioException correoPendienteNoEncontrado() {
        return new UsuarioException("No se encontró una verificación de correo pendiente", TipoError.NOT_FOUND);
    }

    public static UsuarioException correoNoVerificado() {
        return new UsuarioException("El correo no ha sido verificado", TipoError.BAD_REQUEST);
    }
}
