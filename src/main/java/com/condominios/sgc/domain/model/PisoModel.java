package com.condominios.sgc.domain.model;

import com.condominios.sgc.domain.exception.PisoException;
import static com.condominios.sgc.domain.util.ValidacionUtil.*;

public class PisoModel {
    private Long id;
    private Integer numero;
    private Long idTorre;

    public PisoModel(Long id, Integer numero, Long idTorre) {
        this.id = id;
        this.numero = numero;
        this.idTorre = idTorre;
    }

    public PisoModel(Integer numero, Long idTorre) {
        this.id = null;
        this.numero = positivo(numero, PisoException::numeroRequerido);
        this.idTorre = noNulo(idTorre, PisoException::torreRequerida);
    }

    public Long getId() { return id; }
    public Integer getNumero() { return numero; }
    public Long getIdTorre() { return idTorre; }

    public void actualizarNumero(Integer numero) {
        this.numero = positivo(numero, PisoException::numeroRequerido);
    }
}

