package com.condominios.sgc.application.service;

import org.springframework.stereotype.Service;

import com.condominios.sgc.application.dto.ActualizarApartamentoRequest;
import com.condominios.sgc.application.dto.CrearApartamentoRequest;
import com.condominios.sgc.application.usecase.ActualizarApartamentoUseCase;
import com.condominios.sgc.application.usecase.CrearApartamentoUseCase;
import com.condominios.sgc.application.usecase.EliminarApartamentoUseCase;
import com.condominios.sgc.application.usecase.ListarApartamentosPorPisoUseCase;
import com.condominios.sgc.application.usecase.ObtenerApartamentoUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.ApartamentoModel;

@Service
public class ApartamentoService {

    private final CrearApartamentoUseCase crearApartamentoUseCase;
    private final ActualizarApartamentoUseCase actualizarApartamentoUseCase;
    private final ObtenerApartamentoUseCase obtenerApartamentoUseCase;
    private final EliminarApartamentoUseCase eliminarApartamentoUseCase;
    private final ListarApartamentosPorPisoUseCase listarApartamentosPorPisoUseCase;

    public ApartamentoService(
            CrearApartamentoUseCase crearApartamentoUseCase,
            ActualizarApartamentoUseCase actualizarApartamentoUseCase,
            ObtenerApartamentoUseCase obtenerApartamentoUseCase,
            EliminarApartamentoUseCase eliminarApartamentoUseCase,
            ListarApartamentosPorPisoUseCase listarApartamentosPorPisoUseCase) {
        this.crearApartamentoUseCase = crearApartamentoUseCase;
        this.actualizarApartamentoUseCase = actualizarApartamentoUseCase;
        this.obtenerApartamentoUseCase = obtenerApartamentoUseCase;
        this.eliminarApartamentoUseCase = eliminarApartamentoUseCase;
        this.listarApartamentosPorPisoUseCase = listarApartamentosPorPisoUseCase;
    }

    public ApartamentoModel crear(CrearApartamentoRequest request) {
        return crearApartamentoUseCase.ejecutar(request);
    }

    public ApartamentoModel actualizar(Long id, ActualizarApartamentoRequest request) {
        return actualizarApartamentoUseCase.ejecutar(id, request);
    }

    public ApartamentoModel obtener(Long id) {
        return obtenerApartamentoUseCase.ejecutar(id);
    }

    public void eliminar(Long id) {
        eliminarApartamentoUseCase.ejecutar(id);
    }

    public PaginacionResponse<ApartamentoModel> listarPorPiso(Long pisoId, PaginacionRequest request) {
        return listarApartamentosPorPisoUseCase.ejecutar(pisoId, request);
    }
}
