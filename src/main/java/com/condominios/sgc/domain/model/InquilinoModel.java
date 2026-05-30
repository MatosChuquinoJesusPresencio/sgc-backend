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
        this.id = id;
        asignarDatos(nombres, apellidos, dni, apartamentoId);
    }

    public InquilinoModel(
        String nombres, 
        String apellidos, 
        String dni, 
        Long apartamentoId
    ) {
        this(null, nombres, apellidos, dni, apartamentoId);
    }

    private void asignarDatos(String nombres, String apellidos, String dni, Long apartamentoId) {
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

    public void asignarApartamento(Long apartamentoId) {
        this.apartamentoId = requerirNoNulo(apartamentoId, InquilinoException::apartamentoIdObligatorio);
    }

    public void actualizar(String nombres, String apellidos, String dni, Long apartamentoId) {
        asignarDatos(nombres, apellidos, dni, apartamentoId);
    }
}
