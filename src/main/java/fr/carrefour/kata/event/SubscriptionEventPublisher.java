package fr.carrefour.kata.event;

import fr.carrefour.kata.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


//@Component
public class SubscriptionEventPublisher {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishSubscriptionCreated(Subscription sub) {
        String payload = """
                {
                  "type": "SUBSCRIPTION_CREATED",
                  "subscriptionId": %d,
                  "customerId": %d,
                  "plan": "%s"
                }
                """.formatted(sub.getId(), sub.getCustomer().getId(), sub.getPlan().getType().name());
        kafkaTemplate.send("subscription-events", payload);
    }
}
