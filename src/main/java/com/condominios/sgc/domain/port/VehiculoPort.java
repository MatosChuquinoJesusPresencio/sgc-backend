package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.domain.filter.VehiculoFilter;
import com.condominios.sgc.domain.model.VehiculoModel;
import java.util.List;
import java.util.Optional;

public interface VehiculoPort {
    VehiculoModel guardar(VehiculoModel vehiculo);
    Optional<VehiculoModel> obtenerPorId(Long id);
    Optional<VehiculoModel> obtenerPorPlaca(String placa);
    List<VehiculoModel> obtenerPorPropietario(Long idPropietario);
    List<VehiculoModel> obtenerPorInquilino(Long idInquilino);
    List<VehiculoModel> obtenerPorCondominio(Long idCondominio);
    int contarPorCondominioYTipo(Long idCondominio, TipoVehiculo tipo);
    int contarPorPropietario(Long idPropietario);
    List<VehiculoModel> obtenerTodos();
    PaginacionResponse<VehiculoModel> obtenerTodos(PaginacionRequest request, VehiculoFilter filtro);
    void eliminarPorId(Long id);
}

