package com.condominios.sgc.application.service;

import org.springframework.stereotype.Service;

import com.condominios.sgc.application.dto.ActualizarInquilinoRequest;
import com.condominios.sgc.application.dto.CrearInquilinoRequest;
import com.condominios.sgc.application.usecase.ActualizarInquilinoUseCase;
import com.condominios.sgc.application.usecase.CrearInquilinoUseCase;
import com.condominios.sgc.application.usecase.EliminarInquilinoUseCase;
import com.condominios.sgc.application.usecase.ListarInquilinosPorApartamentoUseCase;
import com.condominios.sgc.application.usecase.ObtenerInquilinoUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.InquilinoModel;

@Service
public class InquilinoService {

    private final CrearInquilinoUseCase crearInquilinoUseCase;
    private final ActualizarInquilinoUseCase actualizarInquilinoUseCase;
    private final ObtenerInquilinoUseCase obtenerInquilinoUseCase;
    private final EliminarInquilinoUseCase eliminarInquilinoUseCase;
    private final ListarInquilinosPorApartamentoUseCase listarInquilinosPorApartamentoUseCase;

    public InquilinoService(
            CrearInquilinoUseCase crearInquilinoUseCase,
            ActualizarInquilinoUseCase actualizarInquilinoUseCase,
            ObtenerInquilinoUseCase obtenerInquilinoUseCase,
            EliminarInquilinoUseCase eliminarInquilinoUseCase,
            ListarInquilinosPorApartamentoUseCase listarInquilinosPorApartamentoUseCase) {
        this.crearInquilinoUseCase = crearInquilinoUseCase;
        this.actualizarInquilinoUseCase = actualizarInquilinoUseCase;
        this.obtenerInquilinoUseCase = obtenerInquilinoUseCase;
        this.eliminarInquilinoUseCase = eliminarInquilinoUseCase;
        this.listarInquilinosPorApartamentoUseCase = listarInquilinosPorApartamentoUseCase;
    }

    public InquilinoModel crear(CrearInquilinoRequest request) {
        return crearInquilinoUseCase.ejecutar(request);
    }

    public InquilinoModel actualizar(Long id, ActualizarInquilinoRequest request) {
        return actualizarInquilinoUseCase.ejecutar(id, request);
    }

    public InquilinoModel obtener(Long id) {
        return obtenerInquilinoUseCase.ejecutar(id);
    }

    public void eliminar(Long id) {
        eliminarInquilinoUseCase.ejecutar(id);
    }

    public PaginacionResponse<InquilinoModel> listarPorApartamento(Long apartamentoId, PaginacionRequest request) {
        return listarInquilinosPorApartamentoUseCase.ejecutar(apartamentoId, request);
    }
}
