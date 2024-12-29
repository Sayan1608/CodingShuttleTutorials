package com.codingshuttle.securityapp.services;

import com.codingshuttle.securityapp.entities.User;
import com.codingshuttle.securityapp.exceptions.ResourceNotFoundException;
import com.codingshuttle.securityapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()->new ResourceNotFoundException("User not found with username : " + username));
    }

    public User getUsrByEmail(String email)  {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id : " + userId));
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
