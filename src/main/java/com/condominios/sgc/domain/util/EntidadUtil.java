package com.condominios.sgc.domain.util;

import java.util.function.Function;

public final class EntidadUtil {

    private EntidadUtil() {}

    public static <T, R> R idDe(T entidad, Function<T, R> extractor) {
        return entidad != null ? extractor.apply(entidad) : null;
    }
}
