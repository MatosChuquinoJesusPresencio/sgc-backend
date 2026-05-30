package com.condominios.sgc.application.service;

import org.springframework.stereotype.Service;

import com.condominios.sgc.application.dto.ActualizarTorreRequest;
import com.condominios.sgc.application.dto.CrearTorreRequest;
import com.condominios.sgc.application.usecase.ActualizarTorreUseCase;
import com.condominios.sgc.application.usecase.CrearTorreUseCase;
import com.condominios.sgc.application.usecase.EliminarTorreUseCase;
import com.condominios.sgc.application.usecase.ListarTorresPorCondominioUseCase;
import com.condominios.sgc.application.usecase.ObtenerTorreUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.TorreModel;

@Service
public class TorreService {

    private final CrearTorreUseCase crearTorreUseCase;
    private final ActualizarTorreUseCase actualizarTorreUseCase;
    private final ObtenerTorreUseCase obtenerTorreUseCase;
    private final EliminarTorreUseCase eliminarTorreUseCase;
    private final ListarTorresPorCondominioUseCase listarTorresPorCondominioUseCase;

    public TorreService(
            CrearTorreUseCase crearTorreUseCase,
            ActualizarTorreUseCase actualizarTorreUseCase,
            ObtenerTorreUseCase obtenerTorreUseCase,
            EliminarTorreUseCase eliminarTorreUseCase,
            ListarTorresPorCondominioUseCase listarTorresPorCondominioUseCase) {
        this.crearTorreUseCase = crearTorreUseCase;
        this.actualizarTorreUseCase = actualizarTorreUseCase;
        this.obtenerTorreUseCase = obtenerTorreUseCase;
        this.eliminarTorreUseCase = eliminarTorreUseCase;
        this.listarTorresPorCondominioUseCase = listarTorresPorCondominioUseCase;
    }

    public TorreModel crear(CrearTorreRequest request) {
        return crearTorreUseCase.ejecutar(request);
    }

    public TorreModel actualizar(Long id, ActualizarTorreRequest request) {
        return actualizarTorreUseCase.ejecutar(id, request);
    }

    public TorreModel obtener(Long id) {
        return obtenerTorreUseCase.ejecutar(id);
    }

    public void eliminar(Long id) {
        eliminarTorreUseCase.ejecutar(id);
    }

    public PaginacionResponse<TorreModel> listarPorCondominio(Long condominioId, PaginacionRequest request) {
        return listarTorresPorCondominioUseCase.ejecutar(condominioId, request);
    }
}
