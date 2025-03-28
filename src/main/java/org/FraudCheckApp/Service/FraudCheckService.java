package org.FraudCheckApp.Service;


import org.FraudCheckApp.Exception.FraudCheckException;
import org.FraudCheckApp.Model.FraudCheckRequestDTO;
import org.FraudCheckApp.Model.FraudCheckResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class FraudCheckService {
    private static final Logger log = LoggerFactory.getLogger(FraudCheckService.class);

    private final FraudRuleEngine ruleEngine;
    @Qualifier("fraudCheckKafkaTemplate")
    private final KafkaTemplate<String, FraudCheckResponseDTO> kafkaTemplate;
    private static final String FRAUD_TOPIC = "fraud-results";

    public FraudCheckService(FraudRuleEngine ruleEngine,
                              KafkaTemplate<String, FraudCheckResponseDTO> kafkaTemplate) {
        this.ruleEngine = ruleEngine;
        this.kafkaTemplate = kafkaTemplate;
    }

    public FraudCheckResponseDTO performSyncCheck(FraudCheckRequestDTO request) {
        log.info("Processing sync fraud check for payment: {}", request.getPaymentId());
        return ruleEngine.evaluate(request);
    }

    public CompletableFuture<Void> performAsyncCheck(FraudCheckRequestDTO request) {
        log.info("Processing async fraud check for payment: {}", request.getPaymentId());
        try {
            FraudCheckResponseDTO response = ruleEngine.evaluate(request);
            return kafkaTemplate.send(FRAUD_TOPIC, response.getPaymentId(), response)
                    .thenAccept(result -> {
                        log.info("Async fraud result sent for payment: {}", request.getPaymentId());
                    })
                    .exceptionally(ex -> {
                        throw new FraudCheckException("Failed to send async result", ex);
                    });
        } catch (Exception ex) {
            throw new FraudCheckException("Async fraud check failed", ex);
        }
    }
}