package com.codingshuttle.securityapp.controllers;

import com.codingshuttle.securityapp.advices.ApiResponse;
import com.codingshuttle.securityapp.dtos.*;
import com.codingshuttle.securityapp.services.AuthService;
import com.codingshuttle.securityapp.services.SubscriptionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @Value("${deploy.env}")
    private String deployEnv;
    private final SubscriptionService subscriptionService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
        UserDto userDto = authService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletResponse servletResponse) {
        LoginResponseDto loginResponseDto = authService.login(loginDto);
        Cookie cookie = new Cookie("refreshToken",loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        servletResponse.addCookie(cookie);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<LoginResponseDto> refreshToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh Token not found inside the Cookies"));

        LoginResponseDto loginResponseDto = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(loginResponseDto);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh Token not found inside the Cookies"));

        String msg = authService.logout(refreshToken);
        ApiResponse<String> apiResponse = new ApiResponse<>(msg);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping(path = "/subscribe")
    public ResponseEntity<SubscriptionDto> subscribe(
            @RequestBody @Valid SubscriptionDto subscriptionDto, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh Token not found inside the Cookies"));
        SubscriptionDto subscription =
                subscriptionService.addNewSubscription(refreshToken, subscriptionDto.getSessionCount());

        return new ResponseEntity<>(subscription, HttpStatus.CREATED);
    }
}
