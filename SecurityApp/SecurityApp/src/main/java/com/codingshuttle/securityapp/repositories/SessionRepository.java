package com.codingshuttle.securityapp.repositories;

import com.codingshuttle.securityapp.entities.Session;
import com.codingshuttle.securityapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByUser(User user);

    Optional<Session> findByRefreshToken(String refreshToken);

    boolean existsByUser(User user);
}
