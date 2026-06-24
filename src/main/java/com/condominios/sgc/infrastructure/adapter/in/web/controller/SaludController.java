package com.condominios.sgc.infrastructure.adapter.in.web.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaludController {

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> inicio() {
        return ResponseEntity.ok(Map.of("mensaje", "Bienvenido a la API del Sistema de Gestion de Condominios"));
    }

    @GetMapping("/api/health")
    public ResponseEntity<Map<String, String>> salud() {
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
