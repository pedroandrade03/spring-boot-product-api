package com.pedro.andrade.product.application.dtos.security;

import com.pedro.andrade.product.domain.models.UserRole;

public record RegisterDTO(String email, String password, UserRole role) {
}
