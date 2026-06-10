package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.port.CondominioTreePort;
import com.condominios.sgc.infrastructure.persistence.entity.*;
import com.condominios.sgc.infrastructure.persistence.repository.*;
import com.condominios.sgc.web.dto.CondominioTreeResponse;
import com.condominios.sgc.web.dto.CondominioTreeResponse.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CondominioTreeAdapter implements CondominioTreePort {

    private final CondominioRepository condominioRepository;
    private final TorreRepository torreRepository;
    private final PisoRepository pisoRepository;
    private final ApartamentoRepository apartamentoRepository;
    private final InquilinoRepository inquilinoRepository;

    public CondominioTreeAdapter(CondominioRepository condominioRepository, TorreRepository torreRepository,
                                 PisoRepository pisoRepository, ApartamentoRepository apartamentoRepository,
                                 InquilinoRepository inquilinoRepository) {
        this.condominioRepository = condominioRepository;
        this.torreRepository = torreRepository;
        this.pisoRepository = pisoRepository;
        this.apartamentoRepository = apartamentoRepository;
        this.inquilinoRepository = inquilinoRepository;
    }

    @Override
    public CondominioTreeResponse obtenerArbolCompleto(Long condominioId) {

        CondominioEntity condominio = condominioRepository.findById(condominioId)
                .orElseThrow(() -> CondominioException.noEncontrado(condominioId));

        List<TorreNode> torresNodes = torreRepository.findByCondominioId(condominioId).stream().map(torre -> {

            List<PisoNode> pisosNodes = pisoRepository.findByTorreId(torre.getId()).stream().map(piso -> {

                List<ApartamentoNode> apartamentosNodes = apartamentoRepository.findByPisoId(piso.getId()).stream().map(apto -> {

                    List<InquilinoNode> inquilinosNodes = inquilinoRepository.findByApartamentoId(apto.getId()).stream().map(inq ->
                            new InquilinoNode(inq.getId(), inq.getNombres(), inq.getApellidos(), inq.getDni())
                    ).toList();

                    String propietarioId = apto.getPropietario() != null ? String.valueOf(apto.getPropietario().getId()) : null;

                    return new ApartamentoNode(apto.getId(), apto.getNumero(), apto.getDerechoEstacionamiento(), apto.getMetraje(), propietarioId, inquilinosNodes);
                }).toList();

                return new PisoNode(piso.getId(), piso.getNumero(), apartamentosNodes);
            }).toList();

            return new TorreNode(torre.getId(), torre.getNombre(), pisosNodes);
        }).toList();

        return new CondominioTreeResponse(
                condominio.getId(),
                condominio.getNombre(),
                condominio.getPais(),
                condominio.getCiudad(),
                condominio.getDireccion(),
                torresNodes
        );
    }
}