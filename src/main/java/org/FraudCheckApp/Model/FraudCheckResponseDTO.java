package org.FraudCheckApp.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FraudCheckResponseDTO {

    private String paymentId;
    private FraudCheckStatus status;
    private String reason;

    public enum FraudCheckStatus {
        APPROVED, REJECTED, MANUAL_REVIEW
    }


}