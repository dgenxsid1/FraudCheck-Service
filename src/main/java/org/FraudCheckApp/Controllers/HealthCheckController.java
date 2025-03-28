package org.FraudCheckApp.Controllers;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.FraudCheckApp.Model.FraudCheckRequestDTO;
import org.FraudCheckApp.Model.FraudCheckResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/fraud")
public class HealthCheckController {


    @GetMapping("/healthCheck")
    @Operation(summary = "Performs Health Check")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String > mp = new HashMap<>();
        mp.put("health", "Ok");
        return ResponseEntity.status(HttpStatus.OK).body(mp);
    }
}
