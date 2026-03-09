package fr.carrefour.kata.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "subscription")
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    private SubscriptionPlan plan;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;
    private boolean autoRenew;
    private BigDecimal nextCycleWithProrated;

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public boolean isAutoRenew() {
        return autoRenew;
    }

    public BigDecimal getNextCycleWithProrated() {
        return nextCycleWithProrated;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public void setAutoRenew(boolean autoRenew) {
        this.autoRenew = autoRenew;
    }

    public void setNextCycleWithProrated(BigDecimal nextCycleWithProrated) {
        this.nextCycleWithProrated = nextCycleWithProrated;
    }
}
