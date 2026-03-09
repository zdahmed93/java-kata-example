package fr.carrefour.kata.service;

import fr.carrefour.kata.dto.ChangeSubscriptionRequest;
import fr.carrefour.kata.dto.CreateSubscriptionRequest;
import fr.carrefour.kata.model.Subscription;

public interface ISubscriptionService {

    public Subscription createSubscription(CreateSubscriptionRequest subscription);
    public Subscription changeSubscription(Long subscriptionId, ChangeSubscriptionRequest newPlan);
    public Subscription renewExpiredSubscriptions();
}
