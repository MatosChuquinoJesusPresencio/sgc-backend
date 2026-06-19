package com.condominios.sgc.application.port.out.service;

public interface HashPort {
    String hash(String contrasena);
    boolean verificar(String contrasena, String hash);
}
