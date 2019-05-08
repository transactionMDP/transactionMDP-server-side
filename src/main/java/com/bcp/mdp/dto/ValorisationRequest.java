package com.bcp.mdp.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class ValorisationRequest {

	long accountDebit;
	String transactionCurrency;  
	double amount ;
	double commissionAmount;
}
