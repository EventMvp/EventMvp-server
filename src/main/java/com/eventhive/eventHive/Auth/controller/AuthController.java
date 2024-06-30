package com.eventhive.eventHive.Auth.controller;

import com.eventhive.eventHive.Auth.Service.AuthService;
import com.eventhive.eventHive.Auth.dto.LoginReqDto;
import com.eventhive.eventHive.Auth.dto.LoginRespDto;
import com.eventhive.eventHive.Auth.entity.UserAuth;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Log
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginReqDto loginReqDto){
        log.info("Handling req for: " + loginReqDto.getPassword());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginReqDto.getEmail(),
                        loginReqDto.getPassword()
                ));
        log.info(authentication.toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserAuth userDetails = (UserAuth) authentication.getPrincipal();
        log.info("Token requested for user: " + userDetails.getUsername() + " with roles: " + userDetails.getAuthorities().toArray()[0]);
        String token = authService.generateToken(authentication);

        LoginRespDto loginRespDto = new LoginRespDto();
        loginRespDto.setMessage("Login Success");
        loginRespDto.setToken(token);

        Cookie cookie = new Cookie("sid", token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie.getName() + "=" + cookie.getValue() + "; Path=/; HttpOnly");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(loginRespDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                if ("sid".equals(cookie.getName())){
                    //Clear cookies
                    cookie.setMaxAge(0);
                    cookie.setValue(null);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        return ResponseEntity.ok().body("Logout Successfully");
    }
}
