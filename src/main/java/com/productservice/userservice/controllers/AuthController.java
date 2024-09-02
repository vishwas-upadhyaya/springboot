package com.productservice.userservice.controllers;

import com.productservice.userservice.dtos.*;
import com.productservice.userservice.models.SessionStatus;
import com.productservice.userservice.services.AuthService;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/auth")
public class AuthController {
    private AuthService authService;

    AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request){
//        UserDto userDto = authService.login(request.getEmail(),request.getPassword());
        return authService.login(request.getEmail(),request.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto request){
        return authService.logout(request.getToken(),request.getUserId());
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpRequestDto request){
        UserDto userDto = authService.signup(request.getEmail(),request.getPassword());
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }


    public ResponseEntity<SessionStatus> validateToken(ValidateTokenRequestDto request) throws Exception {
        SessionStatus sessionStatus = authService.validate(request.getToken(),request.getUserId());
        return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<SessionStatus> validateTokenDup(@RequestHeader(value = "token") String token) throws Exception {
        SessionStatus sessionStatus = authService.validate(token,8L);
        return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
    }


}
