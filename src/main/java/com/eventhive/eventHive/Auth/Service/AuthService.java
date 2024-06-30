package com.eventhive.eventHive.Auth.Service;

import org.springframework.security.core.Authentication;

public interface AuthService {
    String generateToken(Authentication authentication);
}
