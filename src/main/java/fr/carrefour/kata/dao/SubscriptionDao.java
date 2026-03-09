package fr.carrefour.kata.dao;

import fr.carrefour.kata.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionDao extends JpaRepository<Subscription, Long> {
}
