package com.condominios.sgc.infrastructure.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

public class SupabaseClient {

    private final RestTemplate restTemplate;
    private final String supabaseUrl;
    private final String anonKey;
    private final String serviceRoleKey;

    public SupabaseClient(RestTemplate restTemplate, String supabaseUrl, String anonKey, String serviceRoleKey) {
        this.restTemplate = restTemplate;
        this.supabaseUrl = supabaseUrl;
        this.anonKey = anonKey;
        this.serviceRoleKey = serviceRoleKey;
    }

    public Map<String, Object> iniciarSesion(String email, String password) {
        var headers = cabecerasConClaveAnonima();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var body = Map.of("email", email, "password", password);
        var response = restTemplate.exchange(
            supabaseUrl + "/auth/v1/token?grant_type=password",
            HttpMethod.POST,
            new HttpEntity<>(body, headers),
            new ParameterizedTypeReference<Map<String, Object>>() {});
        return Objects.requireNonNull(response.getBody(),
            "Respuesta nula del servidor de autenticación");
    }

    public void cerrarSesion(String accessToken) {
        var headers = cabecerasConClaveAnonima();
        headers.setBearerAuth(accessToken);
        restTemplate.postForEntity(
            supabaseUrl + "/auth/v1/logout",
            new HttpEntity<>(headers),
            Void.class);
    }

    public Map<String, Object> crearUsuario(String email, String password, String rol) {
        var headers = headersConServiceRole();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var body = new java.util.LinkedHashMap<String, Object>();
        body.put("email", email);
        body.put("password", password);
        body.put("email_confirm", true);
        body.put("app_metadata", Map.of("rol", rol));

        var response = restTemplate.exchange(
            supabaseUrl + "/auth/v1/admin/users",
            HttpMethod.POST,
            new HttpEntity<>(body, headers),
            new ParameterizedTypeReference<Map<String, Object>>() {});
        return Objects.requireNonNull(response.getBody(),
            "Respuesta nula del servidor de autenticación");
    }

    public void actualizarUsuario(String token, Map<String, Object> attributes) {
        var headers = cabecerasConClaveAnonima();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.exchange(
            supabaseUrl + "/auth/v1/user",
            HttpMethod.PUT,
            new HttpEntity<>(attributes, headers),
            new ParameterizedTypeReference<Map<String, Object>>() {});
    }

    public Map<String, Object> refrescarToken(String refreshToken) {
        var headers = cabecerasConClaveAnonima();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var body = Map.of("refresh_token", refreshToken);
        var response = restTemplate.exchange(
            supabaseUrl + "/auth/v1/token?grant_type=refresh_token",
            HttpMethod.POST,
            new HttpEntity<>(body, headers),
            new ParameterizedTypeReference<Map<String, Object>>() {});
        return Objects.requireNonNull(response.getBody(),
            "Respuesta nula del servidor de autenticación");
    }

    public void actualizarEmailAdmin(String userId, String nuevoEmail) {
        actualizarUsuarioAdmin(userId, Map.of("email", nuevoEmail));
    }

    public void actualizarPasswordAdmin(String userId, String nuevaPassword) {
        actualizarUsuarioAdmin(userId, Map.of("password", nuevaPassword));
    }

    private void actualizarUsuarioAdmin(String userId, Map<String, Object> attributes) {
        var headers = headersConServiceRole();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.exchange(
            supabaseUrl + "/auth/v1/admin/users/" + userId,
            HttpMethod.PUT,
            new HttpEntity<>(attributes, headers),
            new ParameterizedTypeReference<Map<String, Object>>() {});
    }

    public void eliminarUsuario(String userId) {
        var headers = headersConServiceRole();
        restTemplate.exchange(
            supabaseUrl + "/auth/v1/admin/users/" + userId,
            HttpMethod.DELETE,
            new HttpEntity<>(headers),
            Void.class);
    }

    private HttpHeaders cabecerasConClaveAnonima() {
        var headers = new HttpHeaders();
        headers.set("apikey", anonKey);
        return headers;
    }

    private HttpHeaders headersConServiceRole() {
        var headers = new HttpHeaders();
        headers.set("apikey", serviceRoleKey);
        headers.setBearerAuth(serviceRoleKey);
        return headers;
    }
}
