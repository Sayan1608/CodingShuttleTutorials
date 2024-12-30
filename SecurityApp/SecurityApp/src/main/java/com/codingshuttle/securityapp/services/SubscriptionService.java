package com.codingshuttle.securityapp.services;

import com.codingshuttle.securityapp.dtos.SubscriptionDto;
import com.codingshuttle.securityapp.entities.Subscriptions;
import com.codingshuttle.securityapp.entities.User;
import com.codingshuttle.securityapp.exceptions.ResourceNotFoundException;
import com.codingshuttle.securityapp.repositories.SubscriptionRepository;
import com.codingshuttle.securityapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public SubscriptionDto addNewSubscription(String refreshToken, Integer sessionCount){
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));

        Subscriptions subscription = Subscriptions.builder()
                .user(user)
                .sessionCount(sessionCount)
                .build();

        subscription = subscriptionRepository.save(subscription);

       return SubscriptionDto.builder()
               .user(subscription.getUser().getEmail())
               .sessionCount(sessionCount)
               .build();
    }

    public Integer getUserSessionCount(User user){
        Optional<Subscriptions> subscriptions = subscriptionRepository.findByUser(user);
        if(subscriptions.isPresent()){
            return subscriptions.get().getSessionCount();
        }
        return 1;
    }

}
