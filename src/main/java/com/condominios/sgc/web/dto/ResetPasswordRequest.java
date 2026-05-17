package com.condominios.sgc.web.dto;

public record ResetPasswordRequest(
    String token,
    String password
) {}
