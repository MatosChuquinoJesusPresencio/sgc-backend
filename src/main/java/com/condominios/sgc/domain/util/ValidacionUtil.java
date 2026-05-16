package com.condominios.sgc.domain.util;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.Supplier;

import com.condominios.sgc.domain.exception.DominioException;

public final class ValidacionUtil {

    private ValidacionUtil() {}

    public static <T> T requerirNoNulo(T obj, Supplier<DominioException> supplier) {
        if (obj == null) throw supplier.get();
        return obj;
    }

    public static String requerirNoVacio(String str, Supplier<DominioException> supplier) {
        if (str == null || str.trim().isEmpty()) throw supplier.get();
        return str;
    }

    public static int requerirPositivo(Integer value, Supplier<DominioException> supplier) {
        if (value == null || value <= 0) throw supplier.get();
        return value;
    }

    public static int requerirNoNegativo(Integer value, Supplier<DominioException> supplier) {
        if (value == null || value < 0) throw supplier.get();
        return value;
    }

    public static BigDecimal requerirPositivo(BigDecimal value, Supplier<DominioException> supplier) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) throw supplier.get();
        return value;
    }

    public static BigDecimal requerirNoNegativo(BigDecimal value, Supplier<DominioException> supplier) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) throw supplier.get();
        return value;
    }

    public static String requerirEmailValido(String email, Supplier<DominioException> supplier) {
        requerirNoVacio(email, supplier);
        if (!email.contains("@")) throw supplier.get();
        return email;
    }

    public static void requerirQue(boolean condition, Supplier<DominioException> supplier) {
        if (!condition) throw supplier.get();
    }

    public static <T, R> R idDe(T entity, Function<T, R> extractor) {
        return entity != null ? extractor.apply(entity) : null;
    }
}
