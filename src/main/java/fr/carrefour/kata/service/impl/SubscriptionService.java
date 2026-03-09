package fr.carrefour.kata.service.impl;

import fr.carrefour.kata.dao.CustomerDao;
import fr.carrefour.kata.dao.SubscriptionDao;
import fr.carrefour.kata.dao.SubscriptionPlanDao;
import fr.carrefour.kata.dto.ChangeSubscriptionRequest;
import fr.carrefour.kata.dto.CreateSubscriptionRequest;
import fr.carrefour.kata.event.SubscriptionEventPublisher;
import fr.carrefour.kata.model.Customer;
import fr.carrefour.kata.model.Subscription;
import fr.carrefour.kata.model.SubscriptionPlan;
import fr.carrefour.kata.model.SubscriptionStatus;
import fr.carrefour.kata.service.ISubscriptionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class SubscriptionService implements ISubscriptionService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private SubscriptionPlanDao planDao;

    @Autowired
    private SubscriptionDao subscriptionDao;

    //@Autowired
    //private SubscriptionEventPublisher eventPublisher;

    @Override
    @Transactional
    public Subscription createSubscription(CreateSubscriptionRequest subscription) {
        Customer customer = customerDao.findById(subscription.customerId()).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        SubscriptionPlan plan = planDao.findById(subscription.planId()) .orElseThrow(() -> new IllegalArgumentException("Plan not found"));

        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(plan.getDuration());

        Subscription newSubscription = new Subscription();
        newSubscription.setCustomer(customer);
        newSubscription.setPlan(plan);
        newSubscription.setStartDate(start);
        newSubscription.setEndDate(end);
        newSubscription.setStatus(SubscriptionStatus.ACTIVE);
        newSubscription.setAutoRenew(subscription.autoRenew());
        newSubscription.setNextCycleWithProrated(BigDecimal.ZERO);

        Subscription subscriptionSaved = subscriptionDao.save(newSubscription);
        //eventPublisher.publishSubscriptionCreated(subscriptionSaved);

        return subscriptionSaved;
    }

    @Override
    @Transactional
    public Subscription changeSubscription(Long subscriptionId, ChangeSubscriptionRequest newPlanRequest) {
        Subscription currentSubscription = subscriptionDao.findById(subscriptionId).orElseThrow(() -> new EntityNotFoundException("Subscription not found"));
        SubscriptionPlan newPlan = planDao.findById(newPlanRequest.planId()) .orElseThrow(() -> new IllegalArgumentException("Plan not found"));

        if (currentSubscription.getStatus() != SubscriptionStatus.ACTIVE) {
            throw new IllegalStateException("Subscription is not ACTIVE");
        }

        LocalDate today = LocalDate.now();
        LocalDate cycleStart = currentSubscription.getStartDate();
        LocalDate cycleEnd = currentSubscription.getEndDate();

        long totalDays = ChronoUnit.DAYS.between(cycleStart, cycleEnd);
        long remainingDays = ChronoUnit.DAYS.between(today, cycleEnd);
        if (remainingDays < 0) {
            remainingDays = 0;
        }

        BigDecimal oldPrice = currentSubscription.getPlan().getPrice();
        BigDecimal newPrice = newPlan.getPrice();

        BigDecimal prorated = BigDecimal.ZERO;
        if (totalDays > 0 && remainingDays > 0) {
            prorated = oldPrice.multiply(BigDecimal.valueOf(remainingDays))
                    .divide(BigDecimal.valueOf(totalDays), 2);
        }

        BigDecimal amountWithProrated = newPrice.subtract(prorated);

        currentSubscription.setPlan(newPlan);
        currentSubscription.setNextCycleWithProrated(amountWithProrated);

        return subscriptionDao.save(currentSubscription);

    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public Subscription renewExpiredSubscriptions() {
        return null;
    }
}
