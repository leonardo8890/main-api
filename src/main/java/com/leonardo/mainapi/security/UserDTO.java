package com.leonardo.mainapi.security;

public record UserDTO(String login, String password, Role role) {
}
