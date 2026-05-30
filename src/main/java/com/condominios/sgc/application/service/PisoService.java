package com.condominios.sgc.application.service;

import org.springframework.stereotype.Service;

import com.condominios.sgc.application.dto.ActualizarPisoRequest;
import com.condominios.sgc.application.dto.CrearPisoRequest;
import com.condominios.sgc.application.usecase.ActualizarPisoUseCase;
import com.condominios.sgc.application.usecase.CrearPisoUseCase;
import com.condominios.sgc.application.usecase.EliminarPisoUseCase;
import com.condominios.sgc.application.usecase.ListarPisosPorTorreUseCase;
import com.condominios.sgc.application.usecase.ObtenerPisoUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.PisoModel;

@Service
public class PisoService {

    private final CrearPisoUseCase crearPisoUseCase;
    private final ActualizarPisoUseCase actualizarPisoUseCase;
    private final ObtenerPisoUseCase obtenerPisoUseCase;
    private final EliminarPisoUseCase eliminarPisoUseCase;
    private final ListarPisosPorTorreUseCase listarPisosPorTorreUseCase;

    public PisoService(
            CrearPisoUseCase crearPisoUseCase,
            ActualizarPisoUseCase actualizarPisoUseCase,
            ObtenerPisoUseCase obtenerPisoUseCase,
            EliminarPisoUseCase eliminarPisoUseCase,
            ListarPisosPorTorreUseCase listarPisosPorTorreUseCase) {
        this.crearPisoUseCase = crearPisoUseCase;
        this.actualizarPisoUseCase = actualizarPisoUseCase;
        this.obtenerPisoUseCase = obtenerPisoUseCase;
        this.eliminarPisoUseCase = eliminarPisoUseCase;
        this.listarPisosPorTorreUseCase = listarPisosPorTorreUseCase;
    }

    public PisoModel crear(CrearPisoRequest request) {
        return crearPisoUseCase.ejecutar(request);
    }

    public PisoModel actualizar(Long id, ActualizarPisoRequest request) {
        return actualizarPisoUseCase.ejecutar(id, request);
    }

    public PisoModel obtener(Long id) {
        return obtenerPisoUseCase.ejecutar(id);
    }

    public void eliminar(Long id) {
        eliminarPisoUseCase.ejecutar(id);
    }

    public PaginacionResponse<PisoModel> listarPorTorre(Long torreId, PaginacionRequest request) {
        return listarPisosPorTorreUseCase.ejecutar(torreId, request);
    }
}
