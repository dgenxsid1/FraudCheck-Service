package org.FraudCheckApp.Service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    @Qualifier("fraudCheckKafkaTemplate")
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

    // Spring auto-injects the configured KafkaTemplate
    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, Object message) {
        try {
            log.info("Starting to send the message");
            kafkaTemplate.send(topic, message);  // Uses the template to send
            log.info("Message sent to topic: " + topic + ". the message was: " + message);
        }
        catch(RuntimeException ex){
            log.error("Exception occured: " + ex);
            throw ex;

        }
    }
}
