package org.FraudCheckApp.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic paymentRequestsTopic() {
        return TopicBuilder.name("payment-requests")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic fraudResultsTopic() {
        return TopicBuilder.name("fraud-results")
                .partitions(3)
                .replicas(1)
                .build();
    }
}