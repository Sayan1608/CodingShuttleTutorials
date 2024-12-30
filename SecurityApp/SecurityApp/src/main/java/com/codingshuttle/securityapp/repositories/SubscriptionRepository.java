package com.codingshuttle.securityapp.repositories;

import com.codingshuttle.securityapp.entities.Subscriptions;
import com.codingshuttle.securityapp.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscriptions,Long> {
    Optional<Subscriptions> findByUser(User user);
}
