package com.condominios.sgc.infrastructure.util;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public final class PaginacionUtil {

    private PaginacionUtil() {}

    public static Pageable toPageable(PaginacionRequest request) {
        if (request == null) {
            return PageRequest.of(0, 10, Sort.unsorted());
        }
        String campo = request.ordenarPor();
        String direccion = request.direccion();
        Sort sort = Sort.unsorted();
        if (campo != null && !campo.isBlank()) {
            sort = "desc".equalsIgnoreCase(direccion)
                ? Sort.by(campo).descending()
                : Sort.by(campo).ascending();
        }
        return PageRequest.of(
            request.pagina() >= 0 ? request.pagina() : 0,
            request.tamanio() > 0 ? request.tamanio() : 10,
            sort
        );
    }

    public static <T> PaginacionResponse<T> toPaginacionResponse(Page<?> page, List<T> content) {
        return PaginacionResponse.de(
            content,
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages()
        );
    }
}
