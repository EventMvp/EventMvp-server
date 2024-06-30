package com.eventhive.eventHive.Auth.Service.Impl;

import com.eventhive.eventHive.Auth.Service.AuthService;
import com.eventhive.eventHive.Users.Repository.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtEncoder jwtEncoder;
    private final UsersRepository usersRepository;

    public AuthServiceImpl(JwtEncoder jwtEncoder, UsersRepository usersRepository) {
        this.jwtEncoder = jwtEncoder;
        this.usersRepository = usersRepository;
    }

    @Override
    public String generateToken(Authentication authentication) {
        Instant now =Instant.now();
        String scope = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims =JwtClaimsSet.builder()
                .issuer("EventHive")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .claim("userId", usersRepository.findByEmail(authentication.getName()).get().getId())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
