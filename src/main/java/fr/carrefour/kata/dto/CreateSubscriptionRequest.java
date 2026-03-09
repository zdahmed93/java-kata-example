package fr.carrefour.kata.dto;

import fr.carrefour.kata.model.PlanType;

public record CreateSubscriptionRequest(Long customerId, Long planId, boolean autoRenew) {
}
