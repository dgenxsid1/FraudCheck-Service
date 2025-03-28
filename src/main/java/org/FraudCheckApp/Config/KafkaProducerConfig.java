package org.FraudCheckApp.Config;

import org.FraudCheckApp.Model.FraudCheckResponseDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // Generic Producer Factory (used by all templates)
    @Bean
    public <V> DefaultKafkaProducerFactory<String, V> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false); // Cleaner JSON

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    // Generic Template (for ad-hoc use)
    @Bean(name = "genericKafkaTemplate")
    public <V> KafkaTemplate<String, V> genericKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // Specialized Template for Fraud Checks
    @Bean(name = "fraudCheckKafkaTemplate")
    public KafkaTemplate<String, FraudCheckResponseDTO> fraudCheckKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}