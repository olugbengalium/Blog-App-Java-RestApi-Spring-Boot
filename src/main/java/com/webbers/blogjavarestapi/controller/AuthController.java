package com.webbers.blogjavarestapi.controller;

import com.webbers.blogjavarestapi.entity.User;
import com.webbers.blogjavarestapi.payload.JwtAuthResponse;
import com.webbers.blogjavarestapi.payload.LoginDto;
import com.webbers.blogjavarestapi.payload.RegistrationDto;
import com.webbers.blogjavarestapi.repository.UserRepository;
import com.webbers.blogjavarestapi.service.AuthService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
//    @PostMapping(value = {"login", "signin"})
//    public ResponseEntity<JwtAuthResponse> authenticateNewUser(@RequestBody LoginDto loginDto){
//        String token = authService.login(loginDto);
//        log.info(token);
//        JwtAuthResponse authResponse = new JwtAuthResponse();
//        authResponse.setAccessToken(token);
//        return ResponseEntity.ok(authResponse);
//    }
    @PostMapping(value = {"login", "signin"})
    public String authenticateNewUser(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
//        JwtAuthResponse authResponse = new JwtAuthResponse();
//        authResponse.setAccessToken(token);
//        return ResponseEntity.ok("Already found");
        return token;
    }


    @PostMapping(value = {"signup","register"})
    public  ResponseEntity<String> registerNewUser(@RequestBody RegistrationDto registrationDto){
        String response = authService.register(registrationDto);
        return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
