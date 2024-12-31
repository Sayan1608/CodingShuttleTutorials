package com.codingshuttle.securityapp.services;

import com.codingshuttle.securityapp.entities.Session;
import com.codingshuttle.securityapp.entities.User;
import com.codingshuttle.securityapp.exceptions.ResourceNotFoundException;
import com.codingshuttle.securityapp.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SubscriptionService subscriptionService;
//    private final int SESSION_LIMIT = 2;

    public void generateSession(User user, String refreshToken){
        List<Session> sessions = sessionRepository.findByUser(user);

        if(sessions.size() == subscriptionService.getUserSessionCount(user)){
            sessions.sort(Comparator.comparing(Session::getLastUsedAt));
            Session leastRecentlyUsedSession = sessions.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);
        }

        Session newSession = Session.builder()
                .refreshToken(refreshToken)
                .user(user)
                .build();
        sessionRepository.save(newSession);
    }

    public void validateSession(String refreshToken){
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() ->
                        new SessionAuthenticationException("Session not found for refresh token : " + refreshToken));
        session.setLastUsedAt(LocalDateTime.now());
    }

    public void logOutAndClearUserSessions(User user){
        List<Session> userSessions = sessionRepository.findByUser(user);
        if(userSessions.isEmpty())
            throw new ResourceNotFoundException("No Active Session Found for the User : " + user.getEmail());
        sessionRepository.deleteAll(userSessions);
    }

    public boolean isSessionAvailableForUser(User user){
        return sessionRepository.existsByUser(user);
    }

}
