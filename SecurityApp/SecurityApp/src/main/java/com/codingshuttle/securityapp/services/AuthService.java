package com.codingshuttle.securityapp.services;

import com.codingshuttle.securityapp.dtos.LoginDto;
import com.codingshuttle.securityapp.dtos.SignUpDto;
import com.codingshuttle.securityapp.dtos.UserDto;
import com.codingshuttle.securityapp.entities.User;
import com.codingshuttle.securityapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserDto signUp(SignUpDto signUpDto) {
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User already exists with email : " + signUpDto.getEmail());
        }
        User toBeCreatedUser = modelMapper.map(signUpDto, User.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));
        User savedUser = userRepository.save(toBeCreatedUser);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public String login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        User user = (User) authenticate.getPrincipal();
        String token = jwtService.generateToken(user);
        return token;
    }
}
