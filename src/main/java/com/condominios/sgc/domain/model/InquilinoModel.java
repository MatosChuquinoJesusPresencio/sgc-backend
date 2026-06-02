package com.condominios.sgc.domain.model;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

import com.condominios.sgc.domain.exception.InquilinoException;

public class InquilinoModel {
    private Long id;
    private String nombres;
    private String apellidos;
    private String dni;
    private Long apartamentoId;

    public InquilinoModel(
        Long id, 
        String nombres, 
        String apellidos, 
        String dni, 
        Long apartamentoId
    ) {
        this(nombres, apellidos, dni, apartamentoId);
        this.id = id;
    }

    public InquilinoModel(
        String nombres, 
        String apellidos, 
        String dni, 
        Long apartamentoId
    ) {
        validarYAsignarDatos(nombres, apellidos, dni, apartamentoId);
    }

    private void validarYAsignarDatos(String nombres, String apellidos, String dni, Long apartamentoId) {
        this.nombres = requerirNoVacio(nombres, InquilinoException::nombresObligatorios);
        this.apellidos = requerirNoVacio(apellidos, InquilinoException::apellidosObligatorios);
        this.dni = requerirNoVacio(dni, InquilinoException::dniObligatorio);
        this.apartamentoId = requerirNoNulo(apartamentoId, InquilinoException::apartamentoIdObligatorio);
    }

    public Long getId() { return id; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getDni() { return dni; }
    public Long getApartamentoId() { return apartamentoId; }

    public void actualizarApartamento(Long apartamentoId) {
        this.apartamentoId = requerirNoNulo(apartamentoId, InquilinoException::apartamentoIdObligatorio);
    }

    public void actualizarDatos(String nombres, String apellidos) {
        validarYAsignarDatos(nombres, apellidos, this.dni, this.apartamentoId);
    }
}
