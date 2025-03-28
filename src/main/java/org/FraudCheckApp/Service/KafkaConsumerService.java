package org.FraudCheckApp.Service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);


    // Basic listener
    @KafkaListener(topics = "test-topic", groupId = "fraud-service-group")
    public void listen(String message) {
        log.info("Received Message: " + message);
    }
}