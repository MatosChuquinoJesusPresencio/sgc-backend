package com.condominios.sgc.application.service;

import org.springframework.stereotype.Service;

import com.condominios.sgc.application.dto.ActualizarEstacionamientoRequest;
import com.condominios.sgc.application.dto.CrearEstacionamientoRequest;
import com.condominios.sgc.application.usecase.ActualizarEstacionamientoUseCase;
import com.condominios.sgc.application.usecase.ConfigurarEstacionamientoUseCase;
import com.condominios.sgc.application.usecase.CrearEstacionamientoUseCase;
import com.condominios.sgc.application.usecase.EliminarEstacionamientoUseCase;
import com.condominios.sgc.application.usecase.ListarEstacionamientosPorApartamentoUseCase;
import com.condominios.sgc.application.usecase.ListarEstacionamientosPorCondominioUseCase;
import com.condominios.sgc.application.usecase.ObtenerEstacionamientoUseCase;
import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.EstacionamientoModel;

@Service
public class EstacionamientoService {

    private final CrearEstacionamientoUseCase crearEstacionamientoUseCase;
    private final ActualizarEstacionamientoUseCase actualizarEstacionamientoUseCase;
    private final ConfigurarEstacionamientoUseCase configurarEstacionamientoUseCase;
    private final ObtenerEstacionamientoUseCase obtenerEstacionamientoUseCase;
    private final EliminarEstacionamientoUseCase eliminarEstacionamientoUseCase;
    private final ListarEstacionamientosPorApartamentoUseCase listarEstacionamientosPorApartamentoUseCase;
    private final ListarEstacionamientosPorCondominioUseCase listarEstacionamientosPorCondominioUseCase;

    public EstacionamientoService(
            CrearEstacionamientoUseCase crearEstacionamientoUseCase,
            ActualizarEstacionamientoUseCase actualizarEstacionamientoUseCase,
            ConfigurarEstacionamientoUseCase configurarEstacionamientoUseCase,
            ObtenerEstacionamientoUseCase obtenerEstacionamientoUseCase,
            EliminarEstacionamientoUseCase eliminarEstacionamientoUseCase,
            ListarEstacionamientosPorApartamentoUseCase listarEstacionamientosPorApartamentoUseCase,
            ListarEstacionamientosPorCondominioUseCase listarEstacionamientosPorCondominioUseCase) {
        this.crearEstacionamientoUseCase = crearEstacionamientoUseCase;
        this.actualizarEstacionamientoUseCase = actualizarEstacionamientoUseCase;
        this.configurarEstacionamientoUseCase = configurarEstacionamientoUseCase;
        this.obtenerEstacionamientoUseCase = obtenerEstacionamientoUseCase;
        this.eliminarEstacionamientoUseCase = eliminarEstacionamientoUseCase;
        this.listarEstacionamientosPorApartamentoUseCase = listarEstacionamientosPorApartamentoUseCase;
        this.listarEstacionamientosPorCondominioUseCase = listarEstacionamientosPorCondominioUseCase;
    }

    public EstacionamientoModel crear(CrearEstacionamientoRequest request) {
        return crearEstacionamientoUseCase.ejecutar(request);
    }

    public EstacionamientoModel actualizar(Long id, ActualizarEstacionamientoRequest request) {
        return actualizarEstacionamientoUseCase.ejecutar(id, request);
    }

    public EstacionamientoModel configurar(Long id, TipoVehiculo tipoVehiculo, Integer capacidadMaxima) {
        return configurarEstacionamientoUseCase.ejecutar(id, tipoVehiculo, capacidadMaxima);
    }

    public EstacionamientoModel obtener(Long id) {
        return obtenerEstacionamientoUseCase.ejecutar(id);
    }

    public void eliminar(Long id) {
        eliminarEstacionamientoUseCase.ejecutar(id);
    }

    public PaginacionResponse<EstacionamientoModel> listarPorApartamento(Long apartamentoId, PaginacionRequest request) {
        return listarEstacionamientosPorApartamentoUseCase.ejecutar(apartamentoId, request);
    }

    public PaginacionResponse<EstacionamientoModel> listarPorCondominio(Long condominioId, PaginacionRequest request) {
        return listarEstacionamientosPorCondominioUseCase.ejecutar(condominioId, request);
    }
}
