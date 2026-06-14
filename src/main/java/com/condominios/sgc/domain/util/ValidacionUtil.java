package com.condominios.sgc.domain.util;

import java.util.function.Supplier;

import com.condominios.sgc.domain.exception.DominioException;

public final class ValidacionUtil {
    private ValidacionUtil() {}

    public static String requerido(String valor, Supplier<DominioException> ex) {
        if (valor == null || valor.isBlank())
            throw ex.get();
        return valor;
    }

    public static <T> T noNulo(T valor, Supplier<DominioException> ex) {
        if (valor == null)
            throw ex.get();
        return valor;
    }

    public static Integer positivo(Integer valor, Supplier<DominioException> ex) {
        if (valor == null || valor <= 0)
            throw ex.get();
        return valor;
    }

    public static <T extends Number> T positivo(T valor, Supplier<DominioException> ex) {
        if (valor == null || valor.longValue() <= 0)
            throw ex.get();
        return valor;
    }

    public static String correoValido(String valor, Supplier<DominioException> ex) {
        if (valor == null || valor.isBlank() || !valor.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))
            throw ex.get();
        return valor;
    }

    public static <T> T distinto(T actual, T nuevo, Supplier<DominioException> ex) {
        if (actual != null && actual.equals(nuevo))
            throw ex.get();
        return nuevo;
    }
}
