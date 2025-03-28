package org.FraudCheckApp.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.FraudCheckApp.Model.FraudCheckRequestDTO;
import org.FraudCheckApp.Model.FraudCheckResponseDTO;
import org.FraudCheckApp.Service.FraudCheckService;
import org.FraudCheckApp.Service.KafkaProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/fraud")
public class KafkaTestController {


    private final KafkaProducerService kafkaProducerService;

    public KafkaTestController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("test/send")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {
        kafkaProducerService.sendMessage("test-topic", message);
        return ResponseEntity.status(HttpStatus.OK).body("Sent message: " + message);
    }





}
