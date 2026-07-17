package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.ActualizarStatusAssetCommand;
import com.condominios.sgc.application.dto.command.AsignarParkingCommand;
import com.condominios.sgc.application.dto.command.AsignarPropietarioCommand;
import com.condominios.sgc.application.dto.command.CrearAssetCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminAssetResult;
import com.condominios.sgc.application.dto.result.PaginaResult;

public interface GestionarAdminActivosUseCase {
    PaginaResult<AdminAssetResult> listar(Long condominioIdOverride, String type, PaginaQuery pagina);
    AdminAssetResult crear(Long condominioIdOverride, CrearAssetCommand cmd);
    AdminAssetResult actualizarStatus(Long condominioIdOverride, Long id, ActualizarStatusAssetCommand cmd);
    AdminAssetResult asignarApartamento(Long condominioIdOverride, Long id, AsignarParkingCommand cmd);
    AdminAssetResult asignarPropietario(Long condominioIdOverride, Long id, AsignarPropietarioCommand cmd);
    void eliminar(Long condominioIdOverride, Long id, String type);
    void desasignarVehiculo(Long condominioIdOverride, Long vehiculoId);
}
