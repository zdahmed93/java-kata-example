package fr.carrefour.kata.dto;

import fr.carrefour.kata.model.Customer;
import fr.carrefour.kata.model.SubscriptionPlan;
import fr.carrefour.kata.model.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscriptionResponse {
    private Long id;
    private Customer customer;
    private SubscriptionPlan plan;
    private LocalDate startDate;
    private LocalDate endDate;
    private SubscriptionStatus status;
    private boolean autoRenew;
    private BigDecimal nextCycleProrated;
}
