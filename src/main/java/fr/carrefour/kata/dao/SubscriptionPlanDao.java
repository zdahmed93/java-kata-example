package fr.carrefour.kata.dao;

import fr.carrefour.kata.model.PlanType;
import fr.carrefour.kata.model.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionPlanDao extends JpaRepository<SubscriptionPlan, Long> {
}
