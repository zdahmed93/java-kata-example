package fr.carrefour.kata.controller;

import fr.carrefour.kata.dto.ChangeSubscriptionRequest;
import fr.carrefour.kata.dto.CreateSubscriptionRequest;
import fr.carrefour.kata.dto.SubscriptionResponse;
import fr.carrefour.kata.model.Subscription;
import fr.carrefour.kata.service.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    ISubscriptionService subscriptionService;

    @PostMapping("/create")
    public Subscription create(@RequestBody CreateSubscriptionRequest subscriptionRequest) {
        return subscriptionService.createSubscription(subscriptionRequest);
    }

    @PutMapping("/{id}/change")
    public Subscription changeSubscription(@PathVariable Long id, @RequestBody ChangeSubscriptionRequest newPlan) {
        return subscriptionService.changeSubscription(id, newPlan);
    }
}
