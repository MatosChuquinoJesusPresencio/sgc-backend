package com.condominios.sgc.domain.exception;

public class CorreoException extends DominioException {

    private CorreoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    private CorreoException(String mensaje) {
        super(mensaje);
    }

    private CorreoException(String mensaje, int httpStatus) {
        super(mensaje, httpStatus);
    }

    public static CorreoException errorEnvio(String destinatario, Throwable causa) {
        return new CorreoException("error enviando correo a " + destinatario, causa);
    }

    public static CorreoException errorEnvio(String destinatario, String detalle) {
        return new CorreoException("error enviando correo a " + destinatario + ": " + detalle, 500);
    }

    public static CorreoException errorInterno(String detalle, Throwable causa) {
        return new CorreoException("error interno de correo: " + detalle, causa);
    }
}
