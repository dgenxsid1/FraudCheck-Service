package org.FraudCheckApp.Service;


import org.FraudCheckApp.Model.FraudCheckRequestDTO;
import org.FraudCheckApp.Model.FraudCheckResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class FraudRuleEngine {

    public FraudCheckResponseDTO evaluate(FraudCheckRequestDTO request) {
        // Rule 1: High amount check
        if (isHighAmount(request.getAmount())) {
            return buildResponse(request, FraudCheckResponseDTO.FraudCheckStatus.MANUAL_REVIEW, "High amount");
        }

        // Rule 2: Suspicious IP check
        if (isSuspiciousIp(request.getCustomerIp())) {
            return buildResponse(request, FraudCheckResponseDTO.FraudCheckStatus.REJECTED, "Suspicious IP");
        }

        // Rule 3: High-risk merchant check
        if (isHighRiskMerchant(request.getMerchantId())) {
            return buildResponse(request, FraudCheckResponseDTO.FraudCheckStatus.REJECTED, "High-risk merchant");
        }

        // Default approval
        return buildResponse(request, FraudCheckResponseDTO.FraudCheckStatus.APPROVED, "Approved");
    }

    private boolean isHighAmount(BigDecimal amount) {
        return amount.compareTo(new BigDecimal("10000")) > 0;
    }

    private boolean isSuspiciousIp(String ip) {
        return ip != null && ip.startsWith("5.5.");
    }

    private boolean isHighRiskMerchant(String getMerchantId) {
        return getMerchantId != null && getMerchantId.equalsIgnoreCase("HIGH_RISK_MERCHANT");
    }

    private FraudCheckResponseDTO buildResponse(FraudCheckRequestDTO request,
                                                FraudCheckResponseDTO.FraudCheckStatus status,
                                                String reason) {
        return new FraudCheckResponseDTO(
                request.getPaymentId(),
                status,
                reason + " | Evaluated at: " + Instant.now()
        );
    }
}