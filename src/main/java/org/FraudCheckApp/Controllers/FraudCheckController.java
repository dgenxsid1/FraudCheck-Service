package org.FraudCheckApp.Controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.FraudCheckApp.Exception.FraudCheckException;
import org.FraudCheckApp.Model.FraudCheckRequestDTO;
import org.FraudCheckApp.Model.FraudCheckResponseDTO;
import org.FraudCheckApp.Service.FraudCheckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fraud")
@Tag(name = "Fraud Check API", description = "Endpoints for fraud detection")
public class FraudCheckController {
    private final FraudCheckService fraudCheckService;

    public FraudCheckController(FraudCheckService fraudCheckService) {
        this.fraudCheckService = fraudCheckService;
    }

    @PostMapping("/sync")
    @Operation(summary = "Perform synchronous fraud check")
    public ResponseEntity<FraudCheckResponseDTO> syncCheck(
            @Valid @RequestBody FraudCheckRequestDTO request) {
        return ResponseEntity.ok(fraudCheckService.performSyncCheck(request));
    }

    @PostMapping("/async")
    @Operation(summary = "Initiate asynchronous fraud check")
    public ResponseEntity<String> asyncCheck(
            @Valid @RequestBody FraudCheckRequestDTO request) {
        fraudCheckService.performAsyncCheck(request);
        return ResponseEntity.accepted()
                .body("Fraud check initiated for payment: " + request.getPaymentId());
    }

    @ExceptionHandler(FraudCheckException.class)
    public ResponseEntity<String> handleFraudCheckException(FraudCheckException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Fraud check error: " + ex.getMessage());
    }
}