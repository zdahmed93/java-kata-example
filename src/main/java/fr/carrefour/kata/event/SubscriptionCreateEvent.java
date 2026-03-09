package fr.carrefour.kata.event;

import fr.carrefour.kata.model.SubscriptionPlan;

import java.math.BigDecimal;

public record SubscriptionCreateEvent (
    Long subscriptionId,
    Long customerId,
    SubscriptionPlan planId){
}
