package com.condominios.sgc.domain.util;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import com.condominios.sgc.domain.auxiliar.DominioException;

public final class ValidacionUtil {

    private static final Pattern PATRON_CORREO_ELECTRONICO = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private ValidacionUtil() {}

    public static <T> T requerirNoNulo(T objeto, Supplier<DominioException> proveedor) {
        if (objeto == null) throw proveedor.get();
        return objeto;
    }

    public static String requerirNoVacio(String cadena, Supplier<DominioException> proveedor) {
        if (cadena == null || cadena.trim().isEmpty()) throw proveedor.get();
        return cadena;
    }

    public static int requerirPositivo(Integer valor, Supplier<DominioException> proveedor) {
        if (valor == null || valor <= 0) throw proveedor.get();
        return valor;
    }

    public static int requerirNoNegativo(Integer valor, Supplier<DominioException> proveedor) {
        if (valor == null || valor < 0) throw proveedor.get();
        return valor;
    }

    public static BigDecimal requerirPositivo(BigDecimal valor, Supplier<DominioException> proveedor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) throw proveedor.get();
        return valor;
    }

    public static BigDecimal requerirNoNegativo(BigDecimal valor, Supplier<DominioException> proveedor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) throw proveedor.get();
        return valor;
    }

    public static String requerirCorreoElectronicoValido(String correo, Supplier<DominioException> proveedor) {
        requerirNoVacio(correo, proveedor);
        if (!PATRON_CORREO_ELECTRONICO.matcher(correo).matches()) throw proveedor.get();
        return correo;
    }

    public static void requerirQue(boolean condicion, Supplier<DominioException> proveedor) {
        if (!condicion) throw proveedor.get();
    }

    public static <T, R> R idDe(T entidad, Function<T, R> extractor) {
        return entidad != null ? extractor.apply(entidad) : null;
    }
}
