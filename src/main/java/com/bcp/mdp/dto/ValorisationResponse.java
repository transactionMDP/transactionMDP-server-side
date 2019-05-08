package com.bcp.mdp.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class ValorisationResponse {

	double exchangeTransferCurrencyToOther;
	double exchangeTransferCurrencyToMAD;
}
