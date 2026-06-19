package com.condominios.sgc.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.condominios.sgc.domain.shared.exception.ApartamentoException;
import com.condominios.sgc.domain.shared.exception.PisoException;
import static com.condominios.sgc.domain.util.ValidacionUtil.*;

public class PisoModel {
    private Long id;
    private Integer numero;
    private List<ApartamentoModel> apartamentos;

    public PisoModel(Long id, Integer numero, List<ApartamentoModel> apartamentos) {
        this.id = id;
        this.numero = numero;
        this.apartamentos = new ArrayList<>(apartamentos);
    }

    public PisoModel(Integer numero) {
        this.id = null;
        this.numero = positivo(numero, PisoException::numeroRequerido);
        this.apartamentos = new ArrayList<>();
    }

    public Long getId() { return id; }
    public Integer getNumero() { return numero; }

    public List<ApartamentoModel> getApartamentos() {
        return Collections.unmodifiableList(apartamentos);
    }

    public void actualizarNumero(Integer numero) {
        this.numero = positivo(numero, PisoException::numeroRequerido);
    }

    public ApartamentoModel agregarApartamento(Integer numero, BigDecimal metraje) {
        var apartamento = new ApartamentoModel(numero, metraje);
        apartamentos.add(apartamento);
        return apartamento;
    }

    public ApartamentoModel buscarApartamentoPorNumero(Integer numero) {
        return apartamentos.stream()
            .filter(a -> a.getNumero().equals(numero))
            .findFirst()
            .orElseThrow(ApartamentoException::noEncontrado);
    }
}
