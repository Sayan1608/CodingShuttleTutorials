package com.codingshuttle.securityapp.controllers;

import com.codingshuttle.securityapp.advices.ApiResponse;
import com.codingshuttle.securityapp.dtos.LoginDto;
import com.codingshuttle.securityapp.dtos.SignUpDto;
import com.codingshuttle.securityapp.dtos.UserDto;
import com.codingshuttle.securityapp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
        UserDto userDto = authService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        ApiResponse<String> response = new ApiResponse<>(token);
        return ResponseEntity.ok(response);
    }
}
