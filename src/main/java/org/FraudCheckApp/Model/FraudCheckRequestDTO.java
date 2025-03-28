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
public class FraudCheckRequestDTO {

    private String paymentId;
    private String accountNumber;
    private BigDecimal amount;
    private String currency;
    private String merchantId;
    private String customerIp;

}